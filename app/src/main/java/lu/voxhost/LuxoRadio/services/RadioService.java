package lu.voxhost.LuxoRadio.services;

import static lu.voxhost.LuxoRadio.utils.Constant.songName;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lu.voxhost.LuxoRadio.BuildConfig;
import lu.voxhost.LuxoRadio.R;
import lu.voxhost.LuxoRadio.activity.MainActivity;
import lu.voxhost.LuxoRadio.utils.HttpsTrustManager;
import lu.voxhost.LuxoRadio.utils.Tools;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.hls.DefaultHlsExtractorFactory;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;

import java.util.ArrayList;
import java.util.Arrays;

public class RadioService extends Service implements Player.Listener, AudioManager.OnAudioFocusChangeListener {
    public static final String ACTION_PLAY = BuildConfig.APPLICATION_ID + ".ACTION_PLAY";
    public static final String ACTION_PAUSE = ".ACTION_PAUSE";
    public static final String ACTION_RESUME = ".ACTION_RESUME";
    public static final String ACTION_STOP = ".ACTION_STOP";
    private final IBinder iBinder = new LocalBinder();
    private ExoPlayer exoPlayer;
    DataSource.Factory mediaDataSourceFactory;
    private DefaultBandwidthMeter BANDWIDTH_METER;
    public static MediaSessionCompat mediaSession;
    private MediaControllerCompat.TransportControls transportControls;
    private boolean onGoingCall = false;
    private WifiManager.WifiLock wifiLock;
    private AudioManager audioManager;
    public static MediaNotificationManager notificationManager;
    private boolean serviceInUse = false;
    private String status;
    private String nowPlaying;
    private String streamUrl;
    MediaSource mediaSource;

    public class LocalBinder extends Binder {
        public RadioService getService() {
            return RadioService.this;
        }
    }

    private BroadcastReceiver becomingNoisyReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            pause();
        }

    };

    private MediaSessionCompat.Callback mediasSessionCallback = new MediaSessionCompat.Callback() {
        @Override
        public void onPause() {
            super.onPause();
            pause();
        }

        @Override
        public void onStop() {
            super.onStop();
            stop();
            notificationManager.cancelNotify();
        }

        @Override
        public void onPlay() {
            super.onPlay();
            resume();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        serviceInUse = true;
        return iBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        nowPlaying = getResources().getString(R.string.now_playing);
        songName = getResources().getString(R.string.app_name);

        onGoingCall = false;

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        notificationManager = new MediaNotificationManager(this);

        wifiLock = ((WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE))
                .createWifiLock(WifiManager.WIFI_MODE_FULL, "mcScPAmpLock");

        mediaSession = new MediaSessionCompat(this, getClass().getSimpleName());
        transportControls = mediaSession.getController().getTransportControls();
        mediaSession.setActive(true);
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMetadata(new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, songName)
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, nowPlaying)
                .build());
        mediaSession.setCallback(mediasSessionCallback);

        try {
            registerReceiver(onCallIncome, new IntentFilter("android.intent.action.PHONE_STATE"));
            registerReceiver(onHeadPhoneDetect, new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY));
        } catch (Exception e) {
            e.printStackTrace();
        }

        BANDWIDTH_METER = new DefaultBandwidthMeter.Builder(this).build();
        mediaDataSourceFactory = buildDataSourceFactory(true);

        AdaptiveTrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory();
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(this, trackSelectionFactory);
        exoPlayer = new ExoPlayer.Builder(this).setTrackSelector(trackSelector).build();
        exoPlayer.addListener(this);
        exoPlayer.addAnalyticsListener(new AnalyticsListener() {
            @Override
            public void onAudioSessionIdChanged(EventTime eventTime, int audioSessionId) {
                Tools.onAudioSessionId(getAudioSessionId());
            }
        });

        exoPlayer.setPlayWhenReady(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent.getAction();

        if (TextUtils.isEmpty(action))
            return START_NOT_STICKY;

        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            stop();
            return START_NOT_STICKY;
        }

        if (action.equalsIgnoreCase(ACTION_PLAY)) {
            transportControls.play();
        } else if (action.equalsIgnoreCase(ACTION_PAUSE)) {
            transportControls.pause();
        } else if (action.equalsIgnoreCase(ACTION_RESUME)) {
            Intent meaw = new Intent(getApplicationContext(), MainActivity.class);
            meaw.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            meaw.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(meaw);
        } else if (action.equalsIgnoreCase(ACTION_STOP)) {
            transportControls.stop();
        }

        return START_NOT_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        serviceInUse = false;
        if (status.equals(PlaybackStatus.IDLE))
            stopSelf();
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(final Intent intent) {
        serviceInUse = true;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                exoPlayer.setVolume(0.8f);
                resume();
                break;

            case AudioManager.AUDIOFOCUS_LOSS:
                stop();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                if (isPlaying()) pause();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if (isPlaying())
                    exoPlayer.setVolume(0.1f);
                break;
        }
    }

    BroadcastReceiver onCallIncome = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String a = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            try {
                if (exoPlayer.getPlayWhenReady()) {
                    if (a.equals(TelephonyManager.EXTRA_STATE_OFFHOOK) || a.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        exoPlayer.setPlayWhenReady(false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    };

    BroadcastReceiver onHeadPhoneDetect = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (exoPlayer.getPlayWhenReady()) {
                    if (streamUrl != null) {
                        playOrPause(streamUrl);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        switch (playbackState) {
            case Player.STATE_BUFFERING:
                status = PlaybackStatus.LOADING;
                break;
            case Player.STATE_ENDED:
                status = PlaybackStatus.STOPPED;
                break;
            case Player.STATE_READY:
                status = playWhenReady ? PlaybackStatus.PLAYING : PlaybackStatus.PAUSED;
                break;
            default:
                status = PlaybackStatus.IDLE;
                break;
        }

        if (!status.equals(PlaybackStatus.IDLE))
            notificationManager.startNotify(status);

        Tools.onEvent(status);
    }

    @Override
    public void onPlayerError(@NonNull PlaybackException error) {
        Tools.onEvent(PlaybackStatus.ERROR);
    }

    @Override
    public void onMetadata(@NonNull Metadata metadata) {
        new Handler().postDelayed(() -> getMetadata(metadata), 1000);
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    private String getUserAgent() {
        StringBuilder result = new StringBuilder(64);
        result.append("Dalvik/");
        result.append(System.getProperty("java.vm.version"));
        result.append(" (Linux; U; Android ");

        String version = Build.VERSION.RELEASE;
        result.append(version.length() > 0 ? version : "1.0");

        if ("REL".equals(Build.VERSION.CODENAME)) {
            String model = Build.MODEL;
            if (model.length() > 0) {
                result.append("; ");
                result.append(model);
            }
        }

        String id = Build.ID;
        if (id.length() > 0) {
            result.append(" Build/");
            result.append(id);
        }
        result.append(")");
        return result.toString();
    }

    public void play(String streamUrl) {
        this.streamUrl = streamUrl;
        if (wifiLock != null && !wifiLock.isHeld()) {
            wifiLock.acquire();
        }
        HttpsTrustManager.allowAllSSL();

        MediaItem mMediaItem = MediaItem.fromUri(Uri.parse(streamUrl));
        if (streamUrl.contains(".m3u8") || streamUrl.contains(".M3U8")) {
            mediaSource = new HlsMediaSource.Factory(mediaDataSourceFactory)
                    .setAllowChunklessPreparation(false)
                    .setExtractorFactory(new DefaultHlsExtractorFactory(DefaultTsPayloadReaderFactory.FLAG_IGNORE_H264_STREAM, false))
                    .createMediaSource(mMediaItem);
        } else if (streamUrl.contains(".m3u") || streamUrl.contains("yp.shoutcast.com/sbin/tunein-station.m3u?id=")) {
            mediaSource = new ProgressiveMediaSource.Factory(mediaDataSourceFactory, new DefaultExtractorsFactory())
                    .createMediaSource(mMediaItem);
        } else if (streamUrl.contains(".pls") || streamUrl.contains("listen.pls?sid=") || streamUrl.contains("yp.shoutcast.com/sbin/tunein-station.pls?id=")) {
            mediaSource = new ProgressiveMediaSource.Factory(mediaDataSourceFactory, new DefaultExtractorsFactory())
                    .createMediaSource(mMediaItem);
        } else {
            mediaSource = new ProgressiveMediaSource.Factory(mediaDataSourceFactory, new DefaultExtractorsFactory())
                    .createMediaSource(mMediaItem);
        }
        exoPlayer.setMediaSource(mediaSource);
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);
    }

    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    public DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultDataSource.Factory(this, buildHttpDataSourceFactory(bandwidthMeter));
    }

    public HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultHttpDataSource.Factory().setUserAgent(getUserAgent()).setTransferListener(bandwidthMeter);
    }

    public int getAudioSessionId() {
        return exoPlayer.getAudioSessionId();
    }

    public void resume() {
        if (streamUrl != null) {
            play(streamUrl);
        }
    }

    public void pause() {
        exoPlayer.setPlayWhenReady(false);
        audioManager.abandonAudioFocus(this);
        wifiLockRelease();
    }

    public void stop() {
        exoPlayer.stop();
        notificationManager.cancelNotify();
        audioManager.abandonAudioFocus(this);
        try {
            unregisterReceiver(onCallIncome);
            unregisterReceiver(onHeadPhoneDetect);
        } catch (Exception e) {
            e.printStackTrace();
        }
        wifiLockRelease();
    }

    public void playOrPause(String url) {
        if (url != null && url.equals(url)) {
            if (!isPlaying()) {
                play(url);
            } else {
                pause();
            }
        } else {
            if (isPlaying()) {
                pause();
            } else {
                play(url);
            }
        }
    }

    public void stopAndPlay(String url) {
        if (url != null && !url.isEmpty()) {
            if (!isPlaying()) {
                play(url);
            } else {
                stop();
                play(url);
            }
        } else {
            if (isPlaying()) {
                stop();
                play(url);
            } else {
                play(url);
            }
        }
    }

    public String getStatus() {
        return status;
    }

    public MediaSessionCompat getMediaSession() {
        return mediaSession;
    }

    public boolean isPlaying() {
        return this.status.equals(PlaybackStatus.PLAYING);
    }

    private void wifiLockRelease() {
        if (wifiLock != null && wifiLock.isHeld()) {
            wifiLock.release();
        }
    }

    @Override
    public void onDestroy() {
        pause();
        try {
            notificationManager.cancelNotify();
            exoPlayer.release();
            exoPlayer.removeListener(this);
            mediaSession.release();
            unregisterReceiver(onCallIncome);
            unregisterReceiver(onHeadPhoneDetect);
            unregisterReceiver(becomingNoisyReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void getMetadata(Metadata metadata){
        // Toast.makeText(this, metadata.toString(), Toast.LENGTH_SHORT).show();
        if (!metadata.get(0).toString().equals("")) {
            String data = metadata.get(0).toString().replace("ICY: ", "");
            ArrayList<String> arrayList = new ArrayList(Arrays.asList(data.split(",")));
            String[] mediaMetadata = arrayList.get(0).split("=");

            String currentSong = mediaMetadata[1].replace("\"", "");

            if (currentSong.contains("null")) {
                currentSong = getString(R.string.unknown_song);
            } else if (currentSong.isEmpty()){
                currentSong = getString(R.string.unknown_song);
            }

            if (isPlaying()){
                nowPlaying = getString(R.string.now_playing);
                songName = currentSong;
                if (songName.isEmpty()){
                    songName = getString(R.string.unknown_song);
                }
                MainActivity.updateNowPlayingText();
                mediaSession.setMetadata(new MediaMetadataCompat.Builder()
                        .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, songName)
                        .putString(MediaMetadataCompat.METADATA_KEY_TITLE, nowPlaying)
                        .build());
                notificationManager.startNotify(status);
            }
        }
    }


}

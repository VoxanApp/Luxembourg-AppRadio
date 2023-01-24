package lu.voxhost.LuxoRadio.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import lu.voxhost.LuxoRadio.R;

public class MediaNotificationManager {
    public static final int NOTIFICATION_ID = 555;
    public static final String NOTIFICATION_CHANNEL_ID = "AM Radio";
    private RadioService service;
    private String nowPlaying, songName;
    private Bitmap notifyIcon;
    private String playbackStatus;
    private Resources resources;
    PendingIntent action;

    public MediaNotificationManager(RadioService service) {
        this.service = service;
        this.resources = service.getResources();
    }

    public void startNotify(String playbackStatus) {
        this.playbackStatus = playbackStatus;
        //this.notifyIcon = BitmapFactory.decodeResource(resources, R.drawable.placeholder_albumart);
        startNotify();
    }

    public void changeIcon(Bitmap notifyIcon){
        this.notifyIcon = notifyIcon;
        startNotify();
    }

    public void changeRadio(String songName) {
        this.songName = songName;
        startNotify();
    }

    @SuppressLint("NotificationTrampoline")
    private void startNotify() {
        if (playbackStatus == null) return;

        if (notifyIcon == null)
            notifyIcon = BitmapFactory.decodeResource(resources, R.drawable.app_icon);

        NotificationManager notificationManager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, service.getString(R.string.audio_notification), NotificationManager.IMPORTANCE_LOW);
            channel.enableVibration(false);
            channel.setSound(null, null);
            notificationManager.createNotificationChannel(channel);
        }

        int icon = R.drawable.ic_pause;
        Intent playbackAction = new Intent(service, RadioService.class);
        playbackAction.setAction(RadioService.ACTION_PAUSE);
        action = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            action = PendingIntent.getActivity
                    (service, 1, playbackAction, PendingIntent.FLAG_MUTABLE);
        } else {
            action = PendingIntent.getService(service, 1, playbackAction, 0);
        }

        if (playbackStatus.equals(PlaybackStatus.PAUSED)) {
            icon = R.drawable.ic_play;
            playbackAction.setAction(RadioService.ACTION_PLAY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                action = PendingIntent.getService(service, 2, playbackAction, PendingIntent.FLAG_MUTABLE);
            } else {
                action = PendingIntent.getService(service, 2, playbackAction, 0);
            }
        }

        Intent stopIntent = new Intent(service, RadioService.class);
        stopIntent.setAction(RadioService.ACTION_STOP);

        PendingIntent stopAction = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            stopAction = PendingIntent.getService(service, 3, stopIntent, PendingIntent.FLAG_MUTABLE);
        } else {
            stopAction = PendingIntent.getService(service, 3, stopIntent, 0);
        }

        Intent intent = new Intent(service, RadioService.class);
        intent.setAction(RadioService.ACTION_RESUME);
        PendingIntent pendingIntent =  null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getService(service, 1, intent, PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getService(service, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        NotificationManagerCompat.from(service).cancel(NOTIFICATION_ID);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(service, NOTIFICATION_CHANNEL_ID);

        builder.setContentTitle(nowPlaying)
                .setContentText(songName)
                .setLargeIcon(notifyIcon)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_radio_playing)
                .addAction(icon, "pause", action)
                .addAction(R.drawable.ic_stop, "stop", stopAction)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVibrate(new long[]{0L})
                .setWhen(System.currentTimeMillis())
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(service.getMediaSession().getSessionToken())
                        .setShowActionsInCompactView(0, 1)
                        .setShowCancelButton(true)
                        .setCancelButtonIntent(stopAction));

        Notification notification = builder.build();
        service.startForeground(NOTIFICATION_ID, notification);

    }

    public void cancelNotify() {
        service.stopForeground(true);
    }

}

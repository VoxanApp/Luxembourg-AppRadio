package com.andromob.amradio.activity;

import static com.andromob.amradio.utils.Constant.RADIO_CH_VIEWS;
import static com.andromob.amradio.utils.Constant.RADIO_FREQUENCY;
import static com.andromob.amradio.utils.Constant.RADIO_ICON;
import static com.andromob.amradio.utils.Constant.RADIO_ID;
import static com.andromob.amradio.utils.Constant.RADIO_NAME;
import static com.andromob.amradio.utils.Constant.RADIO_STREAM_URL;
import static com.andromob.amradio.utils.Constant.songName;
import static com.andromob.amradio.utils.Methods.chk;
import static com.andromob.amradio.utils.Methods.doReport;
import static com.andromob.amradio.utils.Methods.setMargins;
import static com.andromob.amradio.utils.Methods.showBottomSheetDialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.andromob.amradio.BuildConfig;
import com.andromob.amradio.R;
import com.andromob.amradio.databinding.ActivityMainBinding;
import com.andromob.amradio.fragments.AboutFragment;
import com.andromob.amradio.fragments.CategoryFragment;
import com.andromob.amradio.fragments.ChannelFragment;
import com.andromob.amradio.fragments.HomeFragment;
import com.andromob.amradio.fragments.PrivacyFragment;
import com.andromob.amradio.services.PlaybackStatus;
import com.andromob.amradio.services.RadioManager;
import com.andromob.amradio.utils.AdsManager;
import com.andromob.amradio.utils.FcmReceiver;
import com.andromob.amradio.utils.Methods;
import com.andromob.amradio.utils.Prefs;
import com.andromob.amradio.utils.Tools;
import com.applovin.mediation.ads.MaxAdView;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

@SuppressLint("UseCompatLoadingForDrawables")
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Tools.EventListener {
    public static FragmentManager fragmentManager;
    ActivityMainBinding binding;
    Boolean isExpand = false;
    RadioManager radioManager;
    ImageView radioIcMini, radioIcFull, btnPLayMini, btnFav;
    ExtendedFloatingActionButton btnPLayFull;
    TextView radio_name_mini, radio_name_full, frequency, views, tv_loading;
    public static SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;
    public static TextView now_playing_tv_mini, now_playing_tv_full;
    FrameLayout adContainerView;
    AdView admobBannerSmall;
    MaxAdView maxBannerSmall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        fragmentManager = getSupportFragmentManager();
        radioManager = RadioManager.with();
        FcmReceiver.initFCM(this);
        initUiComponents();
        AdsManager.loadInterAd(this);
        checkNotificationData();
        setupSlidingLayout();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Bundle bundle = new Bundle();
        Fragment frgmt = new ChannelFragment();
        switch (id) {
            case R.id.nav_home:
                AdsManager.showInterAd(this);
                displaySelectedFragment(new HomeFragment());
                break;
            case R.id.nav_latest:
                AdsManager.showInterAd(this);
                Methods.addSelectedFragment(frgmt, this);
                break;
            case R.id.nav_cat:
                AdsManager.showInterAd(this);
                Methods.addSelectedFragment(new CategoryFragment(), this);
                break;
            case R.id.nav_fav:
                AdsManager.showInterAd(this);
                bundle.putString("type", "Fav");
                frgmt.setArguments(bundle);
                Methods.addSelectedFragment(frgmt, this);
                break;
            case R.id.nav_facebook:
                Methods.gotoFB(this);
                break;
            case R.id.nav_instagram:
                Methods.gotoinstagram(this);
                break;
            case R.id.nav_telegram:
                Methods.joinTelegram(this);
                break;
            case R.id.nav_twitter:
                Methods.gotoTwitter(this);
                break;
            case R.id.nav_feedback:
                Methods.sendEmail(this);
                break;
            case R.id.nav_rate:
                Methods.rateOnPlaystore(this);
                break;
            case R.id.nav_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                String shareMessage = getString(R.string.app_share_msg);
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
                break;
            case R.id.nav_about:
                AdsManager.showInterAd(this);
                displaySelectedFragment(new AboutFragment());
                break;
            case R.id.nav_privacy:
                AdsManager.showInterAd(this);
                displaySelectedFragment(new PrivacyFragment());
                break;
            case R.id.nav_exit:
                finish();
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("SetTextI18n")
    private void initUiComponents() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);
        adContainerView = binding.lytMusic.musicPlayerFull.adContainerView;
        tv_loading = binding.lytMusic.musicPlayerFull.tvLoading;
        radioIcMini = binding.lytMusic.musicPlayerMini.radioIconMini;
        radioIcFull = binding.lytMusic.musicPlayerFull.radioIconFull;
        btnPLayMini = binding.lytMusic.musicPlayerMini.btnPLayMini;
        btnPLayFull = binding.lytMusic.musicPlayerFull.btnPLayFull;
        radio_name_mini = binding.lytMusic.musicPlayerMini.radioNameMini;
        radio_name_full = binding.lytMusic.musicPlayerFull.radioNameFull;
        now_playing_tv_mini = binding.lytMusic.musicPlayerMini.nowPlayingTvMini;
        now_playing_tv_full = binding.lytMusic.musicPlayerFull.nowPlayingTvFull;
        frequency = binding.lytMusic.musicPlayerFull.tvFrequency;
        views = binding.lytMusic.musicPlayerFull.views;
        volumeSeekbar = binding.lytMusic.musicPlayerFull.volumeSeekbar;
        btnFav = binding.lytMusic.musicPlayerFull.btnFav;
        now_playing_tv_mini.setSelected(true);
        now_playing_tv_full.setSelected(true);
        if (RADIO_NAME.isEmpty()) {
            RADIO_NAME = getString(R.string.app_name);
        }
        radio_name_mini.setText(RADIO_NAME);
        radio_name_full.setText(RADIO_NAME);
        frequency.setText(RADIO_FREQUENCY);
        views.setText(Methods.viewFormat(RADIO_CH_VIEWS) + " Views");
        btnPLayMini.setOnClickListener(v -> {
            if (radioManager != null) {
                if (RADIO_STREAM_URL.isEmpty()) {
                    Toast.makeText(this, getString(R.string.select_radio_msg), Toast.LENGTH_SHORT).show();
                } else {
                    radioManager.playOrPause(RADIO_STREAM_URL);
                }
            }
        });

        btnPLayFull.setOnClickListener(v -> {
            if (radioManager != null) {
                if (RADIO_STREAM_URL.isEmpty()) {
                    Toast.makeText(this, getString(R.string.select_radio_msg), Toast.LENGTH_SHORT).show();
                } else {
                    radioManager.playOrPause(RADIO_STREAM_URL);
                }
            }
        });
        binding.lytMusic.musicPlayerFull.btnDescription.setOnClickListener(view -> showBottomSheetDialog(MainActivity.this));
        initControls();
        checkFav(RADIO_ID, btnFav);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFav(RADIO_ID, btnFav);
            }
        });
        binding.lytMusic.musicPlayerFull.btnReport.setOnClickListener(view -> doReport(MainActivity.this));

    }

    public static void updateNowPlayingText() {
        now_playing_tv_mini.setText(songName);
        now_playing_tv_full.setText(songName);
    }

    private void checkNotificationData() {
        if (FcmReceiver.TV_CAT_ID != null && !FcmReceiver.TV_CAT_ID.isEmpty() && !FcmReceiver.TV_CAT_ID.equals("false")) {
            Bundle bundle = new Bundle();
            bundle.putString("type", "CatNotification");
            bundle.putInt("cat_id", Integer.parseInt(FcmReceiver.TV_CAT_ID));
            ChannelFragment frgmnt = new ChannelFragment();
            frgmnt.setArguments(bundle);
            MainActivity.fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.fragment_container, frgmnt, null).commit();
            FcmReceiver.TV_CAT_ID = null;
            binding.navView.getMenu().findItem(R.id.nav_cat).setChecked(true);
        } else if (FcmReceiver.TV_CHANNEL_ID != null && !FcmReceiver.TV_CHANNEL_ID.isEmpty() && !FcmReceiver.TV_CHANNEL_ID.equals("false")) {
            Bundle bundle = new Bundle();
            bundle.putString("type", "ChannelNotification");
            bundle.putInt("channel_id", Integer.parseInt(FcmReceiver.TV_CHANNEL_ID));
            ChannelFragment frgmnt = new ChannelFragment();
            frgmnt.setArguments(bundle);
            MainActivity.fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.fragment_container, frgmnt, null).commit();
            FcmReceiver.TV_CHANNEL_ID = null;
            binding.navView.getMenu().findItem(R.id.nav_home).setChecked(true);
        } else {
            displaySelectedFragment(new HomeFragment());
            binding.navView.getMenu().findItem(R.id.nav_home).setChecked(true);
        }
    }

    private void showExitDialog() {
        final Dialog exit_dialog = new Dialog(this);
        exit_dialog.setContentView(R.layout.exit_dialog);
        AdView BannerAdexitAdmob = null;
        MaxAdView BannerAdexitMax = null;
        FrameLayout adContainerView;
        ImageView closebtn = exit_dialog.findViewById(R.id.close_btn);
        Button minimize_btn = exit_dialog.findViewById(R.id.minimize_btn);
        Button quit_btn = exit_dialog.findViewById(R.id.quit_btn);
        adContainerView = exit_dialog.findViewById(R.id.adContainerView);
        AdsManager.showBigBannerAd(this, BannerAdexitAdmob, BannerAdexitMax, adContainerView);
        exit_dialog.show();
        exit_dialog.setCanceledOnTouchOutside(false);
        exit_dialog.setCancelable(false);
        exit_dialog.getWindow().getAttributes().width = FrameLayout.LayoutParams.MATCH_PARENT;
        exit_dialog.getWindow().getAttributes().height = FrameLayout.LayoutParams.MATCH_PARENT;
        exit_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        closebtn.setOnClickListener(v -> exit_dialog.dismiss());
        minimize_btn.setOnClickListener(v -> {
            AdsManager.destroyBigBannerAds(BannerAdexitAdmob, BannerAdexitMax);
            exit_dialog.dismiss();
            minimizeApp();
        });
        quit_btn.setOnClickListener(v -> {
            AdsManager.destroyBigBannerAds(BannerAdexitAdmob, BannerAdexitMax);
            exit_dialog.dismiss();
            stopDestroyService();
            finish();
        });
    }

    private void stopDestroyService() {
        if (radioManager != null) {
            radioManager.stopServices();
            radioManager.unbind(this);
        }
    }

    public void minimizeApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void displaySelectedFragment(Fragment fragment) {
        closeSlidingPanel();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void setupSlidingLayout() {
        binding.slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if (slideOffset == 0.0f) {
                    isExpand = false;
                    binding.lytMusic.musicPlayerMini.getRoot().setVisibility(View.VISIBLE);
                    binding.lytMusic.musicPlayerFull.getRoot().setVisibility(View.INVISIBLE);
                    AdsManager.destroyBannerAds(admobBannerSmall, maxBannerSmall);
                } else if (slideOffset > 0.0f && slideOffset < 1.0f) {
                    AdsManager.destroyBannerAds(admobBannerSmall, maxBannerSmall);
                    binding.lytMusic.musicPlayerMini.getRoot().setVisibility(View.VISIBLE);
                    binding.lytMusic.musicPlayerFull.getRoot().setVisibility(View.VISIBLE);

                    if (isExpand) {
                        binding.lytMusic.musicPlayerMini.getRoot().setAlpha(1.0f - slideOffset);
                        binding.lytMusic.musicPlayerFull.getRoot().setAlpha(0.0f + slideOffset);
                        AdsManager.showBannerAd(MainActivity.this, admobBannerSmall, maxBannerSmall, adContainerView);
                    } else {
                        AdsManager.destroyBannerAds(admobBannerSmall, maxBannerSmall);
                        binding.lytMusic.musicPlayerMini.getRoot().setAlpha(1.0f - slideOffset);
                        binding.lytMusic.musicPlayerFull.getRoot().setAlpha(slideOffset);
                    }
                } else {
                    isExpand = true;
                    AdsManager.showBannerAd(MainActivity.this, admobBannerSmall, maxBannerSmall, adContainerView);
                    binding.lytMusic.musicPlayerMini.getRoot().setVisibility(View.INVISIBLE);
                    binding.lytMusic.musicPlayerFull.getRoot().setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });
        binding.lytMusic.musicPlayerMini.getRoot().setOnClickListener(view -> {
            if (binding.slidingLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
                binding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                AdsManager.showBannerAd(MainActivity.this, admobBannerSmall, maxBannerSmall, adContainerView);
            }
        });
        binding.lytMusic.musicPlayerFull.btnClosePanel.setOnClickListener(view -> {
            if (binding.slidingLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED)) {
                binding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                AdsManager.destroyBannerAds(admobBannerSmall, maxBannerSmall);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else if (binding.slidingLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED)) {
            binding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else if (fragmentManager.getBackStackEntryCount() == 0) {
            showExitDialog();
        } else {
            showExitDialog();
        }
    }

    public void closeSlidingPanel() {
        if (binding.slidingLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED)) {
            binding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
    }

    private void openSlidingPanel() {
        if (binding.slidingLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
            binding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }
    }

    private boolean isPlaying() {
        return (null != radioManager && null != RadioManager.getService() && RadioManager.getService().isPlaying());
    }

    @SuppressLint("SetTextI18n")
    private void updateMusicLyt() {
        tv_loading.setVisibility(View.GONE);
        ProgressBar radioIcFullProg = binding.lytMusic.musicPlayerFull.imgProgressBar;
        btnPLayMini.setVisibility(View.VISIBLE);
        radio_name_mini.setText(RADIO_NAME);
        radio_name_full.setText(RADIO_NAME);
        frequency.setText(RADIO_FREQUENCY);
        views.setText(Methods.viewFormat(RADIO_CH_VIEWS) + " Views");
        checkFav(RADIO_ID, btnFav);
        if (isPlaying()) {
            if (RadioManager.getService() != null && RADIO_STREAM_URL != null && !RADIO_STREAM_URL.equals(RadioManager.getService().getStreamUrl())) {
                btnPLayMini.setImageDrawable(getDrawable(R.drawable.ic_play));
                btnPLayFull.setIcon(getDrawable(R.drawable.ic_play));
                Methods.loadImageNoCropNoProg(radioIcMini, RADIO_ICON);
                Methods.loadImageNoCrop(radioIcFull, RADIO_ICON, radioIcFullProg);
            } else {
                btnPLayMini.setImageDrawable(getDrawable(R.drawable.ic_pause));
                btnPLayFull.setIcon(getDrawable(R.drawable.ic_pause));
                Methods.loadImageNoCropNoProg(radioIcMini, RADIO_ICON);
                Methods.loadImageNoCrop(radioIcFull, RADIO_ICON, radioIcFullProg);
            }
        } else {
            btnPLayMini.setImageDrawable(getDrawable(R.drawable.ic_play));
            btnPLayFull.setIcon(getDrawable(R.drawable.ic_play));
            Methods.loadImageNoCropNoProg(radioIcMini, RADIO_ICON);
            Methods.loadImageNoCrop(radioIcFull, RADIO_ICON, radioIcFullProg);
        }
    }

    @Override
    public void onEvent(String status) {
        updateMusicLyt();
        switch (status) {
            case PlaybackStatus.LOADING:
                tv_loading.setVisibility(View.VISIBLE);
                binding.lytMusic.musicPlayerMini.radioProgressMini.setVisibility(View.VISIBLE);
                btnPLayMini.setVisibility(View.GONE);
                break;
            case PlaybackStatus.ERROR:
                tv_loading.setVisibility(View.GONE);
                binding.lytMusic.musicPlayerMini.radioProgressMini.setVisibility(View.GONE);
                btnPLayMini.setVisibility(View.VISIBLE);
                break;
        }

        if (!status.equals(PlaybackStatus.LOADING)) {
            binding.lytMusic.musicPlayerMini.radioProgressMini.setVisibility(View.GONE);
            btnPLayMini.setVisibility(View.VISIBLE);
            updateMusicLyt();
            updateNowPlayingText();
        }
    }

    @Override
    public void onAudioSessionId(Integer i) {

    }

    public void changeFrequency() {
        binding.lytMusic.musicPlayerMini.getRoot().setVisibility(View.VISIBLE);
        setMargins(this, binding.mainBar.getRoot(), 0, 0, 0, 65);
        if (binding.slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
            binding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }
        radioManager.ChangeFrequency(RADIO_STREAM_URL);
    }

    private void initControls() {
        try {
            audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));

            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, AudioManager.FLAG_PLAY_SOUND);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkFav(int favID, ImageView imageView) {
        String favIDList = Prefs.getPreference(this, Prefs.PREFERENCES, Prefs.FAV_ID, "");
        if (favIDList != null) {
            if (favIDList.contains("," + favID + ",")) {
                imageView.setImageResource(R.drawable.ic_favorite);
            } else {
                imageView.setImageResource(R.drawable.ic_favorite_border);
            }
        } else {
            imageView.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    private void addToFav(int favID, ImageView imageView) {
        String message;
        String favIDList = Prefs.getPreference(this, Prefs.PREFERENCES, Prefs.FAV_ID, "");

        if (favIDList != null) {
            if (favIDList.contains("," + favID + ",")) {
                String newalltitle = favIDList.replace("," + favID + ",", "");
                Prefs.setPreference(this, Prefs.PREFERENCES, Prefs.FAV_ID, newalltitle);
                imageView.setImageResource(R.drawable.ic_favorite_border);
                message = getString(R.string.removed_from_fav);

            } else {
                favIDList = favIDList + "," + favID + ",";
                Prefs.setPreference(this, Prefs.PREFERENCES, Prefs.FAV_ID, favIDList);
                imageView.setImageResource(R.drawable.ic_favorite);
                message = getString(R.string.added_to_fav);
            }
        } else {
            favIDList = "," + favID + ",";
            Prefs.setPreference(this, Prefs.PREFERENCES, Prefs.FAV_ID, favIDList);
            imageView.setImageResource(R.drawable.ic_favorite);
            message = getString(R.string.added_to_fav);
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            int index = volumeSeekbar.getProgress();
            volumeSeekbar.setProgress(index + 1);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            int index = volumeSeekbar.getProgress();
            volumeSeekbar.setProgress(index - 1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        chk(this);
        Tools.registerAsListener(this);
    }

    @Override
    protected void onStop() {
        Tools.unregisterAsListener(this);
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AdsManager.destroyBannerAds(admobBannerSmall, maxBannerSmall);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (radioManager != null) {
            radioManager.bind(this);
        }
        initControls();
        updateMusicLyt();
        if (binding.slidingLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED)) {
            AdsManager.showBannerAd(MainActivity.this, admobBannerSmall, maxBannerSmall, adContainerView);
        }
    }

    @Override
    protected void onDestroy() {
        AdsManager.destroyBannerAds(admobBannerSmall, maxBannerSmall);
        AdsManager.destroyInterAds();
        if (!radioManager.isPlaying()) {
            radioManager.unbind(this);
        }
        super.onDestroy();
    }
}

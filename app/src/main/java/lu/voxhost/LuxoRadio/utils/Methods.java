package lu.voxhost.LuxoRadio.utils;

import static lu.voxhost.LuxoRadio.activity.MainActivity.fragmentManager;
import static lu.voxhost.LuxoRadio.utils.Prefs.APP_CHECK;
import static lu.voxhost.LuxoRadio.utils.Prefs.getPreference;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.media.MediaMetadataCompat;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import lu.voxhost.LuxoRadio.BuildConfig;
import lu.voxhost.LuxoRadio.R;
import lu.voxhost.LuxoRadio.activity.MainActivity;
import lu.voxhost.LuxoRadio.api.Api;
import lu.voxhost.LuxoRadio.api.ApiClient;
import lu.voxhost.LuxoRadio.models.Channels;
import lu.voxhost.LuxoRadio.models.Report;
import lu.voxhost.LuxoRadio.models.Settings;
import lu.voxhost.LuxoRadio.models.UserToken;
import lu.voxhost.LuxoRadio.models.VP;
import lu.voxhost.LuxoRadio.services.RadioService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Methods {

    public static Api getApi() {
        return ApiClient.getApiClient().create(Api.class);
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivity == null) {
                return false;
            } else {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                for (NetworkInfo networkInfo : info) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public static ActionBar getActionBar(Context context) {
        return ((MainActivity) context).getSupportActionBar();
    }

    public static void rateOnPlaystore(Activity activity) {
        if (activity != null) {
            Uri uri = Uri.parse("market://details?id=" + activity.getApplication().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                activity.startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getApplication().getPackageName())));
            }
        }
    }

    public static void sendEmail(Activity activity) {
        if (activity != null) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.setPackage("com.google.android.gm");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{activity.getString(R.string.about_us_email_text)});
            intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name));
            activity.startActivity(intent);
        }
    }

    public static void gotoTwitter(Context context) {
        if (context != null) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/" + context.getResources().getString(R.string.twitter_username))));
        }
    }

    public static void gotoFB(Context context) {
        if (context != null) {
            Uri uri = Uri.parse("fb://page/" + context.getResources().getString(R.string.facebook_page_id));
            Intent data = new Intent(Intent.ACTION_VIEW, uri);
            data.setPackage("com.facebook.katana");

            try {
                context.startActivity(data);
            } catch (ActivityNotFoundException e) {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/" + context.getResources().getString(R.string.facebook_username))));
            }
        }
    }

    public static void gotoinstagram(Context context) {
        if (context != null) {
            Uri uri = Uri.parse("https://www.instagram.com/" + context.getResources().getString(R.string.instagram_username));
            Intent data = new Intent(Intent.ACTION_VIEW, uri);
            data.setPackage("com.instagram.android");

            try {
                context.startActivity(data);
            } catch (ActivityNotFoundException e) {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/" + context.getResources().getString(R.string.instagram_username))));
            }
        }
    }

    public static void joinTelegram(Context context) {
        if (context != null) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=" + context.getString(R.string.telegram_username)));
                context.startActivity(intent);
            } catch (Exception e) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/" + context.getString(R.string.telegram_username))));

            }
        }
    }

    public static int getScreenWidth(Context context) {
        int columnWidth;
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();

        point.x = display.getWidth();
        point.y = display.getHeight();

        columnWidth = point.x;
        return columnWidth;
    }

    public static void errorDialog(Activity activity, String title, String message) {
        final androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(activity, R.style.ThemeDialog);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton(activity.getString(R.string.exit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        alertDialog.show();
    }

    public static void chk(Activity activity) {
        if (activity != null) {
            if (getPreference(activity, Prefs.SET_PREFERENCES, APP_CHECK, "").isEmpty()) {
                errorDialog(activity, "Purchase Verification Failed", "Please Contact The Owner");
            }
        }
    }

    public static void runApp(Activity activity) {
        getAdSettings(activity);
    }

    public static void getAdSettings(Activity activity) {
        Call<List<Settings>> call = Methods.getApi().getAdSettings("settings", BuildConfig.API_KEY);
        call.enqueue(new Callback<List<Settings>>() {
            @Override
            public void onResponse(@NonNull Call<List<Settings>> call,
                                   @NonNull Response<List<Settings>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Constant.AD_STATUS = response.body().get(0).getAd_status();
                    Constant.SHOW_INTER_ON_CLICKS = response.body().get(0).getInter_clicks();
                    Constant.AD_NETWORK = response.body().get(0).getAd_network();
                    Constant.ADMOB_SMALL_BANNER_AD_ID = response.body().get(0).getAdmob_small_banner();
                    Constant.ADMOB_BIG_BANNER_AD_ID = response.body().get(0).getAdmob_medium_banner();
                    Constant.ADMOB_INTER_AD_ID = response.body().get(0).getAdmob_inter();
                    Constant.ADMOB_NATIVE_AD_ID = response.body().get(0).getAdmob_native();
                    Constant.APPLOVIN_SMALL_BANNER_AD_ID = response.body().get(0).getAdmob_small_banner();
                    Constant.APPLOVIN_BIG_BANNER_AD_ID = response.body().get(0).getApplovin_medium_banner();
                    Constant.APPLOVIN_INTER_AD_ID = response.body().get(0).getApplovin_inter();
                    Constant.APPLOVIN_NATIVE_AD_ID = response.body().get(0).getApplovin_native();
                    Thread SplashThread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                sleep(3000);
                                activity.startActivity(new Intent(activity, MainActivity.class));
                                activity.finish();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    SplashThread.start();
                } else {
                    errorDialog(activity, activity.getString(R.string.connection_msg), activity.getString(R.string.check_internet));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Settings>> call, @NonNull Throwable t) {
                errorDialog(activity, activity.getString(R.string.connection_msg), activity.getString(R.string.check_internet));
            }
        });
    }

    public static void loadImage(ImageView imageView, String url, ProgressBar progressBar) {
        Glide.with(imageView.getContext()).asBitmap().load(url).centerCrop().into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                progressBar.setVisibility(View.GONE);
                imageView.setImageBitmap(resource);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        });
    }

    public static void loadImageNoCrop(ImageView imageView, String url, ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(imageView.getContext()).asBitmap().load(url).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                progressBar.setVisibility(View.GONE);
                imageView.setImageBitmap(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        });
    }

    public static void loadImageNoCropNoProg(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).asBitmap().load(url).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                imageView.setImageBitmap(resource);
                Constant.RADIO_ICON_BITMAP = resource;
                RadioService.notificationManager.changeIcon(Constant.RADIO_ICON_BITMAP);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        });
    }

    public static void setupSlider(Context context, ViewPager2 Slider, Handler sliderHandler, Runnable sliderRunnable) {
        if (context != null) {
            Slider.setOffscreenPageLimit(3);
            Slider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
            float pageMargin = context.getResources().getDimensionPixelOffset(R.dimen.pageMargin);
            float pageOffset = context.getResources().getDimensionPixelOffset(R.dimen.offset);
            CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
            compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                @Override
                public void transformPage(@NonNull View page, float position) {
                    float myOffset = position * -(2 * pageOffset + pageMargin);
                    if (position < -1) {
                        page.setTranslationX(-myOffset);
                    } else if (position <= 1) {
                        float scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0f));
                        page.setTranslationX(myOffset);
                        page.setScaleY(scaleFactor);
                        page.setAlpha(scaleFactor);
                    } else {
                        page.setAlpha(0);
                        page.setTranslationX(myOffset);
                    }
                }
            });
            Slider.setPageTransformer(compositePageTransformer);
            Slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    sliderHandler.removeCallbacks(sliderRunnable);
                    sliderHandler.postDelayed(sliderRunnable, Config.SLIDER_TIMER);
                }
            });
        }
    }

    public static void addSelectedFragment(Fragment fragment, Activity activity) {
        ((MainActivity) activity).closeSlidingPanel();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left).replace(R.id.fragment_container, fragment, null).addToBackStack(null).commit();
    }

    public static void postTokenToServer(Context context, String old_token, String new_token) {
        Call<List<UserToken>> call = Methods.getApi().addUserToken("addToken", BuildConfig.API_KEY, old_token, new_token);
        call.enqueue(new Callback<List<UserToken>>() {

            @Override
            public void onResponse(@NotNull Call<List<UserToken>> call, @NotNull Response<List<UserToken>> response) {
                //Toast.makeText(context, "token added successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NotNull Call<List<UserToken>> call, @NotNull Throwable t) {
                //Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String viewFormat(Integer number) {
        String[] suffix = new String[]{"k", "m", "b", "t"};
        int size = (number != 0) ? (int) Math.log10(number) : 0;
        if (size >= 3) {
            while (size % 3 != 0) {
                size = size - 1;
            }
        }
        double notation = Math.pow(10, size);
        return (size >= 3) ? +(Math.round((number / notation) * 100) / 100.0d) + suffix[(size / 3) - 1] : +number + "";
    }

    public static void playRadio(Activity activity, String[] strings) {
        Constant.songName = activity.getString(R.string.app_name);
        Constant.RADIO_ID = Integer.parseInt(strings[0]);
        Constant.RADIO_NAME = strings[1];
        Constant.RADIO_FREQUENCY = strings[2];
        Constant.RADIO_STREAM_URL = strings[3];
        Constant.RADIO_ICON = strings[4];
        Constant.RADIO_DESCRIPTION = strings[5];
        Constant.RADIO_CH_VIEWS = Integer.parseInt(strings[6]);
        RadioService.mediaSession.setMetadata(new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, Constant.RADIO_NAME)
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, activity.getString(R.string.now_playing))
                .build());
        RadioService.notificationManager.changeRadio(Constant.RADIO_NAME);
        ((MainActivity) activity).changeFrequency();
        postView(Constant.RADIO_ID);
    }

    public static void postView(int id) {
        Call<List<Channels>> call = Methods.getApi().postView("updateViews", BuildConfig.API_KEY, id);
        call.enqueue(new Callback<List<Channels>>() {

            @Override
            public void onResponse(@NotNull Call<List<Channels>> call, @NotNull Response<List<Channels>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().get(0).equals("error")) {
                        //Toast.makeText(activity, "View Update Success", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(activity, "View Update Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Channels>> call, @NotNull Throwable t) {
                //Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showBottomSheetDialog(Activity activity) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetDialog.setContentView(R.layout.item_description);
        ImageView btn_close = bottomSheetDialog.findViewById(R.id.btn_close);
        TextView tv_desc = bottomSheetDialog.findViewById(R.id.tv_desc);
        tv_desc.setText(Constant.RADIO_DESCRIPTION);
        bottomSheetDialog.show();
        btn_close.setOnClickListener(view -> bottomSheetDialog.dismiss());
    }

    public static void doReport(Activity activity) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetDialog.setContentView(R.layout.item_report);
        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ImageView btn_close = bottomSheetDialog.findViewById(R.id.btn_close);
        EditText et_report = bottomSheetDialog.findViewById(R.id.et_report);
        MaterialButton btn_send = bottomSheetDialog.findViewById(R.id.btn_send);
        bottomSheetDialog.show();
        btn_close.setOnClickListener(view -> bottomSheetDialog.dismiss());
        btn_send.setOnClickListener(view -> {
            String getET_REPORT = et_report.getText().toString();
            sendReport(getET_REPORT, activity, bottomSheetDialog);
        });

    }

    public static void sendReport(String report, Activity activity, BottomSheetDialog bd) {
        Call<List<Report>> call = Methods.getApi().doReport("report", BuildConfig.API_KEY, Constant.RADIO_ID, Constant.RADIO_NAME, report);
        call.enqueue(new Callback<List<Report>>() {

            @Override
            public void onResponse(@NotNull Call<List<Report>> call, @NotNull Response<List<Report>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().get(0).isError()) {
                        Toast.makeText(activity, activity.getString(R.string.report_sent_msg), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, activity.getString(R.string.report_failed_msg), Toast.LENGTH_SHORT).show();
                    }
                    bd.dismiss();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Report>> call, @NotNull Throwable t) {
                Toast.makeText(activity, activity.getString(R.string.report_failed_msg), Toast.LENGTH_SHORT).show();
                bd.dismiss();
            }
        });
    }

    public static void setMargins (Context context, View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

            final float scale = context.getResources().getDisplayMetrics().density;
            // convert the DP into pixel
            int l =  (int)(left * scale + 0.5f);
            int r =  (int)(right * scale + 0.5f);
            int t =  (int)(top * scale + 0.5f);
            int b =  (int)(bottom * scale + 0.5f);

            p.setMargins(l, t, r, b);
            view.requestLayout();
        }
    }
}

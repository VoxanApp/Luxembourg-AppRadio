package com.andromob.amradio.utils;

import static android.view.View.GONE;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.andromob.amradio.R;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.sdk.AppLovinSdkUtils;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

public class AdsManager {
    public static int inter_i = 1;
    public static InterstitialAd googleFullscreen;
    public static NativeAd mnativeAd;
    public static MaxInterstitialAd maxInterstitialAd;
    public static MaxNativeAdLoader nativeAdLoader;
    public static MaxAd maxNativeAd;

    public static void showBannerAd(Activity activity, AdView banner_admob, MaxAdView maxSmallBanner, FrameLayout adContainerView) {
        if (Constant.AD_STATUS.equals("on")) {
            if (Constant.AD_NETWORK.equalsIgnoreCase("admob")) {
                loadAdMOBBanner(activity, banner_admob, adContainerView);
            } else {
                showMaxBannerAd(activity, maxSmallBanner, adContainerView);
            }
        } else {
            adContainerView.setVisibility(GONE);
        }
    }

    public static void showBigBannerAd(Activity activity, AdView big_banner_admob, MaxAdView
            maxBigBanner, FrameLayout adContainerView) {
        if (Constant.AD_STATUS.equals("on")) {
            if (Constant.AD_NETWORK.equalsIgnoreCase("admob")) {
                loadAdMOBBigBanner(activity, big_banner_admob, adContainerView);
            } else {
                showMaxBigBannerAd(activity, maxBigBanner, adContainerView);
            }
        } else {
            adContainerView.setVisibility(GONE);
        }
    }

    public static void showNativeAd(Activity activity, FrameLayout adContainerNative) {
        if (activity != null) {
            if (Constant.AD_STATUS.equals("on")) {
                if (Constant.AD_NETWORK.equalsIgnoreCase("admob")) {
                    AdsManager.nativeAdLoaderAdMob(activity, adContainerNative);
                } else {
                    showMaxNativeAd(activity, adContainerNative);
                }
            } else {
                adContainerNative.setVisibility(GONE);
            }
        }
    }

    public static void showInterAd(Activity activity) {
        if (activity != null) {
            if (Constant.AD_STATUS.equals("on")) {
                if (inter_i == Constant.SHOW_INTER_ON_CLICKS) {
                    inter_i = 0;
                    if (Constant.AD_NETWORK.equalsIgnoreCase("admob")) {
                        if (googleFullscreen != null) {
                            googleFullscreen.show(activity);
                            loadInterAd(activity);
                        } else {
                            loadInterAd(activity);
                        }
                    } else {
                        if (maxInterstitialAd != null) {
                            if (maxInterstitialAd.isReady()) {
                                maxInterstitialAd.showAd(Constant.APPLOVIN_INTER_AD_ID);
                            } else {
                                loadInterAd(activity);
                            }
                        } else {
                            loadInterAd(activity);
                        }
                    }
                } else {
                    inter_i++;
                }
            }
        }
    }

    public static void showInterAdOnClick(Activity activity, String[] strings) {
        if (activity != null) {
            if (Constant.AD_STATUS.equals("on")) {
                if (inter_i == Constant.SHOW_INTER_ON_CLICKS) {
                    inter_i = 0;
                    if (Constant.AD_NETWORK.equals("admob")) {
                        if (googleFullscreen != null) {
                            googleFullscreen.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    super.onAdDismissedFullScreenContent();
                                    loadInterAd(activity);
                                    Methods.playRadio(activity, strings);
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                    super.onAdFailedToShowFullScreenContent(adError);
                                    loadInterAd(activity);
                                    Methods.playRadio(activity, strings);
                                }
                            });
                            googleFullscreen.show(activity);
                        } else {
                            loadInterAd(activity);
                            Methods.playRadio(activity, strings);
                        }
                    } else {
                        if (maxInterstitialAd != null) {
                            if (maxInterstitialAd.isReady()) {
                                maxInterstitialAd.setListener(new MaxAdListener() {
                                    @Override
                                    public void onAdLoaded(MaxAd ad) {

                                    }

                                    @Override
                                    public void onAdDisplayed(MaxAd ad) {

                                    }

                                    @Override
                                    public void onAdHidden(MaxAd ad) {
                                        Methods.playRadio(activity, strings);
                                        loadInterAd(activity);
                                    }

                                    @Override
                                    public void onAdClicked(MaxAd ad) {

                                    }

                                    @Override
                                    public void onAdLoadFailed(String adUnitId, MaxError error) {
                                        Methods.playRadio(activity, strings);
                                        loadInterAd(activity);
                                    }

                                    @Override
                                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                                        Methods.playRadio(activity, strings);
                                        loadInterAd(activity);
                                    }
                                });
                                maxInterstitialAd.showAd(Constant.APPLOVIN_INTER_AD_ID);
                            } else {
                                loadInterAd(activity);
                                Methods.playRadio(activity, strings);
                            }
                        } else {
                            loadInterAd(activity);
                            Methods.playRadio(activity, strings);
                        }
                    }
                } else {
                    inter_i++;
                    Methods.playRadio(activity, strings);
                }
            } else {
                Methods.playRadio(activity, strings);
            }
        }
    }

    public static void loadInterAd(Activity activity) {
        if (activity != null) {
            if (Constant.AD_NETWORK.equalsIgnoreCase("admob")) {
                FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Proceed to the next level.
                        googleFullscreen = null;
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                    }
                };
                InterstitialAd.load(
                        activity,
                        Constant.ADMOB_INTER_AD_ID,
                        new AdRequest.Builder().build(),
                        new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull InterstitialAd ad) {
                                googleFullscreen = ad;
                                googleFullscreen.setFullScreenContentCallback(fullScreenContentCallback);
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                googleFullscreen = null;
                                inter_i = 0;
                            }
                        });
            } else {
                maxInterstitialAd = new MaxInterstitialAd(Constant.APPLOVIN_INTER_AD_ID, activity);
                maxInterstitialAd.setListener(new MaxAdListener() {
                    @Override
                    public void onAdLoaded(MaxAd ad) {
                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {
                        loadInterAd(activity);
                        inter_i = 0;
                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {
                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    }
                });
                maxInterstitialAd.loadAd();
            }
        }
    }

    public static void loadAdMOBBanner(Activity activity, AdView small_adView, FrameLayout
            adContainerView) {
        if (activity != null) {
            small_adView = new AdView(activity);
            small_adView.setAdUnitId(Constant.ADMOB_SMALL_BANNER_AD_ID);
            adContainerView.setVisibility(View.VISIBLE);
            adContainerView.removeAllViews();
            adContainerView.addView(small_adView);
            AdRequest adRequest =
                    new AdRequest.Builder().build();
            AdSize adSize = getAdSize(activity);
            small_adView.setAdSize(adSize);
            small_adView.loadAd(adRequest);
        }
    }

    public static void loadAdMOBBigBanner(Activity activity, AdView big_adView, FrameLayout
            adContainerView) {
        if (activity != null) {
            big_adView = new AdView(activity);
            big_adView.setAdUnitId(Constant.ADMOB_BIG_BANNER_AD_ID);
            adContainerView.setVisibility(View.VISIBLE);
            adContainerView.removeAllViews();
            adContainerView.addView(big_adView);
            AdRequest adRequest =
                    new AdRequest.Builder().build();
            big_adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
            big_adView.loadAd(adRequest);
        }
    }

    public static AdSize getAdSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

    public static void showMaxBannerAd(Activity activity, MaxAdView maxSmallBanner, FrameLayout
            frameLayout) {
        frameLayout.setVisibility(GONE);
        maxSmallBanner = new MaxAdView(Constant.APPLOVIN_SMALL_BANNER_AD_ID, activity);
        maxSmallBanner.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                frameLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                frameLayout.setVisibility(GONE);
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

            }
        });
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int heightDp = MaxAdFormat.BANNER.getAdaptiveSize(activity).getHeight();
        int heightPx = AppLovinSdkUtils.dpToPx(activity, heightDp);

        maxSmallBanner.setLayoutParams(new FrameLayout.LayoutParams(width, heightPx));
        maxSmallBanner.setExtraParameter("adaptive_banner", "true");
        frameLayout.addView(maxSmallBanner);
        maxSmallBanner.loadAd();
    }

    public static void showMaxBigBannerAd(Activity activity, MaxAdView
            maxBigBanner, FrameLayout frameLayout) {
        frameLayout.setVisibility(GONE);
        maxBigBanner = new MaxAdView(Constant.APPLOVIN_BIG_BANNER_AD_ID, activity);
        maxBigBanner.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                frameLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                frameLayout.setVisibility(GONE);
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

            }
        });
        int widthPx = AppLovinSdkUtils.dpToPx(activity, 300);
        int heightPx = AppLovinSdkUtils.dpToPx(activity, 250);
        maxBigBanner.setLayoutParams(new FrameLayout.LayoutParams(widthPx, heightPx));
        frameLayout.addView(maxBigBanner);
        maxBigBanner.loadAd();
    }

    public static void showMaxNativeAd(Activity activity, FrameLayout nativeAdContainer) {
        if (activity != null) {
            nativeAdLoader = new MaxNativeAdLoader(Constant.APPLOVIN_NATIVE_AD_ID, activity);
            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                @Override
                public void onNativeAdLoaded(MaxNativeAdView nativeAdView, MaxAd ad) {
                    // Clean up any pre-existing native ad to prevent memory leaks.
                    if (maxNativeAd != null) {
                        nativeAdLoader.destroy(maxNativeAd);
                    }
                    maxNativeAd = ad;
                    // Save ad for cleanup.

                    // Add ad view to view.
                    nativeAdContainer.removeAllViews();
                    nativeAdContainer.addView(nativeAdView);
                    nativeAdContainer.setVisibility(View.VISIBLE);
                }

                @Override
                public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                    // We recommend retrying with exponentially higher delays up to a maximum delay
                }

                @Override
                public void onNativeAdClicked(final MaxAd ad) {
                    // Optional click callback
                }
            });

            nativeAdLoader.loadAd();
        }
    }

    public static void nativeAdLoaderAdMob(Activity activity, FrameLayout adContainerNative) {
        if (activity != null) {
            adContainerNative.setVisibility(View.VISIBLE);
            AdLoader.Builder builder = new AdLoader.Builder(activity, Constant.ADMOB_NATIVE_AD_ID)
                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            NativeAdView adView = (NativeAdView) activity.getLayoutInflater()
                                    .inflate(R.layout.ads_native_admob, null);
                            if (mnativeAd != null) {
                                mnativeAd.destroy();
                            }
                            mnativeAd = nativeAd;
                            populateNativeAdView(adView, mnativeAd);
                            adContainerNative.removeAllViews();
                            adContainerNative.addView(adView);
                        }
                    });

            AdLoader adLoader = builder.build();
            adLoader.loadAd(new AdRequest.Builder().build());
        }
    }

    public static void populateNativeAdView(NativeAdView adView, NativeAd nativeAd) {
        // Set the media view.
        adView.setMediaView(adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);
    }

    public static void destroyBannerAds(AdView admob_small_adView, MaxAdView maxSmallBanner) {
        if (Constant.AD_STATUS.equals("on")) {
            if (maxSmallBanner != null) {
                maxSmallBanner.removeAllViews();
                maxSmallBanner.destroy();
            }
            if (admob_small_adView != null) {
                admob_small_adView.removeAllViews();
                admob_small_adView.destroy();
            }
        }
    }

    public static void destroyBigBannerAds(AdView big_banner_admob, MaxAdView maxBigBanner) {
        if (Constant.AD_STATUS.equals("on")) {
            if (big_banner_admob != null) {
                big_banner_admob.removeAllViews();
                big_banner_admob.destroy();
            }
            if (maxBigBanner != null) {
                maxBigBanner.removeAllViews();
                maxBigBanner.destroy();
            }
        }
    }

    public static void destroyNativeAds() {
        if (Constant.AD_STATUS.equals("on")) {
            if (mnativeAd != null) {
                mnativeAd.destroy();
            }
            if (nativeAdLoader != null) {
                nativeAdLoader.destroy(maxNativeAd);
            }
        }
    }

    public static void destroyInterAds() {
        if (Constant.AD_STATUS.equals("on")) {
            if (googleFullscreen != null) {
                googleFullscreen = null;
            }
            if (maxInterstitialAd != null) {
                maxInterstitialAd.destroy();
                maxInterstitialAd = null;
            }
        }
    }
}

package com.andromob.amradio.utils;

import static com.andromob.amradio.utils.Config.LATEST_CHANNEL_LIMIT;
import static com.andromob.amradio.utils.Config.MOST_VIEWED_CHANNEL_LIMIT;
import static com.andromob.amradio.utils.Config.SLIDER_LIMIT;

import android.view.View;

import androidx.annotation.NonNull;

import com.andromob.amradio.BuildConfig;
import com.andromob.amradio.models.Category;
import com.andromob.amradio.models.Channels;
import com.andromob.amradio.views.CategoryViews;
import com.andromob.amradio.views.ChannelViews;
import com.andromob.amradio.views.HomeDashViews;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter {
    private View views;

    private CategoryViews categoryViews;
    private ChannelViews channelViews;
    private HomeDashViews homeDashViews;

    public Presenter(View views) {
        this.views = views;
    }

    public Presenter(CategoryViews categoryViews) {
        this.categoryViews = categoryViews;
    }

    public Presenter(HomeDashViews homeDashViews, ChannelViews channelViews) {
        this.homeDashViews = homeDashViews;
        this.channelViews = channelViews;
    }

    public Presenter(ChannelViews channelViews) {
        this.channelViews = channelViews;
    }

    public void getCategory(String type) {
        Call<List<Category>> call = null;
        if (type.equals("home")) {
            homeDashViews.showLoading();
            call = Methods.getApi().getLatestCategory("category", Config.LATEST_CATEGORY_LIMIT, BuildConfig.API_KEY);
        } else {
            categoryViews.showLoading();
            call = Methods.getApi().getCategory("category", BuildConfig.API_KEY);
        }
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call,
                                   @NonNull Response<List<Category>> response) {
                if (type.equals("home")) {
                    homeDashViews.hideLoading();
                } else {
                    categoryViews.hideLoading();
                }
                if (response.isSuccessful() && response.body() != null) {
                    if (type.equals("home")) {
                        homeDashViews.setRecentCategory(response.body());
                    } else {
                        categoryViews.setCategory(response.body());
                    }
                } else {
                    if (type.equals("home")) {
                        homeDashViews.onErrorLoading(response.message());
                    } else {
                        categoryViews.onErrorLoading(response.message());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                if (type.equals("home")) {
                    homeDashViews.hideLoading();
                    homeDashViews.onErrorLoading("please try again");
                } else {
                    categoryViews.hideLoading();
                    categoryViews.onErrorLoading("please try again");
                }
                call.cancel();
            }
        });
    }

    public void getChannelsByCat(int id) {
        channelViews.showLoading();
        Call<List<Channels>> call = Methods.getApi().getChannelsByCat("channels", id, BuildConfig.API_KEY);
        call.enqueue(new Callback<List<Channels>>() {
            @Override
            public void onResponse(@NonNull Call<List<Channels>> call,
                                   @NonNull Response<List<Channels>> response) {
                channelViews.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    channelViews.setChannels(response.body());
                } else {
                    channelViews.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Channels>> call, @NonNull Throwable t) {
                channelViews.hideLoading();
                channelViews.onErrorLoading("please try again");
                call.cancel();
            }
        });
    }

    public void getLatestChannels() {
        channelViews.showLoading();
        Call<List<Channels>> call = Methods.getApi().getLatestChannels("channels", LATEST_CHANNEL_LIMIT, BuildConfig.API_KEY);
        call.enqueue(new Callback<List<Channels>>() {
            @Override
            public void onResponse(@NonNull Call<List<Channels>> call,
                                   @NonNull Response<List<Channels>> response) {
                channelViews.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    channelViews.setChannels(response.body());
                } else {
                    channelViews.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Channels>> call, @NonNull Throwable t) {
                channelViews.hideLoading();
                channelViews.onErrorLoading("please try again");
                call.cancel();
            }
        });
    }

    public void getFavChannels(String fav) {
        channelViews.showLoading();
        Call<List<Channels>> call = Methods.getApi().getFavChannels("channels", fav, BuildConfig.API_KEY);
        call.enqueue(new Callback<List<Channels>>() {
            @Override
            public void onResponse(@NonNull Call<List<Channels>> call,
                                   @NonNull Response<List<Channels>> response) {
                channelViews.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    channelViews.setChannels(response.body());
                } else {
                    channelViews.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Channels>> call, @NonNull Throwable t) {
                channelViews.hideLoading();
                channelViews.onErrorLoading("please try again");
                call.cancel();
            }
        });
    }

    public void getslider() {
        homeDashViews.showLoading();
        Call<List<Channels>> call = Methods.getApi().getSlider("channels", SLIDER_LIMIT, BuildConfig.API_KEY);
        call.enqueue(new Callback<List<Channels>>() {
            @Override
            public void onResponse(@NonNull Call<List<Channels>> call,
                                   @NonNull Response<List<Channels>> response) {
                homeDashViews.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    homeDashViews.setSlider(response.body());
                } else {
                    homeDashViews.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Channels>> call, @NonNull Throwable t) {
                homeDashViews.hideLoading();
                homeDashViews.onErrorLoading("please try again");
                call.cancel();
            }
        });
    }

    public void getMostViewedChannels() {
        homeDashViews.showLoading();
        Call<List<Channels>> call = Methods.getApi().getMostViewedChannels("channels", MOST_VIEWED_CHANNEL_LIMIT, BuildConfig.API_KEY);
        call.enqueue(new Callback<List<Channels>>() {
            @Override
            public void onResponse(@NonNull Call<List<Channels>> call,
                                   @NonNull Response<List<Channels>> response) {
                homeDashViews.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    homeDashViews.setMostViewed(response.body());
                } else {
                    homeDashViews.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Channels>> call, @NonNull Throwable t) {
                homeDashViews.hideLoading();
                homeDashViews.onErrorLoading("please try again");
                call.cancel();
            }
        });
    }


}

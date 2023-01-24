package lu.voxhost.LuxoRadio.utils;

import static lu.voxhost.LuxoRadio.utils.Config.LATEST_CHANNEL_LIMIT;
import static lu.voxhost.LuxoRadio.utils.Config.MOST_VIEWED_CHANNEL_LIMIT;
import static lu.voxhost.LuxoRadio.utils.Config.SLIDER_LIMIT;

import android.view.View;

import androidx.annotation.NonNull;

import lu.voxhost.LuxoRadio.BuildConfig;
import lu.voxhost.LuxoRadio.models.Category;
import lu.voxhost.LuxoRadio.models.Channels;
import lu.voxhost.LuxoRadio.views.CategoryViews;
import lu.voxhost.LuxoRadio.views.ChannelViews;
import lu.voxhost.LuxoRadio.views.HomeDashViews;

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

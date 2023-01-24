package com.andromob.amradio.views;

import com.andromob.amradio.models.Category;
import com.andromob.amradio.models.Channels;

import java.util.List;

public interface HomeDashViews {
    void showLoading();
    void hideLoading();
    void onErrorLoading(String message);
    void setSlider(List<Channels> dataList);
    void setRecentCategory(List<Category> dataList);
    void setMostViewed(List<Channels> dataList);
}

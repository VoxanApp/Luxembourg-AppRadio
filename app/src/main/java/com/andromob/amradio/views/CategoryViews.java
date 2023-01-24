package com.andromob.amradio.views;

import com.andromob.amradio.models.Category;

import java.util.List;

public interface CategoryViews {
    void showLoading();
    void hideLoading();
    void onErrorLoading(String message);
    void setCategory(List<Category> dataList);
}

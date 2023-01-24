package lu.voxhost.LuxoRadio.views;

import lu.voxhost.LuxoRadio.models.Category;

import java.util.List;

public interface CategoryViews {
    void showLoading();
    void hideLoading();
    void onErrorLoading(String message);
    void setCategory(List<Category> dataList);
}

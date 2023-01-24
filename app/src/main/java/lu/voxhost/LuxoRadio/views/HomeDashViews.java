package lu.voxhost.LuxoRadio.views;

import lu.voxhost.LuxoRadio.models.Category;
import lu.voxhost.LuxoRadio.models.Channels;

import java.util.List;

public interface HomeDashViews {
    void showLoading();
    void hideLoading();
    void onErrorLoading(String message);
    void setSlider(List<Channels> dataList);
    void setRecentCategory(List<Category> dataList);
    void setMostViewed(List<Channels> dataList);
}

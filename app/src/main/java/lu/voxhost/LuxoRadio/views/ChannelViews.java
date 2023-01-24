package lu.voxhost.LuxoRadio.views;

import lu.voxhost.LuxoRadio.models.Channels;

import java.util.List;

public interface ChannelViews {
    void showLoading();
    void hideLoading();
    void onErrorLoading(String message);
    void setChannels(List<Channels> dataList);
}

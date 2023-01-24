package com.andromob.amradio.views;

import com.andromob.amradio.models.Channels;

import java.util.List;

public interface ChannelViews {
    void showLoading();
    void hideLoading();
    void onErrorLoading(String message);
    void setChannels(List<Channels> dataList);
}

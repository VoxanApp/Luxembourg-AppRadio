package com.andromob.amradio.models;

public class Channels {
    private int id, cid, views;
    private String channel_name, frequency, description, category_name, channel_icon, channel_banner, source_url;

    public Channels(int id, int cid, int views, String channel_name, String frequency, String description, String category_name, String channel_icon, String channel_banner, String source_url) {
        this.id = id;
        this.cid = cid;
        this.views = views;
        this.channel_name = channel_name;
        this.frequency = frequency;
        this.description = description;
        this.category_name = category_name;
        this.channel_icon = channel_icon;
        this.channel_banner = channel_banner;
        this.source_url = source_url;
    }

    public int getId() {
        return id;
    }

    public int getCid() {
        return cid;
    }

    public int getViews() {
        return views;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getChannel_icon() {
        return channel_icon;
    }

    public String getChannel_banner() {
        return channel_banner;
    }

    public String getSource_url() {
        return source_url;
    }
}

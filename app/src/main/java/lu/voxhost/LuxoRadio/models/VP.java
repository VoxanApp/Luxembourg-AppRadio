package com.andromob.amradio.models;

public class VP {
    private boolean error;
    private int p_count;
    private String message;

    public VP(boolean error, int p_count) {
        this.error = error;
        this.p_count = p_count;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public int getP_count() {
        return p_count;
    }

    public String getmessage() {
        return message;
    }
}

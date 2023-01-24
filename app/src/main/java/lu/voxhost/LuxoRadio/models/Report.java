package com.andromob.amradio.models;

public class Report {
    private boolean error;
    private String message;

    public Report(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}

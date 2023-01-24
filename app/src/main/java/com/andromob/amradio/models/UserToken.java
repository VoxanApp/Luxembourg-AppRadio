package com.andromob.amradio.models;

public class UserToken {
    boolean error;

    private int id;
    private String token,message;

    public UserToken(boolean error, int id, String token, String message) {
        this.error = error;
        this.id = id;
        this.token = token;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}

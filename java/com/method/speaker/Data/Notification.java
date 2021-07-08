package com.method.speaker.Data;

import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("title")
    public String title;
    @SerializedName("message")
    public String message;
    @SerializedName("mode")
    public int mode;

    @Override
    public String toString() {
        return "Notification{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

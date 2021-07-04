package com.method.speaker.Data;

import com.google.gson.annotations.SerializedName;

public class Member {

    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;
    @SerializedName("channel")
    public String channel;

    @Override
    public String toString() {
        return "Admin{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

}

package com.method.speaker.Data;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;
    @SerializedName("channel")
    public String channel;
    @SerializedName("member")
    public boolean member = true;

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
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

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", channel='" + channel + '\'' +
                ", member=" + member +
                '}';
    }
}

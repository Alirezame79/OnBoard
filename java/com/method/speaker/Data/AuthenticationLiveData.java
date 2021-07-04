package com.method.speaker.Data;

import androidx.lifecycle.ViewModel;

public class AuthenticationLiveData extends ViewModel {

    // member side
    public static boolean member;
    public static String username;
    public static String password;
    public static String channel;

    public static boolean isMember() {
        return member;
    }
    public static void setMember(boolean member) {
        AuthenticationLiveData.member = member;
    }
    public static String getUsername() {
        return username;
    }
    public static void setUsername(String username) {
        AuthenticationLiveData.username = username;
    }
    public static String getPassword() {
        return password;
    }
    public static void setPassword(String password) {
        AuthenticationLiveData.password = password;
    }
    public static String getChannel() {
        return channel;
    }
    public static void setChannel(String channel) {
        AuthenticationLiveData.channel = channel;
    }


    // channel side
    public static String name;
    public static String address;
    public static int memberCount = 0;
    public static String imageUrl;

    public static String getName() {
        return name;
    }
    public static void setName(String name) {
        AuthenticationLiveData.name = name;
    }
    public static String getAddress() {
        return address;
    }
    public static void setAddress(String address) {
        AuthenticationLiveData.address = address;
    }
    public static int getMemberCount() {
        return memberCount;
    }
    public static void setMemberCount(int memberCount) {
        AuthenticationLiveData.memberCount = memberCount;
    }
    public static String getImageUrl() {
        return imageUrl;
    }
    public static void setImageUrl(String imageUrl) {
        AuthenticationLiveData.imageUrl = imageUrl;
    }
}
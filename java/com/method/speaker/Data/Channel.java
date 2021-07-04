package com.method.speaker.Data;

import com.google.gson.annotations.SerializedName;

public class Channel {

    @SerializedName("name")
    String name;
    @SerializedName("address")
    String address;
    @SerializedName("memberCount")
    int memberCount = 0;
    @SerializedName("imageUrl")
    String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", memberCount=" + memberCount +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

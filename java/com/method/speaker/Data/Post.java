package com.method.speaker.Data;

import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("id")
    public int id;
    @SerializedName("author")
    public String poster;
    @SerializedName("text")
    public String post;
    @SerializedName("date")
    public String detail;
    @SerializedName("likes")
    public int likes;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", poster='" + poster + '\'' +
                ", post='" + post + '\'' +
                ", detail='" + detail + '\'' +
                ", likes=" + likes +
                '}';
    }
}

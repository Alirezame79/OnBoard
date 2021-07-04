package com.method.speaker.Retrofit;

import com.method.speaker.Data.Admin;
import com.method.speaker.Data.Channel;
import com.method.speaker.Data.Member;
import com.method.speaker.Data.Post;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyAPI {

    @GET("starting.txt")
    Call<String> getAppSituation();

    @POST("memberUserPass.php")
    Call<ArrayList<Member>> getMemberChannelName(@Query("username") String username, @Query("password") String password);

    @POST("adminUserPass.php")
    Call<ArrayList<Admin>> getAdminChannelAccess(@Query("username")String username, @Query("password")String password);

    @POST("getChannelInfo.php")
    Call<ArrayList<Channel>> getChannel(@Query("name")String name);

    @POST("getPostList.php")
    Call<ArrayList<Post>> getAllPosts(@Query("channel")String channel);

    @POST("getMembersList.php")
    Call<ArrayList<Member>> getMembersForAdmin(@Query("channel")String channel);

    @POST("addPost.php")
    Call<String> sendNewPost(@Query("channel")String channel, @Query("author")String poster, @Query("text")String post, @Query("date")String date);
}

package com.method.speaker.Retrofit;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Global extends Application {

    public static Context context;
    public static Retrofit retrofit;
    public static MyAPI myAPI;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        if (retrofit == null){
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder().baseUrl(BaseUrl.BASEURL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        myAPI = retrofit.create(MyAPI.class);
    }

    public static Context getContext() {
        return context;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static MyAPI getMyAPI() {
        return myAPI;
    }
}

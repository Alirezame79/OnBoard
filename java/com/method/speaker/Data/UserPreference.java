package com.method.speaker.Data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class UserPreference {

    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    // Constructor
    public UserPreference(Context context){
        sharedPreferences = context.getSharedPreferences("user_secret_data", context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void putUserData(User user){
        Gson gson = new Gson();
        String userDataJson = gson.toJson(user, User.class);
        editor.putString("user_data", userDataJson);
        editor.apply();
    }

    public User getUserData(){
        String userDataJson = sharedPreferences.getString("user_data", null);
        if (userDataJson == null){
            return null;
        }

        Gson gson = new Gson();
        return gson.fromJson(userDataJson, User.class);
    }
}

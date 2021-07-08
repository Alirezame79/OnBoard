package com.method.speaker.NewInventions;

import android.util.Log;

public class FindUsername {

    public static String text;

    public static String achieveUsername(String text){
        FindUsername.text = text;
        String[] strings = text.split("Username:");
        Log.d("TAF", "part one " + strings[1]);
        String[] strings1 = strings[1].replaceAll("[\\n ]", "").split("Doyouaccept?");
        Log.d("TAF", "part two " + strings1[0]);
        return strings1[0];
    }
}

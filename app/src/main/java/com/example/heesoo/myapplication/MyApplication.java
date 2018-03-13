package com.example.heesoo.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by riyariya on 2018-03-12.
 */

public class MyApplication extends Application {
    private static Context applicationContext;
    @Override
    public void onCreate(){
        super.onCreate();
        applicationContext = getApplicationContext();
    }

    public static Context getContext(){
        return applicationContext;
    }

    public static String getCurrentUser(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return sharedPreferences.getString("CURRENT_USER", null);
    }

    public static void setCurrentUser(String currentUser){
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.getContext())
                .edit();
        editor.putString("CURRENT_USER", currentUser).apply();
    }
}

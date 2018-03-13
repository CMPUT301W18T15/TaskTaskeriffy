package com.example.heesoo.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by heesoopark on 2018-03-13.
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

    public static String getCurrentMode(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return sharedPreferences.getString("CURRENT_MODE", null);
    }

    public static void setCurrentMode(String currentMode){
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.getContext())
                .edit();
        editor.putString("CURRENT_MODE", currentMode).apply();
    }

}
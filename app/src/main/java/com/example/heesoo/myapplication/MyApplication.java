package com.example.heesoo.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by heesoopark on 2018-03-13.
 */

public class MyApplication extends Application {
    private static Context applicationContext;
    private static User currentUser;
    private static SharedPreferences sharedPreferences;
    @Override
    public void onCreate(){
        super.onCreate();
        applicationContext = getApplicationContext();
        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);

    }

    public static Context getContext(){
        return applicationContext;
    }

    public static User getCurrentUser(){
        //return sharedPreferences.getString("CURRENT_USER", null);
        return currentUser;
    }

    public static void setCurrentUser(User user){
        // SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        //     .edit();
        //editor.putString("CURRENT_USER", currentUser).apply();
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefEditor.putString("MyUser", json);
        prefEditor.commit();
    }

    public static User getCurrentMode(){
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        //return sharedPreferences.getString("CURRENT_MODE", null);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("MyUser", "");
        User user = gson.fromJson(json, User.class);
        return user;
    }

    public static void setCurrentMode(String currentMode){
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(MyApplication.getContext())
                .edit();
        editor.putString("CURRENT_MODE", currentMode).apply();
    }

}
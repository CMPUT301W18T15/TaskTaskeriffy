package com.example.heesoo.myapplication.SetCurrentUser;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;

import com.example.heesoo.myapplication.Entities.User;

import com.google.gson.Gson;

/**
 * Created by heesoopark on 2018-03-13.
 */

/*
This class is used to keep a track of the shared preferences that are needed amongst most other activities.
Upon login, the current user is set so it may be easily looked up through the class method "getCurrentUser".
Similarly current mode is also a shared preference that may be used in future.
 */

public class SetCurrentUser extends MultiDexApplication {
    private static Context applicationContext;
    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate(){
        super.onCreate();
        applicationContext = getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SetCurrentUser.getContext());

    }

    public static Context getContext(){
        return applicationContext;
    }

    public static User getCurrentUser(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("MyUser", "");
        return gson.fromJson(json, User.class);
    }

    public static void setCurrentUser(User currentUser){
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(currentUser);
        prefEditor.putString("MyUser", json);
        prefEditor.commit();
    }

    public static String getCurrentMode(){
        return sharedPreferences.getString("CURRENT_MODE", null);
    }

    public static void setCurrentMode(String currentMode){
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(SetCurrentUser.getContext())
                .edit();
        editor.putString("CURRENT_MODE", currentMode).apply();
    }

    public static Context getCurrentContext(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("MyContext", "");
        return gson.fromJson(json, Context.class);
    }

    public static void setCurrentContext(Context currentContext){
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(currentContext);
        prefEditor.putString("MyContext", json);
        prefEditor.commit();
    }


}
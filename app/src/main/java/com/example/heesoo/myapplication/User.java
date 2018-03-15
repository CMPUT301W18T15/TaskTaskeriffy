package com.example.heesoo.myapplication;

import android.media.Image;
import android.support.annotation.NonNull;

import java.io.Serializable;

import io.searchbox.annotations.JestId;

/**
 * Created by heesoopark on 2018-03-12.
 */

public class User implements Comparable<User>, Serializable {

    private String username, password, emailAddress, phoneNumber;
    transient Image picture;

    @JestId
    private String id = null;

    public User(String username, String pwd, String emailAddress, String phoneNumber) {
        this.username = username;
        this.password = pwd;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public User() {

    }

    public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) { this.password = password; }

    public String getPassword() { return password; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String ad) {
        this.emailAddress = ad;
    }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phonenum) {
        this.phoneNumber = phonenum;
    }

    public Image getPicture() { return picture; }

    public void setPicture(Image picture) { this.picture = picture; }


    @Override
    public int compareTo(@NonNull User user) {
        //return this.getName().toLowerCase().compareTo(user.getName().toLowerCase());
        return 0;
    }
}

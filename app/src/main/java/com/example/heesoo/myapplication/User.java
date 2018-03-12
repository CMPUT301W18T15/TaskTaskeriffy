package com.example.heesoo.myapplication;

import android.media.Image;

/**
 * Created by chengze on 2018/2/25.
 */

public class User {

    private String username;
    private String password;
    private String emailAddress;
    private String address;
    private Image profilePicture; //MAKE DEFAULT PICTURE

    public User(String username, String password, String emailAddress, String address) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.address = address;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getEmail(){
        return emailAddress;
    }
    public void setEmail(String emailAddress){
        this.emailAddress = emailAddress;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public Image getProfilePicture() { return profilePicture; }
    public void setProfilePicture(Image p) { profilePicture = p; }
}

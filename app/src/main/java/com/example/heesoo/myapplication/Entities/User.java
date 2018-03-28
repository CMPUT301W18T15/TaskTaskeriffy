package com.example.heesoo.myapplication.Entities;

import android.media.Image;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

import io.searchbox.annotations.JestId;

public class User implements Comparable<User>, Serializable {

    private String username, password, emailAddress, phoneNumber;

    private ArrayList<Task> tempProTaskList;

    private ArrayList<Task> tempReqTaskList;

    transient Image picture;

    @JestId
    private String id = null;




    /**
     * <p>
     * Every individual that uses the app must register an account ot login to their own account.
     * These accounts are represented by the following class i.e. the User class.
     *</p>
     * <pre>
     * @parm username      a unique string that represents the user uses to login
     * @parm pwd           a string that represents the password associated with the username
     * @parm emailAddress  a string that represents the user's email address
     * @parm phoneNumber   a string that represents the user's phone number
     *
     * @author      Manuela Key Marichales
     * @since       1.0
     * </pre>
     */

    public User(String username, String pwd, String emailAddress, String phoneNumber) {
        this.username = username;
        this.password = pwd;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public User() {

    }

    /**
     * <p>
     *     This method returns the string that corresponds to the username of the user
     * </p>
     *
     * @return string that represents the User that has requested this user
     */
    public String getUsername() {
        return username; }

    /**
     * <p>
     *     This method sets the local variable "username" to the string that is entered to the method
     *     through the parameters
     * </p>
     * @param username string that represents the username associated with this user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * <p>
     *     This method sets the local variable "password" to the string that is entered to the method
     *     through the parameters
     * </p>
     * @param password string that represents the password associated with this user.
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * <p>
     *     This method returns the string that corresponds to the password of the user
     * </p>
     *
     * @return string that represents the password that is associated with this user
     */
    public String getPassword() { return password; }

    /**
     * <p>
     *     This method returns the string that corresponds to the unique id of the user
     * </p>
     *
     * @return string that represents the unique id that is associated with this user
     */
    public String getId() {
        return id;
    }

    /**
     * <p>
     *     This method sets the local variable "id" to the string that is entered to the method
     *     through the parameters
     * </p>
     * @param id string that represents the unique id associated with this user.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * <p>
     *     This method returns the string that corresponds to the email address of the user
     * </p>
     *
     * @return string that represents the email address that is associated with this user
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * <p>
     *     This method sets the local variable "emailAddress" to the string that is entered to the method
     *     through the parameters
     * </p>
     * @param ad string that represents the email address associated with this user.
     */
    public void setEmailAddress(String ad) {
        this.emailAddress = ad;
    }

    /**
     * <p>
     *     This method returns the string that corresponds to the phone number of the user
     * </p>
     *
     * @return string that represents the phone number that is associated with this user
     */
    public String getPhoneNumber() { return phoneNumber; }

    /**
     * <p>
     *     This method sets the local variable "phoneNumber" to the string that is entered to the method
     *     through the parameters
     * </p>
     * @param phone_number string that represents the password associated with this user.
     */
    public void setPhoneNumber(String phone_number) {
        this.phoneNumber = phone_number;
    }

    /**
     * <p>
     *     This method returns the Image that corresponds to the profile picture of the user
     * </p>
     *
     * @return Image that represents the profile picture that is associated with this user
     */
    public Image getPicture() { return picture; }

    /**
     * <p>
     *     This method sets the local variable "picture" to the Image that is entered to the method
     *     through the parameters
     * </p>
     * @param picture Image that represents the profile picture associated with this user.
     */
    public void setPicture(Image picture) { this.picture = picture; }



// for offline behavior =============
    public ArrayList<Task> getTempProTaskList() {
        return tempProTaskList;
    }

    public void setTempProTaskList(ArrayList<Task> list){
        tempProTaskList = list;
    }

    public ArrayList<Task> getTempReqTaskList() {
        return tempReqTaskList;
    }

    public void setTempReqTaskList(ArrayList<Task> list) {
        this.tempReqTaskList = list;
    }
//===========
    @Override
    public int compareTo(@NonNull User user) {
        //return this.getName().toLowerCase().compareTo(user.getName().toLowerCase());
        return 0;
    }

}

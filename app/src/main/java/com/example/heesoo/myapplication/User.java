package com.example.heesoo.myapplication;

import android.support.annotation.NonNull;

import io.searchbox.annotations.JestId;

/**
 * Created by heesoopark on 2018-03-12.
 */

public class User implements Comparable<User> {

    private String name, username, email, address;

    @JestId
    private String id;

    public User(String name, String username, String email, String address) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public int compareTo(@NonNull User user) {
        return this.getName().toLowerCase().compareTo(user.getName().toLowerCase());
    }
}

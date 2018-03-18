package com.example.heesoo.myapplication.Entities;

/**
 * Created by chengze on 2018/2/26.
 */

public class TaskProvider extends User {
    private TaskList myTasks;

    public TaskProvider(String Username, String Password, String emailAddress, String phoneNumber) {
        super(Username, Password, emailAddress, phoneNumber);
        myTasks = new TaskList();
    }

    public TaskList getListofTasks() {

        return myTasks;
    }

}
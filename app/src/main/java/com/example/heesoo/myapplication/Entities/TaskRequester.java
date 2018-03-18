package com.example.heesoo.myapplication.Entities;


/**
 * Created by chengze on 2018/2/26.
 */

public class TaskRequester extends User {
    private TaskList myTasks;

    public TaskRequester(String Username, String Password, String emailAddress, String phoneNumber) {
        super(Username, Password, emailAddress, phoneNumber);
        myTasks = new TaskList();
    }

    public TaskList getListofTasks() {

        return myTasks;
    }

}
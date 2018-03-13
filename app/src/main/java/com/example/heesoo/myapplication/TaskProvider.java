package com.example.heesoo.myapplication;

import android.media.Image;

import java.util.ArrayList;

/**
 * Created by chengze on 2018/2/26.
 */

public class TaskProvider extends User {
    private TaskList myTasks;

    public TaskProvider(String Username, String Password, String emailAddress, String Address) {
        super(Username, Password, emailAddress, Address);
        myTasks = new TaskList();
    }

    public void addTask(Task t) {

        myTasks.addTask(t);
    }

    public void removeTask(Task t) {

        myTasks.removeTask(t);
    }

    public Task getTask(Task t) {

        return myTasks.getTask(t);
    }

    public TaskList getTasks() {

        return myTasks;
    }

    public TaskList getAssignedTasks() {

        return myTasks.getAssignedTasks();
    }

    public TaskList getBiddedTasks() {

        return myTasks.getBiddedTasks();
    }

    public TaskList getCompletedTasks() {

        return myTasks.getCompletedTasks();
    }
}

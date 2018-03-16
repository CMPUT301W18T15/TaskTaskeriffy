package com.example.heesoo.modelclasses;

/**
 * Created by chengze on 2018/2/26.
 */

public class TaskRequester extends User {
    private TaskList myTasks;

    public TaskRequester(String Username, String Password, String emailAddress, String phoneNumber) {
        super(Username, Password, emailAddress, phoneNumber);
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

    public TaskList getAcceptedTasks() {

        return myTasks.getAcceptedTasks();
    }
}
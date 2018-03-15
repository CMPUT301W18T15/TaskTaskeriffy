package com.example.heesoo.myapplication;

import java.util.ArrayList;

/**
 * Created by manuelakm on 2018-03-12.
 */

public class TaskList {

    private ArrayList<Task> tasks;

    public TaskList() {

        tasks = new ArrayList<Task>();
    }

    public void addTask(Task t) {

        tasks.add(t);
    }

    public void removeTask(Task t) {

        tasks.remove(t);
    }

    public Task getTask(Task t) {

        int index = tasks.indexOf(t);

        if(index == -1) {
            return null;
        }
        return tasks.get(index);
    }

    public Task getTask(int i) {

        return tasks.get(i);

    }

    public TaskList getBiddedTasks() {

        TaskList tl = new TaskList();

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getStatus() == "Bidded") {
                tl.addTask(tasks.get(i));
            }
        }
        return tl;
    }

    public TaskList getAssignedTasks() {

        TaskList tl = new TaskList();

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getStatus() == "Assigned") {
                tl.addTask(tasks.get(i));
            }
        }
        return tl;
    }

    public TaskList getCompletedTasks() {

        TaskList tl = new TaskList();

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getStatus() == "Completed") {
                tl.addTask(tasks.get(i));
            }
        }
        return tl;
    }

    public TaskList getAcceptedTasks() {

        TaskList tl = new TaskList();

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getStatus() == "Accepted") {
                tl.addTask(tasks.get(i));
            }
        }
        return tl;
    }

    public int getSize() {
        return tasks.size();
    }
}

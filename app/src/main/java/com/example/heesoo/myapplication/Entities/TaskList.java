package com.example.heesoo.myapplication.Entities;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by manuelakm on 2018-03-12.
 */

public class TaskList {

    private ArrayList<Task> tasks;

    /**
     * <p>
     *  A constructor that creates a new TaskList, which contains an ArrayList<Task>.
     *</p>
     *
     * @author      Manuela Key Marichales
     * @since       1.0
     */
    public TaskList() {

        tasks = new ArrayList<Task>();
    }

    /**
     * <p>
     *     This method adds the given Task to the local ArrayList<Task>
     * </p>
     * @param t Task that is the task that must be added to the taskList
     */
    public void addTask(Task t) {

        tasks.add(t);
    }

    /**
     * <p>
     *     This method removes the given task from the local ArrayList<Task>
     * </p>
     * @param t Task that is the task that must be removed from the taskList
     */
    public void removeTask(Task t) {

        tasks.remove(t);
    }

    /**
     * <p>
     *     This method returns the task that was passed into the method if it exists and returns null
     *     if the task does not exist
     * </p>
     * @param t Task that is to be returned from the TaskList
     * @return Task that is returned from the method
     */
    public Task getTask(Task t) {

        int index = tasks.indexOf(t);

        if(index == -1) {
            return null;
        }
        return tasks.get(index);
    }

    /**
     * <p>
     *     This method returns the task at a particular index of the taskList
     * </p>
     * @param i int that represents the index of the TaskList
     * @return Task that is at the given index
     */
    public Task getTask(int i) {

        return tasks.get(i);

    }

    /**
     * <p>
     *     This method returns all the tasks in the list with the status "Bidded"
     * </p>
     * @return TaskList that contains all the tasks with the status "Bidded"
     */
    @SuppressLint("NewApi")
    public TaskList getBiddedTasks() {

        TaskList tl = new TaskList();

        for (int i = 0; i < tasks.size(); i++) {
            if (Objects.equals(tasks.get(i).getStatus(), "Bidded")) {
                tl.addTask(tasks.get(i));
            }
        }
        return tl;
    }

    /**
     * <p>
     *     This method returns all the tasks in the list with the status "Assigned"
     * </p>
     * @return TaskList that contains all the tasks with the status "Assigned"
     */
    @SuppressLint("NewApi")
    public TaskList getAssignedTasks() {

        TaskList tl = new TaskList();

        for (int i = 0; i < tasks.size(); i++) {
            if (Objects.equals(tasks.get(i).getStatus(), "Assigned")) {
                tl.addTask(tasks.get(i));
            }
        }
        return tl;
    }

    /**
     * <p>
     *     This method returns all the tasks in the list with the status "Completed"
     * </p>
     * @return TaskList that contains all the tasks with the status "Completed"
     */
    @SuppressLint("NewApi")
    public TaskList getCompletedTasks() {

        TaskList tl = new TaskList();

        for (int i = 0; i < tasks.size(); i++) {
            if (Objects.equals(tasks.get(i).getStatus(), "Completed")) {
                tl.addTask(tasks.get(i));
            }
        }
        return tl;
    }

    /**
     * <p>
     *     This method returns all the tasks in the list with the status "Accepted"
     * </p>
     * @return TaskList that contains all the tasks with the status "Accepted"
     */
    @SuppressLint("NewApi")
    public TaskList getAcceptedTasks() {

        TaskList tl = new TaskList();

        for (int i = 0; i < tasks.size(); i++) {
            if (Objects.equals(tasks.get(i).getStatus(), "Accepted")) {
                tl.addTask(tasks.get(i));
            }
        }
        return tl;
    }

    /**
     * <p>
     *     The method that returns the number of tasks in the the taskList
     * </p>
     * @return int that represents the number of tasks in the taskList
     */
    public int getSize() {
        return tasks.size();
    }
}

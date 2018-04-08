package com.example.heesoo.myapplication.entities;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by manuelakm on 2018-03-12.
 */

public class TaskList implements Serializable {

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
     * @param t Task that must be added to the TaskList
     */
    public void addTask(Task t) {

        tasks.add(t);
    }

    /**
     * <p>
     *     This method removes the given task from the TaskList
     * </p>
     * @param t Task that must be removed from the TaskList
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
     * @return Task that is returned from the method (null if the task is not contained in the TqskList)
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
    public TaskList getBiddedTasks() {

        TaskList tl = new TaskList();

        for (int i = 0; i < tasks.size(); i++) {
            if ( tasks.get(i).getStatus().equals("Bidded")) {
                tl.addTask(tasks.get(i));
            }
        }
        return tl;
    }

    /**
     * <p>
     *     This method adds all the tasks in the collection provided to this TaskList
     * </p>
     * @param t Collection of tasks that will be added to the TaskList
     */
    public void addAll(Collection<Task> t) {

        tasks.addAll(t);
    }

    /**
     * <p>
     *     This method returns all the tasks in the list with the status "Assigned"
     * </p>
     * @return TaskList that contains all the tasks with the status "Assigned"
     */
    public TaskList getAssignedTasks() {

        TaskList tl = new TaskList();

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getStatus().equals("Assigned") ) {
                tl.addTask(tasks.get(i));
            }
        }
        return tl;
    }

    /**
     * <p>
     *     The method that returns all the tasks that are available; i.e. with the statuses "Requested" or "Bidded"
     * </p>
     * @return TaskList that contains all the tasks with status "Requested" or "Bidded"
     */
    public TaskList getAvailableTasks() {

        TaskList tl = new TaskList();

        for (int i = 0; i < tasks.size(); i++) {
            if ( (tasks.get(i).getStatus()).equals("Requested") && (tasks.get(i).getStatus()).equals("Bidded") ) {
                tl.addTask(tasks.get(i));
            }
        }
        return tl;
    }

    /**
     * <p>
     *     The method returns all the tasks in the list with the status "Completed"
     * </p>
     * @return TaskList that contains all the tasks with the status "Completed"
     */
    public TaskList getCompletedTasks() {

        TaskList tl = new TaskList();

        for (int i = 0; i < tasks.size(); i++) {
            if ( tasks.get(i).getStatus().equals("Completed")) {
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
    public TaskList getAcceptedTasks() {

        TaskList tl = new TaskList();

        for (int i = 0; i < tasks.size(); i++) {
            if ( tasks.get(i).getStatus().equals("Accepted")) {
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

    /**
     * <p>
     *     The method that empties the TaskList
     * </p>
     */
    public void clear() {
        tasks.clear();
    }
}

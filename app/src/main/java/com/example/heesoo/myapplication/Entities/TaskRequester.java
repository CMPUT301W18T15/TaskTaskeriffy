package com.example.heesoo.myapplication.Entities;


/**
 * Created by chengze on 2018/2/26.
 */

public class TaskRequester extends User {
    private TaskList myTasks;

    /**
     * <p>
     * Every individual that uses the app must register an account ot login to their own account.
     * These accounts are represented by the User class. The user class is extended by TaskRequester.
     * Every TaskRequester contains a TaskList that represents all of the tasks associated with the
     * taskRequester are.
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
    public TaskRequester(String Username, String Password, String emailAddress, String phoneNumber) {
        super(Username, Password, emailAddress, phoneNumber);
        myTasks = new TaskList();
    }


    /**
     * <p>
     * A method that returns all of the tasks that are associated with the taskRequester i.e. all
     * the tasks that the taskRequester has requested etc...
     *</p>
     * <pre>
     * @return a TaskList of all the tasks associated with this taskRequester
     * </pre>
     */
    public TaskList getListofTasks() {

        return myTasks;
    }

}
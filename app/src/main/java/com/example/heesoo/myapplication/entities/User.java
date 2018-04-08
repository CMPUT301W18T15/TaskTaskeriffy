package com.example.heesoo.myapplication.entities;

import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.shared_preferences.SetPublicCurrentUser;

import java.io.Serializable;

import io.searchbox.annotations.JestId;

public class User implements Serializable {

    private String username, password, emailAddress, phoneNumber;
    private Double rating, totalEarnings;
    private Integer completedProvidedTasks, completedPostedTasks;

    transient Image picture;

    private TaskList requesterTasks = new TaskList();
    private TaskList providerTasks = new TaskList();

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
        this.rating = 0.00;
        this.completedPostedTasks = 0;
        this.completedProvidedTasks = 0;
        this.totalEarnings = 0.00;
    }

    /**
     * <p>
     *     A constructor that creates an empty User object
     * </p>
     */
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
    public void setPassword(String password) {

        this.password = password; }

    /**
     * <p>
     *     This method returns the string that corresponds to the password of the user
     * </p>
     *
     * @return string that represents the password that is associated with this user
     */
    public String getPassword() {
        return password; }

    /**
     * <p>
     *     This method returns the number of tasks that are performed by the user
     * </p>
     *
     * @return integer corresponding to the number of tasks performed by the user
     */
    public Integer getCompletedProvidedTasks(){
        return completedProvidedTasks;}

    /**
     * <p>
     *     This method increases the count for the number of completed tasks performed by the user
     * </p>
     */
    public void updateCompletedProvidedTasks(){

        this.completedProvidedTasks += 1;
    }

    /**
     * <p>
     *     This method returns the number of completed tasks that were posted by this user
     * </p>
     *
     * @return integer corresponding to the number of user's tasks that were completed
     */
    public Integer getCompletedPostedTasks(){
        return completedPostedTasks;}

    /**
     * <p>
     *     This method increases the count for the number of completed tasks posted by this user
     * </p>
     */
    public void updateCompletedPostedTasks(){

        this.completedPostedTasks += 1;
    }

    /**
     * <p>
     *     This method returns the double that represents this user's rating
     * </p>
     *
     * @return Double that represents the user's rating
     */
    public Double getRating() {

        return rating;
    }

    /**
     * <p>
     *     The method that assigned the user's rating to the one provided by the parameter
     * </p>
     * @param rating Double that represents the new rating of this user
     */
    public void setRating(Double rating) {

        this.rating = rating;
    }

    /**
     * <p>
     *     The method that updates the user's rating by finding the average of all previous ratings
     * </p>
     * @param rating Double that represents the new rating given to a user
     */
    public void updateRating(Double rating) {

        Double updatedRatings = ((getRating() * (this.completedProvidedTasks-1)) + rating) / this.completedProvidedTasks;
        setRating(updatedRatings);
    }

    /**
     * <p>
     *     This method returns the user's total earnings thus far
     * </p>
     *
     * @return Double that represents user's total earnings thus far
     */
    public Double getTotalEarnings(){
        return totalEarnings;}

    /**
     * <p>
     *     This method adds the parameter provided to the user's total earnings
     * </p>
     * @param earnings Double that represents the new earnings of a user
     */
    public void updateTotalEarnings(Double earnings){

        this.totalEarnings += earnings;
    }

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
    public String getPhoneNumber() {
        return phoneNumber; }

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
     *     A method that stores all the user's tasks in order to enable offline activity. It stores
     *     all the information about tasks upon successful login and separates them into requester and
     *     provider tasks.
     * </p>
     */
    public void initializeOffline(){

        TaskList allTasks;
        ElasticSearchTaskController.GetAllTasks getAllTasks = new ElasticSearchTaskController.GetAllTasks();
        getAllTasks.execute("");

        try {
            allTasks = getAllTasks.get();

            for(int i = 0; i < allTasks.getSize(); i++) {

                Task task = allTasks.getTask(i);
                if (this.getUsername().equals(task.getTaskRequester())){
                    requesterTasks.addTask(task);
                }

                for(int j = 0; j < allTasks.getSize(); j++){
                    if ( allTasks.getTask(i).getStatus().equals("Assigned") && allTasks.getTask(i).getTaskProvider().equals(SetPublicCurrentUser.getCurrentUser().getUsername())) {
                        providerTasks.addTask(allTasks.getTask(i));
                    }
                }
            }
        }
        catch (Exception e) {
            Log.i("Error", "The request for tweets failed in onStart");
        }
    }

    /**
     * <p>
     *     Update the database with newly added and edited tasks that were modified while offline
     * </p>
     */
    public void sync(){
        for(int i = 0; i < requesterTasks.getSize(); i++) {
            Task task = requesterTasks.getTask(i);

            ElasticSearchTaskController.GetTask getTask = new ElasticSearchTaskController.GetTask();
            getTask.execute(task.getId());
            Task currentTask;
            try{
                currentTask = getTask.get();

                if(currentTask == null){
                    ElasticSearchTaskController.AddTask addTasksTask = new ElasticSearchTaskController.AddTask();
                    addTasksTask.execute(task);
                }else{
                    ElasticSearchTaskController.EditTask editTask = new ElasticSearchTaskController.EditTask();
                    currentTask.setTaskName(task.getTaskName());
                    currentTask.setTaskDescription(task.getTaskDescription());
                    editTask.execute(currentTask);
                }
            }
            catch (Exception e){
                Log.i("Error", "Cannot find the task!");
            }
        }
        requesterTasks.clear();
        providerTasks.clear();

        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TaskList allTasks;
        ElasticSearchTaskController.GetAllTasks getAllTasks = new ElasticSearchTaskController.GetAllTasks();
        getAllTasks.execute("");
        try {
            allTasks = getAllTasks.get();

            for(int i = 0; i < allTasks.getSize(); i++) {

                Task task = allTasks.getTask(i);
                if (this.getUsername().equals(task.getTaskRequester())){
                    requesterTasks.addTask(task);
                }
                if ( task.getStatus().equals("Assigned") && task.getTaskProvider().equals(SetPublicCurrentUser.getCurrentUser().getUsername())) {
                    providerTasks.addTask(task);
                }
            }
        }
        catch (Exception e) {
            Log.i("Error", "The request for tweets failed in onStart");
        }

    }

    /**
     * <p>
     *     A method that returns the TaskList of all requester tasks
     * </p>
     * @return TaskList that contains all the requesterTasks associated with this user
     */
    public TaskList getRequesterTasks(){

        return requesterTasks;
    }

    /**
     * <p>
     *     A method that returns the TaskList of all provider tasks
     * </p>
     * @return TaskList that contains all the providerTasks associated with this user
     */
    public TaskList getProviderTasks(){

        return providerTasks;
    }

    /**
     * <p>
     *     A method that adds a task from the requesterTasks TaskList
     * </p>
     * @param task Task that is to be added to the requesterTasks TaskList
     */
    public void addRequesterTasks(Task task){

        requesterTasks.addTask(task);
    }

    /**
     * <p>
     *     A method that adds a task from the providerTasks TaskList
     * </p>
     * @param task Task that is to be added to the providerTasks TaskList
     */
    public void addProviderTasks(Task task){

        providerTasks.addTask(task);
    }

    /**
     * <p>
     *     A method that deletes a task from the requesterTasks TaskList
     * </p>
     * @param task Task that is to be deleted from the requesterTasks TaskList
     */
    public void deleteRequesterTasks(Task task){
        TaskList tempTasks = new TaskList();

        for (int i = 0; i < requesterTasks.getSize(); i++) {
            Task deletedTask = requesterTasks.getTask(i);

            if (!deletedTask.getId().equals(task.getId())){
                tempTasks.addTask(deletedTask);
            }
        }
        requesterTasks = tempTasks;
    }

    /**
     * <p>
     *     A method that deletes a task from the providerTasks TaskList
     * </p>
     * @param task Task that is to be deleted from the providerTasks TaskList
     */
    public void deleteProviderTasks(Task task) {
        TaskList tempTasks = new TaskList();

        for (int i = 0; i < providerTasks.getSize(); i++) {
            Task deletedTask = providerTasks.getTask(i);

            if (!deletedTask.getId().equals(task.getId())){
                tempTasks.addTask(deletedTask);
            }
        }
        providerTasks = tempTasks;
    }
}

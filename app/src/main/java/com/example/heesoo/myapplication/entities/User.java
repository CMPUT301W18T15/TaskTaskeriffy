package com.example.heesoo.myapplication.entities;

import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.shared_preferences.SetPublicCurrentUser;

import java.io.Serializable;
import java.util.ArrayList;

import io.searchbox.annotations.JestId;

public class User implements Comparable<User>, Serializable {

    private String username, password, emailAddress, phoneNumber;
    private Double rating, totalEarnings;
    private Integer completedProvidedTasks, completedPostedTasks;

    transient Image picture;

    private ArrayList<Task> requesterTasks = new ArrayList<Task>();
    private ArrayList<Task> providerTasks = new ArrayList<Task>();

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
    public void setPassword(String password) { this.password = password; }

    /**
     * <p>
     *     This method returns the string that corresponds to the password of the user
     * </p>
     *
     * @return string that represents the password that is associated with this user
     */
    public String getPassword() { return password; }

    /**
     * <p>
     *     This method returns the number of tasks that are provided by the user
     * </p>
     *
     * @return integer corresponding to the number of tasks finished by the user
     */
    public Integer getCompletedProvidedTasks(){ return completedProvidedTasks;}

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
    public Integer getCompletedPostedTasks(){ return completedPostedTasks;}

    public void updateCompletedPostedTasks(){
        this.completedPostedTasks += 1;
    }

    /**
     * <p>
     *     This method returns the user's rating
     * </p>
     *
     * @return double, user's rating from 5 stars
     */
    public Double getRating(){ return rating;}

    public void setRating(Double rating){this.rating = rating;}

    public void updateRating(Double rating){
        Double updatedRatings = ((getRating() * (this.completedProvidedTasks-1)) + rating) / this.completedProvidedTasks;
        setRating(updatedRatings);
    }

    /**
     * <p>
     *     This method returns the user's earnings so far
     * </p>
     *
     * @return double, user's total earnings so far
     */
    public Double getTotalEarnings(){ return totalEarnings;}

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
    public String getPhoneNumber() { return phoneNumber; }

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
     *     This method returns the Image that corresponds to the profile picture of the user
     * </p>
     *
     * @return Image that represents the profile picture that is associated with this user
     */
    public Image getPicture() { return picture; }

    /**
     * <p>
     *     This method sets the local variable "picture" to the Image that is entered to the method
     *     through the parameters
     * </p>
     * @param picture Image that represents the profile picture associated with this user.
     */
    public void setPicture(Image picture) { this.picture = picture; }


    @Override
    public int compareTo(@NonNull User user) {
        //return this.getName().toLowerCase().compareTo(user.getName().toLowerCase());
        return 0;
    }

    public void initializeOffline(){
        // offline behavior
        // get the data as login successfully
        ArrayList<Task> allTasks;
        ElasticSearchTaskController.GetAllTasks getAllTasks = new ElasticSearchTaskController.GetAllTasks();
        getAllTasks.execute("");

        try {
            allTasks = getAllTasks.get();

            for (Task task : allTasks){
                if (this.getUsername().equals(task.getUserName())){
                    Log.d("REQUESTCODE", task.getTaskName());
                    requesterTasks.add(task);
                }

                for(int i = 0; i < allTasks.size(); i++){
                    if ( allTasks.get(i).getStatus().equals("Assigned") && allTasks.get(i).getTaskProvider().equals(SetPublicCurrentUser.getCurrentUser().getUsername())) {
                        providerTasks.add(allTasks.get(i));
                    }
                }
            }
        }
        catch (Exception e) {
            Log.i("Error", "The request for tweets failed in onStart");
        }
    }

    public void sync(){
        for (Task task : requesterTasks){
            ElasticSearchTaskController.GetTask getTask = new ElasticSearchTaskController.GetTask();
            getTask.execute(task.getId());
            Task currentTask;
            try{
                currentTask = getTask.get();
                // push
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

        // pull
        ArrayList<Task> allTasks;
        ElasticSearchTaskController.GetAllTasks getAllTasks = new ElasticSearchTaskController.GetAllTasks();
        getAllTasks.execute("");
        try {
            allTasks = getAllTasks.get();
            for (Task task : allTasks){
                if (this.getUsername().equals(task.getUserName())){
                    Log.d("REQUESTCODE", task.getTaskName());
                    requesterTasks.add(task);
                }
                if ( task.getStatus().equals("Assigned") && task.getTaskProvider().equals(SetPublicCurrentUser.getCurrentUser().getUsername())) {
                    providerTasks.add(task);
                }
            }
        }
        catch (Exception e) {
            Log.i("Error", "The request for tweets failed in onStart");
        }

    }

    public ArrayList<Task> getRequesterTasks(){
        return requesterTasks;
    }

    public ArrayList<Task> getProviderTasks(){
        return providerTasks;
    }

    public void addRequesterTasks(Task task){
        requesterTasks.add(task);
    }

    public void addProviderTasks(Task task){
        providerTasks.add(task);
    }

    public void deleteRequesterTasks(Task task){
        ArrayList<Task> tempTasks = new ArrayList<Task>();
        for (Task deletedTask : requesterTasks){
            if (!deletedTask.getId().equals(task.getId())){
                tempTasks.add(deletedTask);
            }
        }
        requesterTasks = tempTasks;
    }

    public void deleteProviderTasks(Task task){
        ArrayList<Task> tempTasks = new ArrayList<Task>();
        for (Task deletedTask : providerTasks){
            if (!deletedTask.getId().equals(task.getId())){
                tempTasks.add(deletedTask);
            }
        }
        providerTasks = tempTasks;
    }
}

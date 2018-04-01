package com.example.heesoo.myapplication.Entities;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Base64;

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchTaskController;

import io.searchbox.annotations.JestId;

public class Task implements Serializable{
    private String taskRequester; // Changing to just attach the username for making MainActivity getCurrentUser work in the constructor for task in ElasticSearchTaskController.
    private String taskName;
    private String taskDescription;
    private String assignedTaskProvider;
    private String status;
    private Double latitude;
    private Double longitude;

    @JestId
    private String id;
    private ArrayList<String> pictures; // photo bitmaps are encoded to base64 strings to store in elasticsearch
    private ArrayList<Bid> bids;
    private ArrayList<User> taskBidders;

    /**
     *<p>
     *     This method is a constructor that creates a new instance of the Task class. As well as
     *     assigning variables to the provided parameters, it also sets the status to the default
     *     value "Requested" and sets assignedTaskProvider to the default value "".
     *</p>
     * @param taskRequester
     * @param taskName
     * @param taskDescription
     *
     * @author      Manuela Key Marichales
     * @since       1.0
     *
     */
    public Task(String taskRequester, String taskName, String taskDescription) {
        this.taskRequester = taskRequester;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = "Requested";
        this.bids = new ArrayList<Bid>();
        this.pictures = new ArrayList<String>();
        this.taskBidders = new ArrayList<User>();
        this.assignedTaskProvider = "";
        this.latitude = -1.0;
        this.longitude = -1.0;
    }

    public Task() {
    }

    /**
     * <p>
     *     This method returns the string that represents the username of the user that has
     *     requested this activity
     * </p>
     *
     * @return string that represents the User that has requested this task
     */
    public String getUserName(){

        return taskRequester;
    }

    /**
     * <p>
     *     This method returns the string that represents the username of the user that has
     *     been assigned to perform this task
     * </p>
     *
     * @return string that represents the username of the user assigned to perform this activity
     */
    public String getTaskProvider(){

        return assignedTaskProvider;
    }

    /**
     * <p>
     *     This method replaces the local value of this variable with the value passed to the method.
     * </p>
     * @param taskProvider string that represents the username of the user that has been assigned to
     *                     perform this activity
     */
    public void setTaskProvider(String taskProvider){

        this.assignedTaskProvider = taskProvider;
    }

    /**
     * <p>
     *     This methods returns the string that corresponds to the give taskName of this task.
     * </p>
     *
     * @return string that represents the name given to this task
     */
    public String getTaskName(){

        return taskName;
    }

    /**
     * <p>
     *     This method sets the variable taskName to the string passed into the method.
     * </p>
     * @param taskName string that represents the name given to this task
     */
    public void setTaskName(String taskName){

        this.taskName = taskName;
    }

    /**
     * <p>
     *     This method returns the string that corresponds to the task description given to this task
     * </p>
     * @return string that represents the description given to this task
     */
    public String getTaskDescription(){

        return taskDescription;
    }

    /**
     * <p>
     *     This method replaces the local value of this variable with the value passed to the method.
     * </p>
     * @param taskDescription string that represents the description given to this task
     */
    public void setTaskDescription(String taskDescription) {

        this.taskDescription = taskDescription;
    }

    /**
     * <p>
     *     This method returns the string that represents the current status of the task
     * </p>
     * @return string that represents the status of the task
     */
    public String getStatus() {

        return status;
    }

    /**
     * <p>
     *     This method replaces the local value of this variable with the value passed to the method.
     * </p>
     * @param status string that represents the new status of the task
     */
    public void setStatus(String status) {

        this.status = status;
    }

    /**
     * <p>
     *     A method that returns the string that represents the unique ID assigned this task
     * </p>
     * @return string that represents the unique id assigned to this task
     */
    public String getId() {

        return id;
    }

    /**
     * <p>
     *     This method replaces the local value of this variable with the value passed to the method.
     * </p>
     * @param id string that represents the unique id assigned to the task
     */
    public void setId(String id) {

        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }


    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * <p>
     *     This method adds a bid to the local ArrayList<Bid> that represents all the bids placed
     *     on the task and changes the status of Task to "Bidded". This method also uses
     *     elasticsearchcontroller to update the database.
     * </p>
     * @param bid a Bid that has been placed on this task
     */
    public void addBid(Bid bid) {

        bids.add(bid);
        this.status = "Bidded";
        ElasticSearchTaskController.EditTask editTask = new ElasticSearchTaskController.EditTask();
        editTask.execute(this);
    }

    /**
     * <p>
     *     This method deletes the bid from the local ArrayList<Bid> that represents all the bids
     *     placed on the task and changes the status to "Requested" if there are no other bids on
     *     this task. This method also uses elasticsearchcontroller to update the database.
     * </p>
     * @param recieved_bid a Bid that is to be deleted from this task
     */
    public void deleteBid(Bid recieved_bid) {

        // TODO fix remove bid from task
        if (bids.contains(recieved_bid)) {
            int index = bids.indexOf(recieved_bid);
            if (bids.get(index).getId() == recieved_bid.getId()) {
                bids.remove(index);
            }
        }

        if (bids.isEmpty()){
            this.status = "Requested";
            ElasticSearchTaskController.EditTask editTask = new ElasticSearchTaskController.EditTask();
            editTask.execute(this);
        }
    }

    /**
     *<p>
     *     This method deletes all bids associated with this task from the ArrayList<Bid>
     *</p>
     */
    public void deleteAllBids() {
        this.bids.clear();
    }


    /**
     * <p>
     *     This method returns an ArrayList<Bid> that represents all the bids associated with this task
     * </p>
     * @return ArrayList<Bid>< that represents all the bids associated with this task
     */
    public ArrayList<Bid> getBids(){

        return bids;
    }

    /**
     * <p>
     *     This method returns an ArrayList<Bitmap> that represents all the pictures associated with this task
     * </p>
     * @return ArrayList<Bitmap>< that represents all the pictures associated with this task
     */
    public ArrayList<String> getPictures(){

        return pictures;
    }

    /**
     * <p>
     *     This method adds a picture to the local ArrayList<Bitmap> that represents all the pictures placed
     *     on the task. This method also uses elasticsearchcontroller to update the database.
     * </p>
     * @param picture a Bitmap that has been placed on this task
     */
    public void addPicture(String encodedPicture) {

        pictures.add(encodedPicture);
    }

    public String getPicture() {
        return pictures.get(0);
    }

    /**
     * <p>
     *     This method accepts a bid placed on a task by assigning the string passed to the method
     *     to the "assignedTaskProvider" variable. This method also updates this task entry on the database.
     * </p>
     * @param taskProvider a string that represents the username of the user assigned to perform this task
     */
    public void acceptBid(String taskProvider) {

        assignedTaskProvider = taskProvider;
        this.setStatus("Assigned");
        ElasticSearchTaskController.EditTask editTask = new ElasticSearchTaskController.EditTask();
        editTask.execute(this);
    }

    /**
     * <p>
     *     This method returns a string that represents this task.
     * </p>
     * @return  string that represents the task as a string
     */
    public String toString() {

        return  "Name: " + taskName +" \n Status: " + status;
    }

    /**
     * <p>
     * A method that transverses all the bids placed on a specific task and returns the value of the
     * lowest bid placed on this particular task.
     *</p>
     * <pre>
     *  @return minValue   the string conversion of the lowest bid placed on the given Task
     * </pre>
     */
    public String getLowestBid() {

        if (bids.isEmpty()){
            return "Null";
        }
        else {
            Float minValue = bids.get(0).getBidPrice();
            for (Bid bid : bids) {
                if (bid.getBidPrice() < minValue) {
                    minValue = bid.getBidPrice();
                }
            }
            return minValue.toString();
        }

    }
}

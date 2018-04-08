package com.example.heesoo.myapplication.entities;

import java.io.Serializable;
import java.util.Calendar;

import io.searchbox.annotations.JestId;

public class Bid implements Serializable {

    private String taskName;
    private String description;
    private String status;
    private String taskRequester;
    private String taskProvider;
    private Float bidPrice;
    private Calendar timeStamp;

    @JestId
    private String id;

    /**
     * <p>
     * A constructor that creates a new bid when given the following four parameters. The default value
     * of a newly created bid's status is "Placed" and the timeStamp is set to the current time.
     *</p>
     * <pre>
     * @parm taskProvider     a string that represents the unique username of the user than has bid on this task
     * @parm taskName         a string that represents the task name
     * @parm taskDescription  a string that represents the task description
     * @parm bidPrice         a float that represents the price of the bid the taskProvider has placed on this task
     * </pre>
     * @author      Manuela Key Marichales
     * @since       1.0
     */
    public Bid(String taskName, String description, Float bidPrice, String taskProvider, String taskRequester) {

        this.taskName = taskName;
        this.taskProvider = taskProvider;
        this.taskRequester = taskRequester;
        this.description = description;
        this.status = "Placed";
        this.bidPrice = bidPrice;
        this.timeStamp = Calendar.getInstance();
    }

    /**
     * <p>
     *     This method returns a string object that corresponds to the name of the task that this bid is placed on.
     * </p>
     * @return string object that represents the name of this task
     */
    public String getTaskName(){

        return taskName;
    }

    /**
     * <p>
     *     This method replaces the taskName attribute with the parameter provided.
     * </p>
     * @param taskName string object that represents the name of the task that this bid is placed on.
     */
    public void setTaskName(String taskName){

        this.taskName = taskName;
    }

    /**
     * <p>
     *     This method returns a string object that corresponds to the description of the task that this bid is placed on.
     * </p>
     * @return string that represents the description of the task this bid is placed on
     */
    public String getTaskDescription(){

        return description;
    }

    /**
     * <p>
     *     This method replaces the taskDescription attribute with the parameter provided.
     * </p>
     * @param description string that represents the description of the task that this bid is placed on
     */
    public void setTaskDescription(String description){

        this.description = description;
    }

    /**
     * <p>
     *     This method returns the string that corresponds to the status of this bid.
     * </p>
     * @return string that represents the status of this bid
     */
    public String getStatus(){

        return status;
    }

    /**
     * <p>
     *     This method replaces the value of the status attribute with the parameter provided.
     * </p>
     * @param status string that represents the status of this bid
     */
    public void setStatus(String status) {

        this.status = status;
    }

    /**
     * <p>
     *     This method returns the string that corresponds to the username of task provider that has
     *     placed this bid.
     * </p>
     * @return string that represents the username of task provider that has placed this bid
     */
    public String getTaskProvider(){

        return this.taskProvider;
    }

    /**
     * <p>
     *     This method replaces the value of the taskProvider attribute with the parameter provided.
     * </p>
     * @param taskProvider1 string that represents the username of the task provider that has placed this bid
     */
    public void setTaskProvider(String taskProvider1) {

        this.taskProvider = taskProvider1;
    }

    /**
     * <p>
     *     This method returns the string object that represents the unique id of this bid.
     * </p>
     * @return string object that represents the unique id of this bid
     */
    public String getId(){
        
        return id;
    }

    /**
     * <p>
     *     This method replaces the value of the id attribute with the parameter provided.
     * </p>
     * @param id string object that represents the unique id of this bid
     */
    public void setId(String id) {
        
        this.id = id;
    }

    /**
     * <p>
     *     This method returns the Float object that represents the price of the bid placed on the task.
     * </p>
     * @return Float object that represents the price of the bid placed on a task
     */
    public Float getBidPrice(){

        return this.bidPrice;
    }

    /**
     * <p>
     *     This method replaces the value of the bidPrice attribute with the parameter provided.
     * </p>
     * @param bidPrice Float object that represents the price of bid placed on a task
     */
    public void setBidPrice(Float bidPrice){

        this.bidPrice = bidPrice;
    }

    /**
     * <p>
     *     This method returns a String object that represents the task requester's username.
     * </p>
     *
     * @return string object that represents the task requester's username
     */
    public String getTaskRequester() {

        return taskRequester;
    }

    /**
     * <p>
     *     This method replaces the value of the taskRequester attribute with the parameter provided.
     * </p>
     *
     * @param taskRequester string object that represents the task requester's username
     */
    public void setTaskRequester(String taskRequester) {

        this.taskRequester = taskRequester;
    }

    /**
     * <p>
     *     This method returns a Calendar object that represents the time of the bid's creation.
     * </p>
     *
     * @return calender object that represents the bid's time of creation
     */
    public Calendar getTimeStamp() {

        return timeStamp;
    }
}

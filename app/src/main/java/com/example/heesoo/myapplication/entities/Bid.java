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
     * of a newly created bid's status is "Placed".
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
     *     This method returns a string that corresponds to the name of the task that this bid is placed on.
     * </p>
     * @return string that represents the name of this task
     */
    public String getTaskName(){

        return taskName;
    }

    /**
     * <p>
     *     This method replaces the local value of this variable with the value passed to the method.
     * </p>
     * @param taskName string that represents the name of the task that this bid is placed on.
     */
    public void setTaskName(String taskName){

        this.taskName = taskName;
    }

    /**
     * <p>
     *     This method returns a string that represents the description of the task this bid is
     *     placed on.
     * </p>
     * @return string that represents the description of the task this bid is placed on
     */
    public String getTaskDescription(){

        return description;
    }

    /**
     * <p>
     *     This method replaces the local value of this variable with the value passed to the method.
     * </p>
     * @param description string that represents the description of the task that this bid is placed on
     */
    public void setTaskDescription(String description){

        this.description = description;
    }

    /**
     * <p>
     *     This method returns a string that represents the status of this bid.
     * </p>
     * @return string that represents the status of this bid
     */
    public String getStatus(){

        return status;
    }

    /**
     * <p>
     *     This method replaces the local value of this variable with the value passed to the method.
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

        return taskProvider;
    }

    /**
     * <p>
     *     This method replaces the local value of this variable with the value passed to the method.
     * </p>
     * @param taskProvider string that represents the username of the task provider that has placed this bid
     */
    public void setTaskProvider(String taskProvider) {

        this.taskProvider = taskProvider;
    }

    /**
     * <p>
     *     This method returns the string that represents the unique id of this bid.
     * </p>
     * @return string that represents the unique id of this bid
     */
    public String getId(){
        
        return id;
    }

    /**
     * <p>
     *     This method replaces the local value of this variable with the value passed to the method.
     * </p>
     * @param id string that represents the unique id of this bid
     */
    public void setId(String id) {
        
        this.id = id;
    }

    /**
     * <p>
     *     This method returns the Float that represents the value of bid placed on this task
     * </p>
     * @return Float that represents the price of the bid placed on a task
     */
    public Float getBidPrice(){

        return this.bidPrice;
    }

    /**
     * <p>
     *     This method replaces the local value of this variable with the value passed to the method.
     * </p>
     * @param bidPrice Float that represents the price of bid placed on a task
     */
    public void setBidPrice(Float bidPrice){

        this.bidPrice = bidPrice;
    }

    public String getTaskRequester() {

        return taskRequester;
    }

    public void setTaskRequester(String taskRequester) {

        this.taskRequester = taskRequester;
    }

    public Calendar getTimeStamp() {

        return timeStamp;
    }

    public void setTimeStamp(Calendar timeStamp) {

        this.timeStamp = timeStamp;
    }
}
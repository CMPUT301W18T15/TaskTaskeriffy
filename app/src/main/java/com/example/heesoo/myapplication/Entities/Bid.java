package com.example.heesoo.myapplication.Entities;

import java.io.Serializable;

import io.searchbox.annotations.JestId;


/**
 * Created by chengze on 2018/2/26.
 */

public class Bid implements Serializable{
    private String taskName;
    private String description;
    private String status;
    private String taskProvider;
    private Float bidPrice;

    @JestId
    private String id;

    /**
     * <p>
     * A constructor that creates a new bid  given the following four parameters. The default value
     * for a newly created bid's status is "Placed".
     *</p>
     * <pre>
     * @parm taskProvider     a string that represents the unique username of the user than has bid on this task
     * @parm taskName         a string that represents the task name
     * @parm taskDescription  a string that represents the task description
     * @parm bidPrice         a float that represents the bid the taskProvider has placed on this task
     * </pre>
     * @author      Manuela Key Marichales
     * @since       1.0
     */
    public Bid(String TaskName, String Description, Float BidPrice, String TaskProvider) {
        this.taskName = TaskName;
        this.taskProvider = TaskProvider;
        this.description = Description;
        this.status = "Placed";
        this.bidPrice = BidPrice;
    }

    /**
     * <p>
     *     This method returns the string that corresponds to the task that this bid is placed on
     * </p>
     * @return string that represents the taskName that corresponds to this task
     */
    public String getTaskName(){

        return taskName;
    }

    /**
     * <p>
     *     This method sets the variable "taskName" to the string passed to this method through
     *     the parameters.
     * </p>
     * @param taskname string that represents the name of the task that this bid is placed on
     */
    public void setTaskName(String taskname){

        this.taskName = taskname;
    }

    /**
     * <p>
     *     This method returns the string that represents the description of the task this bid is
     *     placed on
     * </p>
     * @return string that represents the description of the task this bid is placed on
     */
    public String getTaskDescription(){

        return description;
    }

    /**
     * <p>
     *     This method sets the "description" variable to the string passed to this method through the
     *     parameter.
     * </p>
     * @param description string that represents the description of the task that this bid is placed on
     */
    public void setTaskDescription(String description){

        this.description = description;
    }

    /**
     * <p>
     *     This method returns a string that represents the status of this bid
     * </p>
     * @return string that represents the status of this bid
     */
    public String getStatus(){

        return status;
    }

    /**
     * <p>
     *     This method sets the variable "status" to the string that is passed to the method through
     *     the parameter.
     * </p>
     * @param status string that repreents the status of this bid
     */
    public void setStatus(String status) {

        this.status = status;
    }

    /**
     * <p>
     *     This method returns the string that corresponds to the username of task provider that has
     *     placed the bid.
     * </p>
     * @return string that represents the username of task provider that has placed this bid
     */
    public String getTaskProvider(){

        return taskProvider;
    }

    /**
     * <p>
     *     This method sets the variable "taskProvider" to the string that is passed to the method
     *     through the parameters.
     * </p>
     * @param taskProvider string that represents the username of the task provider that has placed this bid
     */
    public void setTaskProvider(String taskProvider) {

        this.taskProvider = taskProvider;
    }

    /**
     * <p>
     *     This method returns the string that corresponds to the id of this bid
     * </p>
     * @return string that represents the unique id of this bid
     */
    public String getId(){
        
        return id;
    }

    /**
     * <p>
     *     This method sets the variable "id" to the string passed to the method through the parameter
     * </p>
     * @param id string that represents the unique id of this bid
     */
    public void setId(String id) {
        
        this.id = id;
    }

    /**
     * <p>
     *     This method returns the Float value that represents the value of bid placed on this task
     * </p>
     * @return Float that represents the price of the bid placed on a task
     */
    public Float getBidPrice(){

        return this.bidPrice;
    }

    /**
     * <p>
     *     This method sets the variable "bidPrice" to the Float passed to the method through the
     *     parameters
     * </p>
     * @param BidPrice Float that represents the price of bid placed on a task
     */
    public void setBidPrice(Float BidPrice){

        this.bidPrice = BidPrice;
    }
}

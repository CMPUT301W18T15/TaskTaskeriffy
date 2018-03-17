package com.example.heesoo.myapplication.Entities;

import java.io.Serializable;
import java.util.ArrayList;

import android.media.Image;

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchBidController;
import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchTaskController;

import io.searchbox.annotations.JestId;


/**
 * Created by chengze on 2018/2/26.
 */

public class Task implements Serializable{
    private String taskRequester; // Changing to just attach the username for making MainActivity getCurrentUser work in the constructor for task in ElasticSearchTaskController.
    private String taskName;
    private String taskDescription;
    private String assignedTaskProvider;
    private String status;
    @JestId
    private String id;
    private Image picture;
    private ArrayList<Bid> bids;
    private ArrayList<User> taskBidders;

    public Task(String taskRequester, String taskName, String taskDescription, String status) {
        this.taskRequester = taskRequester;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
        this.bids = new ArrayList<Bid>();
        this.taskBidders = new ArrayList<User>();
        this.assignedTaskProvider = "";
    }


    public String getUserName(){
        return taskRequester;
    }

    public String getTaskProvider(){
        return assignedTaskProvider;
    }

    public void setTaskProvider(String taskProvider){
        this.assignedTaskProvider = taskProvider;
    }


    public String getTaskName(){
        return taskName;
    }

    public void setTaskName(String taskName){
        this.taskName = taskName;
    }

    public String getTaskDescription(){
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription){
        this.taskDescription = taskDescription;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public void addBid(Bid bid){
        bids.add(bid);
        this.status = "Bidded";
        ElasticSearchTaskController.EditTask editTask = new ElasticSearchTaskController.EditTask();
        editTask.execute(this);

    }

    public void deleteBid(Bid bid){
        bids.remove(bid);
        if (bids.isEmpty()){
            this.status = "Req  uested";
            ElasticSearchTaskController.EditTask editTask = new ElasticSearchTaskController.EditTask();
            editTask.execute(this);
        }
    }

    public ArrayList<Bid> getBids(){
        return bids;
    }

    public void acceptBid(String taskProvider) {
        assignedTaskProvider = taskProvider;
        this.setStatus("Assigned");
        ElasticSearchTaskController.EditTask editTask = new ElasticSearchTaskController.EditTask();
        editTask.execute(this);
    }

    public String toString() {
        return  "Name: " + taskName +" \n Status: " + status;
    }

    public String getLowestBid() {
        if (bids.isEmpty()){
            return "Null";
        }
        else {
            Float maxValue = bids.get(0).getBidPrice();
            for (Bid bid : bids) {
                if (bid.getBidPrice() < maxValue) {
                    maxValue = bid.getBidPrice();
                }
            }
            return maxValue.toString();
        }

    }
}

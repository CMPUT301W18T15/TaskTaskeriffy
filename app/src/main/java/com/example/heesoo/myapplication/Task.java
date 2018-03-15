package com.example.heesoo.myapplication;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import android.media.Image;

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

    public ArrayList<Bid> getBid(){
        return bids;
    }

    public void addBid(Bid bid, User taskProvider){
        bids.add(bid);
        taskBidders.add(taskProvider);
    }

    public void deleteBid(Bid bid, User taskProvider){
        bids.remove(bid);
        taskBidders.remove(taskProvider);
    }

    public ArrayList<Bid> getBids(){
        return bids;
    }

    public void acceptBid(String taskProvider) {
        assignedTaskProvider = taskProvider;
    }

    public String toString() {
        return  "Name: " + taskName +" \n Status: " + status;
    }

    public Bid getLowestBid() {
        Bid minBid = bids.get(0);
        for (int i = 1; i < bids.size(); i++) {
            if (bids.get(i).getBidPrice() < minBid.getBidPrice() ) {
                minBid = bids.get(i);
            }

        }
        return minBid;
    }
}

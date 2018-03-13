package com.example.heesoo.myapplication;

import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Collection;
import android.media.Image;
import io.searchbox.annotations.JestId;



/**
 * Created by chengze on 2018/2/26.
 */

public class Task {
    private TaskRequester taskRequester;
    private String taskName;
    private String taskDescription;
    private TaskProvider assignedTaskProvider;
    private String status;
    @JestId
    private String Id;
    private Image picture;
    private ArrayList<Bid> bids;
    private ArrayList<User> taskBidders;

    public Task(TaskRequester taskRequester, String taskName, String taskDescription, String status) {
        this.taskRequester = taskRequester;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
        this.bids = new ArrayList<Bid>();
        this.taskBidders = new ArrayList<User>();
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

    public void acceptBid(TaskProvider taskProvider) {

        assignedTaskProvider = taskProvider;
    }

    public String getStatus(){

        return status;
    }
    public String getId(){
        
        return id;
    }
    
    public void setId(String id) {
        
        this.id = id;
    }
}

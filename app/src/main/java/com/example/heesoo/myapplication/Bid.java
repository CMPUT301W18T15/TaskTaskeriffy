package com.example.heesoo.myapplication;

import android.app.ActivityManager;

/**
 * Created by chengze on 2018/2/26.
 */

public class Bid {
    private String TaskName;
    private String TaskDetail;
    private String Description;
    private String Status;
    private Float BidPrice;

    public Bid(String TaskName, String TaskDetail, String Description, String Status, Float BidPrice){
        this.TaskName = TaskName;
        this.TaskDetail = TaskDetail;
        this.Description = Description;
        this.Status = Status;
        this.BidPrice = BidPrice;
    }
    // should I use different constructor to create Bids without task description?

    public String getTaskName(){
        return TaskName;
    }

    public void setTaskName(String taskname){
        this.TaskName = taskname;
    }

    public String getTaskDescription(){
        return Description;
    }

    public void setTaskDescription(String description){
        this.Description = description;
    }
    public String getStatus(){
        return Status;
    }
    public void setStatus(String status){
        this.Status = status;
    }

    // do we need methods to get/set TaskDetails and BidPrice?
    public String getTaskDetail(){
        return TaskDetail;
    }

    public void setTaskDetail(String TaskDetail){
        this.TaskDetail = TaskDetail;
    }

    public Float getBidPrice(){
        return this.BidPrice;
    }

    public void setBidPrice(Float BidPrice){
        this.BidPrice = BidPrice;
    }
}

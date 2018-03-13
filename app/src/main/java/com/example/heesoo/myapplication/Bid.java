package com.example.heesoo.myapplication;

import android.app.ActivityManager;
import io.searchbox.annotations.JestId;


/**
 * Created by chengze on 2018/2/26.
 */

public class Bid {
    private String taskName;
    private String taskDetails;
    private String description;
    private String status;
    private Float bidPrice;
    @JestId
    private String id;

    public Bid(String TaskName, String TaskDetail, String Description, String Status, Float BidPrice) {
        this.taskName = TaskName;
        this.taskDetails = TaskDetail;
        this.description = Description;
        this.status = Status;
        this.bidPrice = BidPrice;
    }

    public String getTaskName(){

        return taskName;
    }

    public void setTaskName(String taskname){

        this.taskName = taskname;
    }

    public String getTaskDescription(){

        return description;
    }

    public void setTaskDescription(String description){

        this.description = description;
    }

    public String getStatus(){

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }
    public String getId(){
        
        return id;
    }
    
    public void setId(String id) {
        
        this.id = id;
    }

    public String getTaskDetail(){

        return taskDetails;
    }

    public void setTaskDetail(String TaskDetail){

        this.taskDetails = TaskDetail;
    }

    public Float getBidPrice(){

        return this.bidPrice;
    }

    public void setBidPrice(Float BidPrice){

        this.bidPrice = BidPrice;
    }
}

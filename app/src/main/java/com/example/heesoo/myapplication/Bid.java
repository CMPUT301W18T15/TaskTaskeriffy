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
    private Float LowestBid;
    // should be self bid?

    public Bid(String TaskName, String TaskDetail, String Description, String Status, Float LowestBid){
        this.TaskName = TaskName;
        this.TaskDetail = TaskDetail;
        this.Description = Description;
    }

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
        this.Status =status;
    }
}

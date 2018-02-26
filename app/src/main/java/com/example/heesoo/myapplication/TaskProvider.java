package com.example.heesoo.myapplication;

import android.media.Image;

import java.util.ArrayList;

/**
 * Created by chengze on 2018/2/26.
 */

public class TaskProvider extends User {
    public ArrayList<Task> MyTasks;

    public TaskProvider(String username, String password, String emailAddress, String address, Image profilePicture){
        super(username, password, emailAddress, address, profilePicture);
    }

    public ArrayList<Task> getTasks(){
        return MyTasks;
    }

    public ArrayList<Task> getAssignedTasks(){
        ArrayList<Task> assignedList = new ArrayList<Task>();
        for(Task tasks : MyTasks){
            if (tasks.status.equals("Assigned")){
                assignedList.add(tasks);
            }
        }
        return assignedList;
    }

    public ArrayList<Task> getBiddedTasks(){
        ArrayList<Task> biddedList = new ArrayList<Task>();
        for(Task tasks : MyTasks) {
            if (tasks.status.equals("Bidded")) {
                biddedList.add(tasks);
            }
        }
        return biddedList;
    }
}

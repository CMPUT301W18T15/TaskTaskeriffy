package com.example.heesoo.myapplication;

import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by chengze on 2018/2/26.
 */

public class Task {
    private String taskRequester;
    private String taskName;
    private String taskDescription;
    public String status;
    private ContactsContract.Contacts.Photo picture;
    private ArrayList<Bid> bids;
    private Collection<User> assignedProvider;

    public Task(String taskRequester, String taskName, String taskDescription, String status){
        this.taskRequester = taskRequester;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
    }

    public void addBid(Bid bid){
        bids.add(bid);
    }

    public void deleteBid(Bid bid){
        bids.remove(bid);
    }

    public Collection<Bid> getBid(){
        return bids;
    }

    public String getStatus(){
        return status;
    }
}

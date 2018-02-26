package com.example.heesoo.myapplication;

import android.app.ActivityManager;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by chengze on 2018/2/25.
 */

public class BidTest extends ActivityInstrumentationTestCase2 {
    public BidTest(){
        super(Bid.class);
    }

    private String TaskName = "task1";
    private String TaskDetail = "This is task detail";
    private String Description = "This is task description";
    private String Status = "Requested";
    private Float LowestBid = 1f;


    public void testGetTaskName(){
        Bid bid = new Bid(TaskName, TaskDetail, Description, Status, LowestBid);
        assertEquals(TaskName, bid.getTaskName());
    }

    public void testSetTaskName(){
        Bid bid = new Bid(TaskName, TaskDetail, Description, Status, LowestBid);
        bid.setTaskName("new task name");
        assertEquals("new task name", bid.getTaskName());
    }

    public void testGetTaskDescription(){
        Bid bid = new Bid(TaskName, TaskDetail, Description, Status, LowestBid);
        assertEquals(Description, bid.getTaskDescription());
    }

    public void testSetTaskDescription(){
        Bid bid = new Bid(TaskName, TaskDetail, Description, Status, LowestBid);
        bid.setTaskDescription("new task description");
        assertEquals("new task description", bid.getTaskDescription());
    }

    public void testGetStatus(){
        Bid bid = new Bid(TaskName, TaskDetail, Description, Status, LowestBid);
        assertEquals(Status, bid.getStatus());
    }

    public void testSetStatus(){
        Bid bid = new Bid(TaskName, TaskDetail, Description, Status, LowestBid);
        bid.setStatus("Bidded");
        assertEquals("Bidded", bid.getStatus());
    }
}

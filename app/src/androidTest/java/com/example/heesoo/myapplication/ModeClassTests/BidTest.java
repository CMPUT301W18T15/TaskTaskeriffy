package com.example.heesoo.myapplication.ModeClassTests;

import android.test.ActivityInstrumentationTestCase2;

import com.example.heesoo.myapplication.entities.Bid;
//import com.example.heesoo.myapplication.user_task_bid.Bid;

/**
 * Created by chengze on 2018/2/25.
 */

public class BidTest extends ActivityInstrumentationTestCase2 {

    public BidTest(){
        super(Bid.class);
    }

    private String TaskName = "Task1";
    private String Description = "TaskDescription";
    private Float BidPrice = 1f;
    private String TaskProvider = "TaskProvider";
    private String TaskRequester = "TaskRequester";


    public void testGetTaskName(){
        Bid bid = new Bid(TaskName, Description, BidPrice, TaskProvider, TaskRequester);
        assertEquals(TaskName, bid.getTaskName());
    }

    public void testSetTaskName(){
        Bid bid = new Bid(TaskName, Description, BidPrice, TaskProvider, TaskRequester);
        bid.setTaskName("NewTaskName");
        assertEquals("NewTaskName", bid.getTaskName());
    }

    public void testGetTaskDescription(){
        Bid bid = new Bid(TaskName, Description, BidPrice, TaskProvider, TaskRequester);
        assertEquals(Description, bid.getTaskDescription());
    }

    public void testSetTaskDescription(){
        Bid bid = new Bid(TaskName, Description, BidPrice, TaskProvider, TaskRequester);
        bid.setTaskDescription("NewTaskDescription");
        assertEquals("NewTaskDescription", bid.getTaskDescription());
    }

    public void testGetStatus(){
        Bid bid = new Bid(TaskName, Description, BidPrice, TaskProvider, TaskRequester);
        assertEquals("Placed", bid.getStatus());
    }

    public void testSetStatus(){
        Bid bid = new Bid(TaskName, Description, BidPrice, TaskProvider, TaskRequester);
        bid.setStatus("Declined");
        assertEquals("Declined", bid.getStatus());
    }

    public void testGetBidPrice(){
        Bid bid = new Bid(TaskName, Description, BidPrice, TaskProvider, TaskRequester);
        assertEquals(BidPrice, bid.getBidPrice());
    }

    public void testSetBidPrice(){
        Bid bid = new Bid(TaskName, Description, BidPrice, TaskProvider, TaskRequester);
        bid.setBidPrice(2f);
        assertEquals(2f, bid.getBidPrice());
    }

    public void testSetTaskProvider() {
        Bid bid = new Bid(TaskName, Description, BidPrice, TaskProvider, TaskRequester);
        bid.setTaskProvider("TaskProvider1");
        assertEquals("TaskProvider1", bid.getTaskProvider());
    }

    public void testGetTaskProvider() {
        Bid bid = new Bid(TaskName, Description, BidPrice, TaskProvider, TaskRequester);
        assertEquals(TaskProvider, bid.getTaskProvider());
    }
}

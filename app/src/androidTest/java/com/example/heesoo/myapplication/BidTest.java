package com.example.heesoo.myapplication;

import android.test.ActivityInstrumentationTestCase2;

<<<<<<< HEAD
import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.user_task_bid.Bid;
=======
import com.example.heesoo.modelclasses.Bid;
>>>>>>> 44a22492f696b3ec835da725f9daf182c819c159

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
    private Float BidPrice = 1f;


    public void testGetTaskName(){
        Bid bid = new Bid(TaskName, TaskDetail, Status, BidPrice);
        assertEquals(TaskName, bid.getTaskName());
    }

    public void testSetTaskName(){
        Bid bid = new Bid(TaskName, TaskDetail, Status, BidPrice);
        bid.setTaskName("new task name");
        assertEquals("new task name", bid.getTaskName());
    }

    public void testGetTaskDescription(){
        Bid bid = new Bid(TaskName, TaskDetail, Status, BidPrice);
        assertEquals(Description, bid.getTaskDescription());
    }

    public void testSetTaskDescription(){
        Bid bid = new Bid(TaskName, TaskDetail, Status, BidPrice);
        bid.setTaskDescription("new task description");
        assertEquals("new task description", bid.getTaskDescription());
    }

    public void testGetStatus(){
        Bid bid = new Bid(TaskName, TaskDetail, Status, BidPrice);
        assertEquals(Status, bid.getStatus());
    }

    public void testSetStatus(){
        Bid bid = new Bid(TaskName, TaskDetail, Status, BidPrice);
        bid.setStatus("Bidded");
        assertEquals("Bidded", bid.getStatus());
    }

    public void testGetTaskDetail(){
        Bid bid = new Bid(TaskName, TaskDetail, Status, BidPrice);
        assertEquals(TaskDetail, bid.getTaskDetail());
    }

    public void testSetTaskDetail(){
        Bid bid = new Bid(TaskName, TaskDetail, Status, BidPrice);
        bid.setTaskDetail("new task detail");
        assertEquals("new task detail", bid.getTaskDetail());
    }

    public void testGetBidPrice(){
        Bid bid = new Bid(TaskName, TaskDetail, Status, BidPrice);
        assertEquals(BidPrice, bid.getBidPrice());
    }

    public void testSetBidPrice(){
        Bid bid = new Bid(TaskName, TaskDetail, Status, BidPrice);
        bid.setBidPrice(2f);
        assertEquals(2f, bid.getBidPrice());
    }
}

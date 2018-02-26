package com.example.heesoo.myapplication;

import android.provider.ContactsContract;
import android.test.ActivityInstrumentationTestCase2;

import java.util.Collection;

/**
 * Created by chengze on 2018/2/25.
 */

public class TaskTest extends ActivityInstrumentationTestCase2 {
    public TaskTest(){
        super(Task.class);
    }

    private String taskRequester = "requester1";
    private String TaskName = "task1";
    private String TaskDescription = "task description";
    public String status = "requested";

    //private String TaskName = "task1";
    private String TaskDetail = "This is task detail";
    private String Description = "This is task description";
    private String Status = "Requested";
    private Float LowestBid = 1f;

    public void testAddBid(){
        Task task = new Task(taskRequester, TaskName, TaskDescription, status);
        Bid bid = new Bid(TaskName, TaskDetail, Description, Status, LowestBid);
        task.addBid(bid);
        assertTrue(task.getBid().contains(bid));
    }

    public void testDeleteBid(){
        Task task = new Task(taskRequester, TaskName, TaskDescription, status);
        Bid bid = new Bid(TaskName, TaskDetail, Description, Status, LowestBid);
        task.addBid(bid);
        assertTrue(task.getBid().contains(bid));
        task.deleteBid(bid);
        assertFalse(task.getBid().contains(bid));
    }

    public void testGetBid(){
        Task task = new Task(taskRequester, TaskName, TaskDescription, status);
        Bid bid = new Bid(TaskName, TaskDetail, Description, Status, LowestBid);
        task.addBid(bid);
        assertEquals(bid, task.getBid());
    }

}

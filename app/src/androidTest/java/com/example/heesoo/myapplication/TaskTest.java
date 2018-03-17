package com.example.heesoo.myapplication;

import android.test.ActivityInstrumentationTestCase2;

import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.Entities.TaskProvider;
import com.example.heesoo.myapplication.Entities.TaskRequester;
//import com.example.heesoo.myapplication.user_task_bid.Bid;
//import com.example.heesoo.myapplication.user_task_bid.Task;

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
        TaskRequester taskRequester = new TaskRequester("username1", "password", "emailaddress@email.com", "Home Address");
        TaskProvider tr = new TaskProvider("username1", "password", "emailaddress@email.com", "Home Address");
        Task task = new Task(taskRequester, TaskName, TaskDescription, status);
        Bid bid = new Bid(TaskName, TaskDetail, Status, LowestBid);
        task.addBid(bid, tr);
        assertTrue(task.getBids().contains(bid));
    }

    public void testDeleteBid(){
        TaskRequester taskRequester = new TaskRequester("username1", "password", "emailaddress@email.com", "Home Address");
        TaskProvider tr = new TaskProvider("username1", "password", "emailaddress@email.com", "Home Address");
        Task task = new Task(taskRequester, TaskName, TaskDescription, status);
        Bid bid = new Bid(TaskName, TaskDetail, Status, LowestBid);
        task.addBid(bid, tr);
        assertTrue(task.getBids().contains(bid));
        task.deleteBid(bid, tr);
        assertFalse(task.getBids().contains(bid));
    }

    public void testGetBid(){
        TaskRequester taskRequester = new TaskRequester("username1", "password", "emailaddress@email.com", "Home Address");
        TaskProvider tr = new TaskProvider("username1", "password", "emailaddress@email.com", "Home Address");
        Task task = new Task(taskRequester, TaskName, TaskDescription, status);
        Bid bid = new Bid(TaskName, TaskDetail, Status, LowestBid);
        task.addBid(bid, tr);
        assertEquals(bid, task.getBids().get(0));
    }

}

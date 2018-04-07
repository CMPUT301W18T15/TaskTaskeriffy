package com.example.heesoo.myapplication.ModeClassTests;

import android.test.ActivityInstrumentationTestCase2;

import com.example.heesoo.myapplication.entities.Bid;
import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.entities.User;
//import com.example.heesoo.myapplication.user_task_bid.Bid;
//import com.example.heesoo.myapplication.user_task_bid.Task;

/**
 * Created by chengze on 2018/2/25.
 */

public class TaskTest extends ActivityInstrumentationTestCase2 {
    public TaskTest(){
        super(Task.class);
    }

    private String username = "Username1";
    private String password = "Password";
    private String email = "email@email.com";
    private String phoneNumber = "0000000000";

    private String taskRequester = "Requester1";
    private String TaskName = "Task1";
    private String TaskDescription = "TaskDescription";
    private Float HighestBid = 100f;
    private Float LowestBid = 1f;

    public void testSetTaskProvider() {
        User taskRequester = new User(username, password, email, phoneNumber);
        User taskProvider = new User(username, password, email, phoneNumber);
        Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
        task.setTaskProvider(taskRequester.getUsername());
        assertEquals(task.getTaskProvider(), taskProvider.getUsername());

    }

    public void testSetTaskName() {
        User taskRequester = new User(username, password, email, phoneNumber);
        User taskProvider = new User(username, password, email, phoneNumber);
        Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
        task.setTaskName("SampleTaskName");
        assertEquals(task.getTaskName(), "SampleTaskName");

    }

    public void testSetTaskDescription() {
        User taskRequester = new User(username, password, email, phoneNumber);
        User taskProvider = new User(username, password, email, phoneNumber);
        Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
        task.setTaskDescription("SampleTaskDescription");
        assertEquals(task.getTaskDescription(), "SampleTaskDescription");

    }

    public void testSetTaskStatus() {
        User taskRequester = new User(username, password, email, phoneNumber);
        User taskProvider = new User(username, password, email, phoneNumber);
        Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
        task.setStatus("Assigned");
        assertEquals(task.getStatus(), "Assigned");

    }

    public void testSetTaskID() {
        User taskRequester = new User(username, password, email, phoneNumber);
        User taskProvider = new User(username, password, email, phoneNumber);
        Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
        task.setId("01");
        assertEquals(task.getId(), "01");

    }

    public void testAcceptBid() {

        User taskRequester = new User(username, password, email, phoneNumber);
        User taskProvider = new User(username, password, email, phoneNumber);
        Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
        Bid bid = new Bid(TaskName, TaskDescription, LowestBid, taskProvider.getUsername(), taskRequester.getUsername());
        task.addBid(bid);
        task.acceptBid(taskProvider.getUsername());
        assertEquals(task.getStatus(), "Assigned");
    }

    public void testAddBid(){
        User taskRequester = new User(username, password, email, phoneNumber);
        User taskProvider = new User(username, password, email, phoneNumber);
        Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
        Bid bid = new Bid(TaskName, TaskDescription, LowestBid, taskProvider.getUsername(), taskRequester.getUsername());
        task.addBid(bid);

        assertTrue(task.getBids().contains(bid));
    }

    public void testDeleteBid(){
        User taskRequester = new User(username, password, email, phoneNumber);
        User taskProvider = new User(username, password, email, phoneNumber);
        Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
        Bid bid = new Bid(TaskName, TaskDescription, LowestBid, taskProvider.getUsername(), taskRequester.getUsername());
        bid.setId("01");
        task.addBid(bid);
        assertTrue(task.getBids().contains(bid));
        task.deleteBid(bid);
        assertFalse(task.getBids().contains(bid));
    }

    public void testGetBid(){
        User taskRequester = new User(username, password, email, phoneNumber);
        User taskProvider = new User(username, password, email, phoneNumber);
        Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
        Bid bid = new Bid(TaskName, TaskDescription, LowestBid, taskProvider.getUsername(), taskRequester.getUsername());
        task.addBid(bid);
        assertEquals(bid, task.getBids().get(0));
    }

    public void testGetLowestBid(){
        User taskRequester = new User(username, password, email, phoneNumber);
        User taskProvider = new User(username, password, email, phoneNumber);
        Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
        Bid bid = new Bid(TaskName, TaskDescription, LowestBid, taskProvider.getUsername(), taskRequester.getUsername());
        Bid bid1 = new Bid(TaskName, TaskDescription, HighestBid, taskProvider.getUsername(), taskRequester.getUsername());
        task.addBid(bid);
        task.addBid(bid1);
        assertEquals(bid.getBidPrice().toString(), task.getLowestBid());
    }

}

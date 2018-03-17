package com.example.heesoo.myapplication;

import android.test.ActivityInstrumentationTestCase2;

import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.Entities.TaskRequester;

/**
 * Created by chengze on 2018/2/25.
 */

public class TaskRequesterTest extends ActivityInstrumentationTestCase2 {
    public TaskRequesterTest(){

        super(TaskRequester.class);
    }

    private String username = "Username1";
    private String password = "Password";
    private String email = "email@email.com";
    private String phoneNumber = "0000000000";

    private String TaskName = "Task1";
    private String TaskDescription = "TaskDescription";

    public void testAddTask(){
        TaskRequester taskRequester = new TaskRequester(username, password, email, phoneNumber);
        Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
        taskRequester.addTask(task);
        assertEquals(taskRequester.getTask(task), task);

    }

    public void testRemoveTask() {
        TaskRequester taskRequester = new TaskRequester(username, password, email, phoneNumber);
        Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
        taskRequester.addTask(task);
        assertEquals(taskRequester.getTask(task), task);
        taskRequester.removeTask(task);
        assertNull(taskRequester.getTask(task));
    }
}

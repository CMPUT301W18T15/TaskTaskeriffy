package com.example.heesoo.myapplication;

import android.media.Image;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by chengze on 2018/2/25.
 */

public class TaskRequesterTest extends ActivityInstrumentationTestCase2 {
    public TaskRequesterTest(){
        super(TaskRequester.class);
    }

    TaskRequester tp = new TaskRequester("username1", "password", "emailaddress@email.com", "Home Address");
    private String TaskName = "task1";
    private String Description = "This is task description";
    private String Status = "Requested";
    Task t = new Task(tp, TaskName, Description, Status);

    TaskRequester tp2 = new TaskRequester("username2", "password2", "emailaddress@email.com2", "Home Address2");
    private String TaskName2 = "task2";
    private String Description2 = "This is task description2";
    private String Status2 = "Requested2";
    Task t2 = new Task(tp2, TaskName2, Description2, Status2);

    public void testAddTask(){

        tp.addTask(t);
        assertEquals(tp.getTask(t), t);

    }

    public void testRemoveTask() {

        tp.addTask(t);
        tp.removeTask(t);
        assertNull(tp.getTask(t));
    }
}

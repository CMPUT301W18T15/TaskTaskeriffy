package com.example.heesoo.myapplication;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by chengze on 2018/2/25.
 */

public class TaskProviderTest extends ActivityInstrumentationTestCase2 {
    public TaskProviderTest(){
        super(TaskProvider.class);
    }

    private String taskRequester = "requester1";
    private String TaskName = "task1";
    private String TaskDescription = "task description";

    public void testGetTasks(){
        TaskProvider taskprovider = new TaskProvider();

        Task task = new Task(taskRequester, TaskName, TaskDescription, "Requested");
        taskprovider.MyTasks.add(task);

        assertEquals(taskprovider.MyTasks, taskprovider.getTasks());
    }

    public void testGetAssignedTasks(){
        TaskProvider taskprovider = new TaskProvider();

        Task task = new Task(taskRequester, TaskName, TaskDescription, "Assigned");
        taskprovider.MyTasks.add(task);

        ArrayList<Task> assignedList = new ArrayList<Task>;
        for(Task tasks : taskprovider.MyTasks){
            if (tasks.status = "Assigned"){
                assignedList.add(tasks);
            }
        }
        assertEquals(assignedList, taskprovider.getAssignedTasks());
    }

    public void testGetBiddedTasks(){
        TaskProvider taskprovider = new TaskProvider();

        ask task = new Task(taskRequester, TaskName, TaskDescription, "Bidded");
        taskprovider.MyTasks.add(task);

        ArrayList<Task> biddedList = new ArrayList<Task>;
        for(Task tasks : taskprovider.MyTasks) {
            if (tasks.status = "Bidded") {
                biddedList.add(tasks);
            }
        }
        assertEquals(biddedList, taskprovider.getBiddedTasks());
    }
}

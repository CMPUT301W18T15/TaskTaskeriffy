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

    public void testGetTasks(){
        TaskProvider taskprovider = new TaskProvider();
        assertEquals(taskprovider.MyTasks, taskprovider.getTasks());
    }

    public void testGetAssignedTasks(){
        TaskProvider taskprovider = new TaskProvider();
        ArrayList<Task> assignedList = new ArrayList<Task>;
        for(Task task : taskprovider.MyTasks){
            if (!task.assignedProvider.isEmpty()){
                assignedList.add(task);
            }
        }
        assertEquals(assignedList, taskprovider.getAssignedTasks());
    }

    public void testGetBiddedTasks(){
        TaskProvider taskprovider = new TaskProvider();
        ArrayList<Task> biddedList = new ArrayList<Task>;
        for(Task task : taskprovider.MyTasks) {
            if (!task.bids.isEmpty()) {
                biddedList.add(task);
            }
        }
        assertEquals(biddedList, taskprovider.getBiddedTasks());
    }
}

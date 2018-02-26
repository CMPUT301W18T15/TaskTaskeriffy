package com.example.heesoo.myapplication;

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

    public void testGetTasks(){
        TaskRequester taskrequester = new TaskRequester();
        assertEquals(taskrequester.MyTasks, taskrequester.getTasks());
    }

    public void testGetAssignedTasks(){
        TaskRequester taskrequester = new TaskRequester();
        ArrayList<Task> assignedList = new ArrayList<Task>;
        for(Task task : taskrequester.MyTasks){
            if (!task.assignedProvider.isEmpty()){
                assignedList.add(task);
            }
        }
        assertEquals(assignedList, taskrequester.getAssignedTasks());
    }

    public void testGetBiddedTasks(){
        TaskRequester taskrequester = new TaskRequester();
        ArrayList<Task> biddedList = new ArrayList<Task>;
        for(Task task : taskrequester.MyTasks) {
            if (!task.bids.isEmpty()) {
                biddedList.add(task);
            }
        }
        assertEquals(biddedList, taskrequester.getBiddedTasks());
    }
}

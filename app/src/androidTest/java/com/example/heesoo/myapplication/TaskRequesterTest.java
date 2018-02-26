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

    private String taskRequester = "requester1";
    private String TaskName = "task1";
    private String TaskDescription = "task description";

    public void testGetTasks(){
        String username = "provider1";
        String password = "providerpassword";
        String emailAddress = "provideremailAddress";
        String address = "provideraddress";
        Image profilePicture = null;
        TaskRequester taskrequester = new TaskRequester(username, password, emailAddress, address, profilePicture);

        Task task = new Task(taskRequester, TaskName, TaskDescription, "Requested");
        taskrequester.MyTasks.add(task);

        assertEquals(taskrequester.MyTasks, taskrequester.getTasks());
    }

    public void testGetAssignedTasks(){
        String username = "provider1";
        String password = "providerpassword";
        String emailAddress = "provideremailAddress";
        String address = "provideraddress";
        Image profilePicture = null;
        TaskRequester taskrequester = new TaskRequester(username, password, emailAddress, address, profilePicture);

        Task task = new Task(taskRequester, TaskName, TaskDescription, "Assigned");
        taskrequester.MyTasks.add(task);

        ArrayList<Task> assignedList = new ArrayList<Task>();
        for(Task tasks : taskrequester.MyTasks){
            if (tasks.status.equals("Assigned")){
                assignedList.add(tasks);
            }
        }
        assertEquals(assignedList, taskrequester.getAssignedTasks());
    }

    public void testGetBiddedTasks(){
        String username = "provider1";
        String password = "providerpassword";
        String emailAddress = "provideremailAddress";
        String address = "provideraddress";
        Image profilePicture = null;
        TaskRequester taskrequester = new TaskRequester(username, password, emailAddress, address, profilePicture);

        Task task = new Task(taskRequester, TaskName, TaskDescription, "Bidded");
        taskrequester.MyTasks.add(task);

        ArrayList<Task> biddedList = new ArrayList<Task>();
        for(Task tasks : taskrequester.MyTasks) {
            if (tasks.status.equals("Bidded")) {
                biddedList.add(tasks);
            }
        }
        assertEquals(biddedList, taskrequester.getBiddedTasks());
    }
}

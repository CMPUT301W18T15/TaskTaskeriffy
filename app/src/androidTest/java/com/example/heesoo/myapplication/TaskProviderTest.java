package com.example.heesoo.myapplication;

import android.media.Image;
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
        String username = "provider1";
        String password = "providerpassword";
        String emailAddress = "provideremailAddress";
        String address = "provideraddress";
        Image profilePicture = null;
        TaskProvider taskprovider = new TaskProvider(username, password, emailAddress, address, profilePicture);

        Task task = new Task(taskRequester, TaskName, TaskDescription, "Requested");
        taskprovider.MyTasks.add(task);

        assertEquals(taskprovider.MyTasks, taskprovider.getTasks());
    }

    public void testGetAssignedTasks(){
        String username = "provider1";
        String password = "providerpassword";
        String emailAddress = "provideremailAddress";
        String address = "provideraddress";
        Image profilePicture = null;
        TaskProvider taskprovider = new TaskProvider(username, password, emailAddress, address, profilePicture);

        Task task = new Task(taskRequester, TaskName, TaskDescription, "Assigned");
        taskprovider.MyTasks.add(task);

        ArrayList<Task> assignedList = new ArrayList<Task>();
        for(Task tasks : taskprovider.MyTasks){
            if (tasks.status.equals("Assigned")){
                assignedList.add(tasks);
            }
        }
        assertEquals(assignedList, taskprovider.getAssignedTasks());
    }

    public void testGetBiddedTasks(){
        String username = "provider1";
        String password = "providerpassword";
        String emailAddress = "provideremailAddress";
        String address = "provideraddress";
        Image profilePicture = null;
        TaskProvider taskprovider = new TaskProvider(username, password, emailAddress, address, profilePicture);

        Task task = new Task(taskRequester, TaskName, TaskDescription, "Bidded");
        taskprovider.MyTasks.add(task);

        ArrayList<Task> biddedList = new ArrayList<Task>();
        for(Task tasks : taskprovider.MyTasks) {
            if (tasks.status.equals("Bidded")) {
                biddedList.add(tasks);
            }
        }
        assertEquals(biddedList, taskprovider.getBiddedTasks());
    }
}

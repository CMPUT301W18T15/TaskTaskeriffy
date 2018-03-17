package com.example.heesoo.myapplication;

import android.test.ActivityInstrumentationTestCase2;

import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.Entities.TaskProvider;
import com.example.heesoo.myapplication.Entities.TaskRequester;


/**
 * Created by chengze on 2018/2/25.
 */

public class TaskProviderTest extends ActivityInstrumentationTestCase2 {

    public TaskProviderTest(){

        super(TaskProvider.class);
    }

    private String username = "Username1";
    private String password = "Password";
    private String email = "email@email.com";
    private String phoneNumber = "0000000000";

    private String TaskName = "Task1";
    private String TaskDescription = "TaskDescription";

    public void testAddTask(){

        TaskProvider taskProvider = new TaskProvider(username, password, email, phoneNumber);
        TaskRequester taskRequester = new TaskRequester(username, password, email, phoneNumber);
        Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
        taskProvider.addTask(task);
        assertEquals(taskProvider.getTask(task), task);

    }

    public void testRemoveTask() {
        TaskProvider taskProvider = new TaskProvider(username, password, email, phoneNumber);
        TaskRequester taskRequester = new TaskRequester(username, password, email, phoneNumber);
        Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
        taskProvider.addTask(task);
        assertEquals(taskProvider.getTask(task), task);
        taskProvider.removeTask(task);
        assertNull(taskProvider.getTask(task));
    }
}

package com.example.heesoo.myapplication;

import android.test.ActivityInstrumentationTestCase2;

<<<<<<< HEAD
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.user_task_bid.Task;
=======
import com.example.heesoo.modelclasses.Task;
import com.example.heesoo.modelclasses.TaskProvider;
import com.example.heesoo.modelclasses.TaskRequester;
>>>>>>> 44a22492f696b3ec835da725f9daf182c819c159

/**
 * Created by chengze on 2018/2/25.
 */

public class TaskProviderTest extends ActivityInstrumentationTestCase2 {
    public TaskProviderTest(){
        super(TaskProvider.class);
    }

    TaskProvider tr = new TaskProvider("username1", "password", "emailaddress@email.com", "Home Address");
    TaskRequester tp = new TaskRequester("username1", "password", "emailaddress@email.com", "Home Address");
    private String TaskName = "task1";
    private String Description = "This is task description";
    private String Status = "Requested";
    Task t = new Task(tp, TaskName, Description, Status);

    TaskProvider tr2 = new TaskProvider("username1", "password", "emailaddress@email.com", "Home Address");
    TaskRequester tp2 = new TaskRequester("username2", "password2", "emailaddress@email.com2", "Home Address2");
    private String TaskName2 = "task2";
    private String Description2 = "This is task description2";
    private String Status2 = "Requested2";
    Task t2 = new Task(tp2, TaskName2, Description2, Status2);

    public void testAddTask(){

        tr.addTask(t);
        assertEquals(tr.getTask(t), t);

    }

    public void testRemoveTask() {

        tr.addTask(t);
        tr.removeTask(t);
        assertNull(tr.getTask(t));
    }
}

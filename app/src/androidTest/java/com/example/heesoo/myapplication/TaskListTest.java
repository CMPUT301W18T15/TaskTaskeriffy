package com.example.heesoo.myapplication;

import android.test.ActivityInstrumentationTestCase2;

import com.example.heesoo.modelclasses.Task;
import com.example.heesoo.modelclasses.TaskList;
import com.example.heesoo.modelclasses.TaskRequester;
import com.example.heesoo.myapplication.Entities.TaskRequester;

/**
 * Created by manuelakm on 2018-03-12.
 */


public class TaskListTest extends ActivityInstrumentationTestCase2 {
        public TaskListTest() {

            super(Task.class);
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

                TaskList tl = new TaskList();
                tl.addTask(t);
                assertEquals(tl.getTask(t), t);

        }

        public void testRemoveTask() {
            TaskList tl = new TaskList();
            tl.addTask(t);
            tl.removeTask(t);
            assertNull(tl.getTask(t));
        }

}

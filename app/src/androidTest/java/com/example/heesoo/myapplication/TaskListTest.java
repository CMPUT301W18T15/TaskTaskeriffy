package com.example.heesoo.myapplication;

import android.test.ActivityInstrumentationTestCase2;

import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.Entities.TaskList;
import com.example.heesoo.myapplication.Entities.TaskRequester;

/**
 * Created by manuelakm on 2018-03-12.
 */


public class TaskListTest extends ActivityInstrumentationTestCase2 {
        public TaskListTest() {

            super(Task.class);
        }


        private String username = "Username1";
        private String password = "Password";
        private String email = "email@email.com";
        private String phoneNumber = "0000000000";

        private String TaskName = "Task1";
        private String TaskDescription = "TaskDescription";

        public void testAddTask() {
            TaskRequester taskRequester = new TaskRequester(username, password, email, phoneNumber);
            Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
            TaskList taskList = new TaskList();
            taskList.addTask(task);
            assertEquals(taskList.getTask(task), task);

        }

        public void testRemoveTask() {
            TaskRequester taskRequester = new TaskRequester(username, password, email, phoneNumber);
            Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
            TaskList taskList = new TaskList();
            taskList.addTask(task);
            assertEquals(taskList.getTask(task), task);
            taskList.removeTask(task);
            assertNull(taskList.getTask(task));
        }

}

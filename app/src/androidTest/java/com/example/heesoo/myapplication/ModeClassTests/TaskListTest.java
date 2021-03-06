package com.example.heesoo.myapplication.ModeClassTests;

import android.test.ActivityInstrumentationTestCase2;

import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.entities.TaskList;
import com.example.heesoo.myapplication.entities.User;

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
            User taskRequester = new User(username, password, email, phoneNumber);
            Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
            TaskList taskList = new TaskList();
            taskList.addTask(task);
            assertEquals(taskList.getTask(task), task);

        }

        public void testRemoveTask() {
            User taskRequester = new User(username, password, email, phoneNumber);
            Task task = new Task(taskRequester.getUsername(), TaskName, TaskDescription);
            TaskList taskList = new TaskList();
            taskList.addTask(task);
            assertEquals(taskList.getTask(task), task);
            taskList.removeTask(task);
            assertNull(taskList.getTask(task));
        }

}

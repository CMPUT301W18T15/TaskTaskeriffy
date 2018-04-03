package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.heesoo.myapplication.MainTaskActivity;
import com.example.heesoo.myapplication.R;

import com.example.heesoo.myapplication.Requester.RequesterAddTaskActivity;
import com.example.heesoo.myapplication.Requester.RequesterMainActivity;
import com.example.heesoo.myapplication.Requester.RequesterShowTaskDetailActivity;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/17.
 * User Story: 01.03.01
 */

/* IMPORTANT NOTE : Tests must be run in order as some tests depend on previous data.
Please clear the database before running the first intent test.
 */

public class BEDeleteTaskTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public BEDeleteTaskTest(){
        super(com.example.heesoo.myapplication.Main_LogIn.MainActivity.class);
        try{
            solo.clickOnButton("register");
            solo.enterText((EditText) solo.getView(R.id.enter_username), "user0000");
            solo.enterText((EditText) solo.getView(R.id.enter_password), "user0000");
            solo.enterText((EditText) solo.getView(R.id.enter_repeat_password), "user0000");
            solo.enterText((EditText) solo.getView(R.id.enter_email), "user0000@example.com");
            solo.enterText((EditText) solo.getView(R.id.enter_phone), "7800000000");
            solo.clickOnButton("Submit");
        }
        catch (Exception e){
        }

    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());

        // Login as user0000
        solo.enterText((EditText) solo.getView(R.id.login_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0000");
        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Logged In"));
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testDeleteTask(){
        // add sample task: user0000 task1
        solo.assertCurrentActivity("Wrong Activity", MainTaskActivity.class);
        solo.clickOnButton("Add Task");
        solo.assertCurrentActivity("Wrong Activity", RequesterAddTaskActivity.class);
        solo.enterText((EditText) solo.getView(R.id.taskName), "user0000 task1");
        solo.enterText((EditText) solo.getView(R.id.taskDescription), "user0000 task1 Description");
        solo.clickOnButton("Save");
        solo.assertCurrentActivity("Wrong Activity", MainTaskActivity.class);

        // click the list view in position 0
        // show the task details
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", RequesterShowTaskDetailActivity.class);
        assertTrue(solo.searchText("user0000 task1"));
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", MainTaskActivity.class);

        // delete the task
        solo.clickInList(0);
        solo.clickOnButton("Delete Task");
        solo.assertCurrentActivity("Wrong Activity", MainTaskActivity.class);
        assertFalse(solo.searchText("user0000 task1"));
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.heesoo.myapplication.R;

import com.example.heesoo.myapplication.Requester.RequesterAddTaskActivity;
import com.example.heesoo.myapplication.Requester.RequesterEditTaskActivity;
import com.example.heesoo.myapplication.Requester.RequesterMainActivity;
import com.example.heesoo.myapplication.Requester.RequesterShowTaskDetailActivity;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/17.
 * User Story: 01.04.01
 */

/* IMPORTANT NOTE : Tests must be run in order as some tests depend on previous data.
Please clear the database before running the first intent test.
 */

public class BFUpdateTaskTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public BFUpdateTaskTest(){
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
        solo.clickOnButton("Would you like a task performed for you?");
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testUpdateTask(){
        // add sample task: user0000 task1
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);
        solo.clickOnButton("Add new task");
        solo.assertCurrentActivity("Wrong Activity", RequesterAddTaskActivity.class);
        solo.enterText((EditText) solo.getView(R.id.taskName), "user0000 task1");
        solo.enterText((EditText) solo.getView(R.id.taskDescription), "user0000 task1 Description");
        solo.clickOnButton("Save");
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        // click the list view in position 0
        // show the task details
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", RequesterShowTaskDetailActivity.class);
        assertTrue(solo.searchText("user0000 task1"));

        // edit the task with empty task name
        solo.clickOnButton("Edit Task");
        solo.assertCurrentActivity("Wrong Activity", RequesterEditTaskActivity.class);
        assertTrue(solo.searchText("user0000 task1"));
        solo.clearEditText((EditText) solo.getView(R.id.taskNameEdit));
        solo.clickOnButton("Save Changes");
        assertTrue(solo.searchText("Missing Required Fields"));
        solo.enterText((EditText) solo.getView(R.id.taskNameEdit), "user0000 task1");

        // edit the task with empty task description
        solo.clearEditText((EditText) solo.getView(R.id.descriptionEdit));
        solo.clickOnButton("Save Changes");
        assertTrue(solo.searchText("Missing Required Fields"));
        solo.enterText((EditText) solo.getView(R.id.descriptionEdit), "user0000 task1 Description");


        // TODO test too long task name and description

        // edit the task name
        solo.clearEditText((EditText) solo.getView(R.id.taskNameEdit));
        solo.enterText((EditText) solo.getView(R.id.taskNameEdit), "user0000 task1_changed");
        solo.clickOnButton("Save Changes");
        assertTrue(solo.searchText("Saving Task"));
        solo.assertCurrentActivity("Wrong Activity", RequesterShowTaskDetailActivity.class);
        assertTrue(solo.searchText("user0000 task1_changed"));
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", RequesterShowTaskDetailActivity.class);

        // TODO Fix the bug: click edit --> change details --> save changes --> click edit ==> details does not change!
        // edit the description
        solo.clickOnButton("Edit Task");
        solo.assertCurrentActivity("Wrong Activity", RequesterEditTaskActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.descriptionEdit));
        solo.enterText((EditText) solo.getView(R.id.descriptionEdit), "new description");
        solo.clickOnButton("Save Changes");
        assertTrue(solo.searchText("Saving Task"));
        solo.assertCurrentActivity("Wrong Activity", RequesterShowTaskDetailActivity.class);
        assertTrue(solo.searchText("new description"));

        // clear the garbage
        solo.goBack();
        solo.clickInList(0);
        solo.clickOnButton("Delete Task");
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

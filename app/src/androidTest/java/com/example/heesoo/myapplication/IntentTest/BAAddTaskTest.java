package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity;
import com.example.heesoo.myapplication.login_activity.MainActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.task_requester_activities.AddTaskActivity;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/17.
 * User Story: 01.01.01
 *             01.01.02
 *             01.01.03
 */

/* IMPORTANT NOTE : Tests must be run in order as some tests depend on previous data.
Please clear the database before running the first intent test.
 */

public class BAAddTaskTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public BAAddTaskTest(){
        super(com.example.heesoo.myapplication.login_activity.MainActivity.class);
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
        MainActivity activity = (MainActivity)solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0000");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);
        assertTrue(solo.searchText("Logged In"));
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testAddTask(){
        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);

        // add new task page
        solo.clickOnButton("Add Task");
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);

        // did not fill task name
        solo.clickOnButton("Save");
        assertTrue(solo.searchText("Missing Required Fields"));

        // only fill the task name
        solo.enterText((EditText) solo.getView(R.id.taskName), "user0000 task1");
        solo.clickOnButton("Save");
        assertTrue(solo.searchText("Missing Required Fields"));

        // fill both task name and description
        solo.enterText((EditText) solo.getView(R.id.taskDescription), "user0000 task1 Description");
        solo.clickOnButton("Save");
        assertTrue(solo.searchText("Saving Task"));
        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);
        assertTrue(solo.searchText("user0000 task1"));

        // clear the garbage
        solo.clickInList(0);
        solo.clickOnButton("Delete Task");
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

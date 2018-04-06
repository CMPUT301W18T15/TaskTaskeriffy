package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.heesoo.myapplication.ChooseMode.ChooseModeActivity;
import com.example.heesoo.myapplication.login_activity.MainActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewAssignedTasksActivity;
import com.example.heesoo.myapplication.task_requester_activities.ViewBidDetailActivity;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewBiddedTasksActivity;
import com.example.heesoo.myapplication.task_requester_activities.ShowTaskDetailActivity;
import com.example.heesoo.myapplication.task_requester_activities.ViewBidsOnTaskActivity;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/18.
 * User Story 07.02.01
 */

/* IMPORTANT NOTE : Tests must be run in order as some tests depend on previous data.
Please clear the database before running the first intent test.
 */

public class EBRequesterSetTaskRequested extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public EBRequesterSetTaskRequested(){
        super(com.example.heesoo.myapplication.login_activity.MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testSetTaskRequested(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.enterText((EditText) solo.getView(R.id.login_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0000");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", ChooseModeActivity.class);
        assertTrue(solo.searchText("Logged In"));

        // choose mode
        solo.clickOnButton("Would you like a task performed for you?");
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        // set a bidded task to assigned
        solo.clickOnButton("show bidded");
        solo.assertCurrentActivity("Wrong Activity", TaskRequesterViewBiddedTasksActivity.class);
        // click the list view in position 0
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ShowTaskDetailActivity.class);
        solo.clickOnButton("View Bids");
        solo.assertCurrentActivity("Wrong Activity", ViewBidsOnTaskActivity.class);
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ViewBidDetailActivity.class);
        solo.clickOnButton("acceptBid");
        solo.assertCurrentActivity("Wrong Activity", ViewBidsOnTaskActivity.class);

        // back to requester main activity
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ShowTaskDetailActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", TaskRequesterViewBiddedTasksActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        // see assigned task
        solo.clickOnButton("show assigned task");
        solo.assertCurrentActivity("Wrong Activity", TaskRequesterViewAssignedTasksActivity.class);
        assertTrue(solo.searchText("Assigned"));

        // click the list view in position 0
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ShowTaskDetailActivity.class);
        assertTrue(solo.searchText("Task Provider"));
        assertTrue(solo.searchText("Task Name"));
        assertTrue(solo.searchText("Accepted Bid"));
        assertTrue(solo.searchText("Task Status"));
        assertTrue(solo.searchText("Assigned"));

        // set the task requested
        solo.clickOnButton("Mark Requested");
        assertTrue(solo.searchText("Task Marked as Requested"));
        solo.assertCurrentActivity("Wrong Activity", TaskRequesterViewAssignedTasksActivity.class);

        // back to Requester Main Activity
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);
        assertTrue(solo.searchText("Requested"));
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

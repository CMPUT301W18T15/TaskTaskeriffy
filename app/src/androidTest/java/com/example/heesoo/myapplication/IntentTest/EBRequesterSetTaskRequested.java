package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.heesoo.myapplication.ChooseMode.ChooseModeActivity;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.Requester.RequesterAssignedTaskListActivity;
import com.example.heesoo.myapplication.Requester.RequesterBidDetailActivity;
import com.example.heesoo.myapplication.Requester.RequesterBiddedTasksListActivity;
import com.example.heesoo.myapplication.Requester.RequesterMainActivity;
import com.example.heesoo.myapplication.Requester.RequesterShowTaskDetailActivity;
import com.example.heesoo.myapplication.Requester.RequesterViewBidsOnTaskActivity;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/18.
 * User Story 07.02.01
 */

public class EBRequesterSetTaskRequested extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public EBRequesterSetTaskRequested(){
        super(com.example.heesoo.myapplication.Main_LogIn.MainActivity.class);
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
        solo.assertCurrentActivity("Wrong Activity", RequesterBiddedTasksListActivity.class);
        // click the list view in position 0
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", RequesterShowTaskDetailActivity.class);
        solo.clickOnButton("View Bids");
        solo.assertCurrentActivity("Wrong Activity", RequesterViewBidsOnTaskActivity.class);
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", RequesterBidDetailActivity.class);
        solo.clickOnButton("acceptBid");
        solo.assertCurrentActivity("Wrong Activity", RequesterViewBidsOnTaskActivity.class);

        // back to requester main activity
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", RequesterShowTaskDetailActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", RequesterBiddedTasksListActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        // see assigned task
        solo.clickOnButton("show assigned task");
        solo.assertCurrentActivity("Wrong Activity", RequesterAssignedTaskListActivity.class);
        assertTrue(solo.searchText("Assigned"));

        // click the list view in position 0
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", RequesterShowTaskDetailActivity.class);
        assertTrue(solo.searchText("Task Provider"));
        assertTrue(solo.searchText("Task Name"));
        assertTrue(solo.searchText("Accepted Bid"));
        assertTrue(solo.searchText("Task Status"));
        assertTrue(solo.searchText("Assigned"));

        // set the task requested
        solo.clickOnButton("Mark Requested");
        assertTrue(solo.searchText("Task Marked as Requested"));
        solo.assertCurrentActivity("Wrong Activity", RequesterAssignedTaskListActivity.class);

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

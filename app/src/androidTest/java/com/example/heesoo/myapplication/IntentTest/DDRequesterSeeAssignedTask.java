package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.heesoo.myapplication.ChooseMode.ChooseModeActivity;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.Requester.RequesterAssignedTaskListActivity;
import com.example.heesoo.myapplication.Requester.RequesterBiddedTasksListActivity;
import com.example.heesoo.myapplication.Requester.RequesterMainActivity;
import com.example.heesoo.myapplication.Requester.RequesterShowTaskDetailActivity;
import com.example.heesoo.myapplication.Requester.RequesterViewBidsOnTaskActivity;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/18.
 */

public class DDRequesterSeeAssignedTask extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public DDRequesterSeeAssignedTask(){
        super(com.example.heesoo.myapplication.Main_LogIn.MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testSeeAssignedTask(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.enterText((EditText) solo.getView(R.id.login_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0000");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", ChooseModeActivity.class);
        assertTrue(solo.searchText("Logged In"));

        // choose mode
        solo.clickOnButton("Would you like a task performed for you?");
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
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

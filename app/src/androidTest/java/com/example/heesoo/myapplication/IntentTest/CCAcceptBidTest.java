package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import com.example.heesoo.myapplication.ChooseMode.ChooseModeActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewAssignedTasksActivity;
import com.example.heesoo.myapplication.task_requester_activities.ViewBidDetailActivity;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewBiddedTasksActivity;
import com.example.heesoo.myapplication.task_requester_activities.ShowTaskDetailActivity;
import com.example.heesoo.myapplication.task_requester_activities.ViewBidsOnTaskActivity;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/18.
 * User Story: 05.06.01
 */

/* IMPORTANT NOTE : Tests must be run in order as some tests depend on previous data.
Please clear the database before running the first intent test.
 */

public class CCAcceptBidTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public CCAcceptBidTest(){
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
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testAcceptBid(){
        // Login as user0000 Requester
        solo.enterText((EditText) solo.getView(R.id.login_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0000");
        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Logged In"));
        solo.clickOnButton("Would you like a task performed for you?");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        // show requester bidded task list
        solo.clickOnButton("show bidded");
        solo.assertCurrentActivity("Wrong Activity", TaskRequesterViewBiddedTasksActivity.class);

        // click the list view in position 0
        // see detail of a task
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ShowTaskDetailActivity.class);

        // see the bid list of a task
        solo.clickOnButton("View Bids");
        solo.assertCurrentActivity("Wrong Activity", ViewBidsOnTaskActivity.class);

        // choose the first bid
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ViewBidDetailActivity.class);

        // get current bid information for later check
        TextView view1 =  (TextView) solo.getView(R.id.bidderName);
        TextView view2 =  (TextView) solo.getView(R.id.taskName);
        TextView view3 =  (TextView) solo.getView(R.id.bidAmount);
        String biderName = view1.getText().toString();
        String taskName = view2.getText().toString();
        String bidAmount = view3.getText().toString();

        // accept current bid
        solo.clickOnButton("acceptBid");
        assertTrue(solo.searchText("Task Accepted"));

        // back to requester main activity
        solo.assertCurrentActivity("Wrong Activity", ViewBidsOnTaskActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ShowTaskDetailActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", TaskRequesterViewBiddedTasksActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        // see assigned task list
        solo.clickOnButton("show assigned task");
        solo.assertCurrentActivity("Wrong Activity", TaskRequesterViewAssignedTasksActivity.class);

        // choose the first assigned task
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ShowTaskDetailActivity.class);
        assertTrue(solo.searchText(biderName));
        assertTrue(solo.searchText(taskName));
        assertTrue(solo.searchText(bidAmount));
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

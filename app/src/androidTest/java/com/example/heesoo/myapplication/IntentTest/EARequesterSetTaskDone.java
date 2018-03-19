package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.heesoo.myapplication.ChooseMode.ChooseModeActivity;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.Requester.RequesterAssignedTaskListActivity;
import com.example.heesoo.myapplication.Requester.RequesterMainActivity;
import com.example.heesoo.myapplication.Requester.RequesterShowTaskDetailActivity;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/18.
 * User Story: 07.01.01
 */

/* IMPORTANT NOTE : Please DELETE the contents of the database before running intent tests.
    Some tests will not pass if the intent tests have already been run; for example, testRegister
    will not pass if the account you are attempting to create is already in the database from a previous
    iteration of the intent tests.
 */

public class EARequesterSetTaskDone extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public EARequesterSetTaskDone(){
        super(com.example.heesoo.myapplication.Main_LogIn.MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testSetTaskDone(){
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

        // set the task done
        solo.clickOnButton("Mark Done");
        assertTrue(solo.searchText("Task Marked as Done"));
        solo.assertCurrentActivity("Wrong Activity", RequesterAssignedTaskListActivity.class);

        // back to Requester Main Activity
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);
        assertTrue(solo.searchText("Done"));
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}

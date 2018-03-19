package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.heesoo.myapplication.ChooseMode.ChooseModeActivity;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.Provider.ProviderMainActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.Requester.RequesterBiddedTasksListActivity;
import com.example.heesoo.myapplication.Requester.RequesterMainActivity;
import com.example.heesoo.myapplication.Requester.RequesterShowTaskDetailActivity;
import com.example.heesoo.myapplication.Requester.RequesterViewBidsOnTaskActivity;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/18.
 * User Story: 05.05.01
 */

/* IMPORTANT NOTE : Please DELETE the contents of the database before running intent tests.
    Some tests will not pass if the intent tests have already been run; for example, testRegister
    will not pass if the account you are attempting to create is already in the database from a previous
    iteration of the intent tests.
 */

public class DCRequesterSeeBidsForTask extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public DCRequesterSeeBidsForTask(){
        super(com.example.heesoo.myapplication.Main_LogIn.MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testSeeBidsForTask(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.enterText((EditText) solo.getView(R.id.login_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0000");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", ChooseModeActivity.class);
        assertTrue(solo.searchText("Logged In"));

        // choose mode
        solo.clickOnButton("Would you like a task performed for you?");
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        // see bidded task
        solo.clickOnButton("show bidded");
        solo.assertCurrentActivity("Wrong Activity", RequesterBiddedTasksListActivity.class);

        // check it is a bidded task list
        assertTrue(solo.searchText("Bidded"));

        // click the list view in position 0
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", RequesterShowTaskDetailActivity.class);

        // see bids for a task
        solo.clickOnButton("View Bids");
        solo.assertCurrentActivity("Wrong Activity", RequesterViewBidsOnTaskActivity.class);

        // check if there contain placed bids or not
        assertTrue(solo.searchText("Placed"));


    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

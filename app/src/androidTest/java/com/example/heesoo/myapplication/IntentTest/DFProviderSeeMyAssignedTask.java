package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.heesoo.myapplication.ChooseMode.ChooseModeActivity;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.Provider.ProviderMainActivity;
import com.example.heesoo.myapplication.Provider.ProviderViewAssignedTaskDetail;
import com.example.heesoo.myapplication.Provider.ProviderViewBiddedTaskList;
import com.example.heesoo.myapplication.R;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/18.
 * User Story: 06.01.01
 */

/* IMPORTANT NOTE : Please DELETE the contents of the database before running intent tests.
    Some tests will not pass if the intent tests have already been run; for example, testRegister
    will not pass if the account you are attempting to create is already in the database from a previous
    iteration of the intent tests.
 */

public class DFProviderSeeMyAssignedTask extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public DFProviderSeeMyAssignedTask(){
        super(com.example.heesoo.myapplication.Main_LogIn.MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testSeeMyAssignedTask(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.enterText((EditText) solo.getView(R.id.login_username), "user0001");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0001");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", ChooseModeActivity.class);
        assertTrue(solo.searchText("Logged In"));

        // choose mode
        solo.clickOnButton("Would you like to perform a task?");
        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        // see my assigned task for provider
        // click the list view in position 0
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ProviderViewAssignedTaskDetail.class);
        assertTrue(solo.searchText("Requester Username"));
        assertTrue(solo.searchText("Task Name"));
        assertTrue(solo.searchText("Lowest Bid"));
        assertTrue(solo.searchText("My Bid Price"));
        assertTrue(solo.searchText("Task Status"));
        assertTrue(solo.searchText("My Bid Status"));
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

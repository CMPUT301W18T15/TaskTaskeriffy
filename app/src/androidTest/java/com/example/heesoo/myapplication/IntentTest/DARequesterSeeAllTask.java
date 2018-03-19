package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.heesoo.myapplication.ChooseMode.ChooseModeActivity;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.Provider.ProviderFindNewTaskActivity;
import com.example.heesoo.myapplication.Provider.ProviderMainActivity;
import com.example.heesoo.myapplication.Provider.ProviderPlaceBidActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.Requester.RequesterAddTaskActivity;
import com.example.heesoo.myapplication.Requester.RequesterMainActivity;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/18.
 * User Story:
 *
 */

public class DARequesterSeeAllTask extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public DARequesterSeeAllTask(){
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
        MainActivity activity = (MainActivity)solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0000");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", ChooseModeActivity.class);
        assertTrue(solo.searchText("Logged In"));
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testSeeAllTask(){
        solo.assertCurrentActivity("Wrong Activity", ChooseModeActivity.class);
        solo.clickOnButton("Would you like a task performed for you?");
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        // add new task task2
        solo.clickOnButton("Add new task");
        solo.assertCurrentActivity("Wrong Activity", RequesterAddTaskActivity.class);
        solo.enterText((EditText) solo.getView(R.id.taskName), "user0000 task2");
        solo.enterText((EditText) solo.getView(R.id.taskDescription), "user0000 task2 Description");
        solo.clickOnButton("Save");
        assertTrue(solo.searchText("Saving Task"));
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);
        assertTrue(solo.searchText("user0000 task2"));
        assertTrue(solo.searchText("Requested"));

        // add new task task3
        solo.clickOnButton("Add new task");
        solo.assertCurrentActivity("Wrong Activity", RequesterAddTaskActivity.class);
        solo.enterText((EditText) solo.getView(R.id.taskName), "user0000 task3");
        solo.enterText((EditText) solo.getView(R.id.taskDescription), "user0000 task3 Description");
        solo.clickOnButton("Save");
        assertTrue(solo.searchText("Saving Task"));
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);
        assertTrue(solo.searchText("user0000 task3"));
        assertTrue(solo.searchText("Requested"));

        // back to Login page
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ChooseModeActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        // Login as user0001
        solo.clearEditText((EditText) solo.getView(R.id.login_username));
        solo.clearEditText((EditText) solo.getView(R.id.login_password));
        solo.enterText((EditText) solo.getView(R.id.login_username), "user0001");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0001");
        solo.clickOnButton("Login");
        solo.clickOnButton("Would you like to perform a task?");
        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);
        solo.clickOnButton("search new task");
        solo.assertCurrentActivity("Wrong Activity", ProviderFindNewTaskActivity.class);

        // PLACE BID 1
        // click the list view in position 0
        solo.clickInList(0);

        // get a dialog and choose Yes
//        solo.clickOnButton("No");
//        solo.assertCurrentActivity("Wrong Activity", ProviderFindNewTaskActivity.class);
        solo.clickOnButton("Yes");
        solo.assertCurrentActivity("Wrong Activity", ProviderPlaceBidActivity.class);

        // enter Bid Price
        solo.enterText((EditText) solo.getView(R.id.placeBid), "89");
        solo.clickOnButton("Place Bid");
        assertTrue(solo.searchText("Bid Placed"));

        // PLACE BID 2
        // click the list view in position 0
        solo.clickInList(0);

        // get a dialog and choose Yes
//        solo.clickOnButton("No");
//        solo.assertCurrentActivity("Wrong Activity", ProviderFindNewTaskActivity.class);
        solo.clickOnButton("Yes");
        solo.assertCurrentActivity("Wrong Activity", ProviderPlaceBidActivity.class);

        // enter Bid Price
        solo.enterText((EditText) solo.getView(R.id.placeBid), "79");
        solo.clickOnButton("Place Bid");
        assertTrue(solo.searchText("Bid Placed"));

        // go back ProviderMainActivity
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        // go back Login page
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ChooseModeActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        // Login as user0000 again
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.login_username));
        solo.clearEditText((EditText) solo.getView(R.id.login_password));
        solo.enterText((EditText) solo.getView(R.id.login_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0000");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", ChooseModeActivity.class);
        assertTrue(solo.searchText("Logged In"));
        solo.assertCurrentActivity("Wrong Activity", ChooseModeActivity.class);
        solo.clickOnButton("Would you like a task performed for you?");
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        // check if this page contain Bidded status
        assertTrue(solo.searchText("Bidded"));

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

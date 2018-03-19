package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.heesoo.myapplication.ChooseMode.ChooseModeActivity;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.Profile.EditProfileActivity;
import com.example.heesoo.myapplication.Profile.ViewProfileActivity;
import com.example.heesoo.myapplication.Provider.ProviderMainActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.Requester.RequesterMainActivity;
import com.example.heesoo.myapplication.Requester.RequesterAddTaskActivity;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/17.
 * User Story: 01.01.01
 *             01.01.02
 *             01.01.03
 */

/* IMPORTANT NOTE : Please DELETE the contents of the database before running intent tests.
    Some tests will not pass if the intent tests have already been run; for example, testRegister
    will not pass if the account you are attempting to create is already in the database from a previous
    iteration of the intent tests.
 */

public class BAAddTaskTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public BAAddTaskTest(){
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

    public void testAddTask(){
        solo.assertCurrentActivity("Wrong Activity", ChooseModeActivity.class);
        solo.clickOnButton("Would you like a task performed for you?");
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        // add new task page
        solo.clickOnButton("Add new task");
        solo.assertCurrentActivity("Wrong Activity", RequesterAddTaskActivity.class);

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
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);
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

package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Gravity;
import android.widget.EditText;

import com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity;
import com.example.heesoo.myapplication.login_activity.MainActivity;
import com.example.heesoo.myapplication.R;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/17.
 * User Story:
 */

/* IMPORTANT NOTE : Tests must be run in order as some tests depend on previous data.
Please clear the database before running the first intent test.
 */

public class ABUserLoginTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public ABUserLoginTest(){
        super(com.example.heesoo.myapplication.login_activity.MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testLogin(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Please fill in Username and Password"));

        // TODO make sure user USER0000 not exist
        // user not exist
        solo.enterText((EditText) solo.getView(R.id.login_username), "USER0000");
        solo.enterText((EditText) solo.getView(R.id.login_password), "USER0000");
        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Account Does not Exist"));

        // TODO we need a existed user account to test
        // user exist
        // did not enter password
        solo.clearEditText((EditText) solo.getView(R.id.login_username));
        solo.clearEditText((EditText) solo.getView(R.id.login_password));
        solo.enterText((EditText) solo.getView(R.id.login_username), "user0000");
        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Please fill in Username and Password"));

        // password not match
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0001");
        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Password Does not Match"));

        solo.clearEditText((EditText) solo.getView(R.id.login_username));
        solo.clearEditText((EditText) solo.getView(R.id.login_password));
        solo.enterText((EditText) solo.getView(R.id.login_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0000");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);
        assertTrue(solo.searchText("Logged In"));

        // open the navigation bar
        DrawerLayout drawerLayout = (DrawerLayout) solo.getView(R.id.drawer_layout);
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

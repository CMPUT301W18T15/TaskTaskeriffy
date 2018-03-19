package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.heesoo.myapplication.ChooseMode.ChooseModeActivity;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.Register.RegisterActivity;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/17.
 * User Story: 03.01.01
 *             03.01.02
 *             03.01.03
 */

/* IMPORTANT NOTE : Please DELETE the contents of the database before running intent tests.
    Some tests will not pass if the intent tests have already been run; for example, testRegister
    will not pass if the account you are attempting to create is already in the database from a previous
    iteration of the intent tests.
 */


public class AAUserRegistrationTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public AAUserRegistrationTest(){
        super(com.example.heesoo.myapplication.Main_LogIn.MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testClickRegister(){
        MainActivity activity = (MainActivity)solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("register");
        solo.assertCurrentActivity("Wrong Activity", RegisterActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    public void testRegister(){
        // prepare a existed account
        solo.clickOnButton("register");
        solo.enterText((EditText) solo.getView(R.id.enter_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.enter_password), "user0000");
        solo.enterText((EditText) solo.getView(R.id.enter_repeat_password), "user0000");
        solo.enterText((EditText) solo.getView(R.id.enter_email), "user0000@example.com");
        solo.enterText((EditText) solo.getView(R.id.enter_phone), "7800000000");
        solo.clickOnButton("Submit");

        solo.clickOnButton("register");
        solo.assertCurrentActivity("Wrong Activity", RegisterActivity.class);

        // Did not fill anything
        solo.clickOnButton("Submit");
        assertTrue(solo.searchText("All Fields Must Be Filled"));

        // The username has been used
        solo.enterText((EditText) solo.getView(R.id.enter_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.enter_password), "user0000");
        solo.enterText((EditText) solo.getView(R.id.enter_repeat_password), "user0000");
        solo.enterText((EditText) solo.getView(R.id.enter_email), "user0000@example.com");
        solo.enterText((EditText) solo.getView(R.id.enter_phone), "7800000000");
        solo.clickOnButton("Submit");
        assertTrue(solo.searchText("Username Already Exists"));

        // Create a new account
        solo.clearEditText((EditText) solo.getView(R.id.enter_username));
        solo.clearEditText((EditText) solo.getView(R.id.enter_password));
        solo.clearEditText((EditText) solo.getView(R.id.enter_repeat_password));
        solo.clearEditText((EditText) solo.getView(R.id.enter_email));
        solo.clearEditText((EditText) solo.getView(R.id.enter_phone));
        solo.enterText((EditText) solo.getView(R.id.enter_username), "user0001");
        solo.clickOnButton("Submit");
        assertTrue(solo.searchText("All Fields Must Be Filled"));
        solo.enterText((EditText) solo.getView(R.id.enter_password), "user0001");
        solo.clickOnButton("Submit");
        assertTrue(solo.searchText("All Fields Must Be Filled"));
        solo.enterText((EditText) solo.getView(R.id.enter_repeat_password), "user0001");
        solo.clickOnButton("Submit");
        assertTrue(solo.searchText("All Fields Must Be Filled"));
        solo.enterText((EditText) solo.getView(R.id.enter_email), "user0001@example.com");
        solo.clickOnButton("Submit");
        assertTrue(solo.searchText("All Fields Must Be Filled"));
        solo.enterText((EditText) solo.getView(R.id.enter_phone), "7800000001");
        solo.clickOnButton("Submit");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        assertTrue(solo.searchText("Account Registered"));

        // try to login use the account created just now
        solo.enterText((EditText) solo.getView(R.id.login_username), "user0001");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0001");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", ChooseModeActivity.class);
        assertTrue(solo.searchText("Logged In"));
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

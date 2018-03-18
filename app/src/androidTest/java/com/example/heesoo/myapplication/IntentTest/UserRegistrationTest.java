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
 */

public class UserRegistrationTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public UserRegistrationTest(){
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
        solo.clickOnButton("register");
        solo.assertCurrentActivity("Wrong Activity", RegisterActivity.class);

        // Did not fill anything
        solo.clickOnButton("Submit");
        assertTrue(solo.searchText("All fields must be filled"));

        // The username has been used
        // TODO put the account into database
        solo.enterText((EditText) solo.getView(R.id.enter_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.enter_password), "user0000");
        solo.enterText((EditText) solo.getView(R.id.enter_repeat_password), "user0000");
        solo.enterText((EditText) solo.getView(R.id.enter_email), "user0000@example.com");
        solo.enterText((EditText) solo.getView(R.id.enter_phone), "7800000000");
        solo.clickOnButton("Submit");
        assertTrue(solo.searchText("Username already exists"));

        // Create a new account
        solo.clearEditText((EditText) solo.getView(R.id.enter_username));
        solo.clearEditText((EditText) solo.getView(R.id.enter_password));
        solo.clearEditText((EditText) solo.getView(R.id.enter_repeat_password));
        solo.clearEditText((EditText) solo.getView(R.id.enter_email));
        solo.clearEditText((EditText) solo.getView(R.id.enter_phone));
        solo.enterText((EditText) solo.getView(R.id.enter_username), "user0001");
        solo.enterText((EditText) solo.getView(R.id.enter_password), "user0001");
        solo.enterText((EditText) solo.getView(R.id.enter_repeat_password), "user0001");
        solo.enterText((EditText) solo.getView(R.id.enter_email), "user0001@example.com");
        solo.enterText((EditText) solo.getView(R.id.enter_phone), "7800000001");
        solo.clickOnButton("Submit");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        assertTrue(solo.searchText("Account Registered"));

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

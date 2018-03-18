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

public class UserLoginTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public UserLoginTest(){
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

    public void testLogin(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Please fill in Username and Password"));

        // user not exist
        solo.enterText((EditText) solo.getView(R.id.login_username), "USER0000");
        solo.enterText((EditText) solo.getView(R.id.login_password), "USER0000");
        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Account Does not Exist"));

        // TODO we need a existed user account to test
        // user exist
        // password not match
        solo.clearEditText((EditText) solo.getView(R.id.login_username));
        solo.clearEditText((EditText) solo.getView(R.id.login_password));
        solo.enterText((EditText) solo.getView(R.id.login_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0001");
        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Password Does not Match"));

        solo.clearEditText((EditText) solo.getView(R.id.login_username));
        solo.clearEditText((EditText) solo.getView(R.id.login_password));
        solo.enterText((EditText) solo.getView(R.id.login_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0000");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", ChooseModeActivity.class);
        assertTrue(solo.searchText("Logged In"));

        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

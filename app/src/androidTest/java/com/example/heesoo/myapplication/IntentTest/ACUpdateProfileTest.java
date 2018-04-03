package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Gravity;
import android.widget.EditText;

import com.example.heesoo.myapplication.ChooseMode.ChooseModeActivity;
import com.example.heesoo.myapplication.MainTaskActivity;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.Profile.EditProfileActivity;
import com.example.heesoo.myapplication.Profile.ViewProfileActivity;
import com.example.heesoo.myapplication.Provider.ProviderMainActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.Requester.RequesterMainActivity;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/17.
 * User Story: 03.02.01
 */

/* IMPORTANT NOTE : Tests must be run in order as some tests depend on previous data.
Please clear the database before running the first intent test.
 */

public class ACUpdateProfileTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public ACUpdateProfileTest(){
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
        solo.assertCurrentActivity("Wrong Activity", MainTaskActivity.class);
        assertTrue(solo.searchText("Logged In"));
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

//    public void testRequesterUpdateProfile(){
////        solo.assertCurrentActivity("Wrong Activity", ChooseModeActivity.class);
////        solo.clickOnButton("Would you like a task performed for you?");
//
//        // open the navigation bar
//        DrawerLayout drawerLayout = (DrawerLayout) solo.getView(R.id.drawer_layout);
//        drawerLayout.openDrawer(Gravity.LEFT);
//
//        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);
////        solo.clickOnButton("My Account");
//        solo.clickOnMenuItem("My Account");
//        solo.assertCurrentActivity("Wrong Activity", ViewProfileActivity.class);
//        solo.clickOnButton("Edit Information");
//        solo.assertCurrentActivity("Wrong Activity", EditProfileActivity.class);
//
//        // change the profile
//        solo.clearEditText((EditText) solo.getView(R.id.emailAddressEdit));
//        solo.clearEditText((EditText) solo.getView(R.id.phoneNumberEdit));
//        solo.enterText((EditText) solo.getView(R.id.emailAddressEdit), "user0000_changed2@example.com");
//        solo.enterText((EditText) solo.getView(R.id.phoneNumberEdit), "7800009998");
//        solo.clickOnButton("Save Information");
//        solo.goBack();
//        solo.clickOnButton("My Account");
//        assertTrue(solo.searchText("user0000_changed2@example.com"));
//        assertTrue(solo.searchText("7800009998"));
//    }

    public void testProviderUpdateProfile(){
        // get the navigation bar
        DrawerLayout drawerLayout = (DrawerLayout) solo.getView(R.id.drawer_layout);

        // open the navigation bar
        drawerLayout.openDrawer(Gravity.LEFT);

        solo.clickOnMenuItem("My Account");
        solo.assertCurrentActivity("Wrong Activity", ViewProfileActivity.class);
        solo.clickOnButton("Edit Information");
        solo.assertCurrentActivity("Wrong Activity", EditProfileActivity.class);

        // change the profile
        solo.clearEditText((EditText) solo.getView(R.id.emailAddressEdit));
        solo.clearEditText((EditText) solo.getView(R.id.phoneNumberEdit));
        solo.enterText((EditText) solo.getView(R.id.emailAddressEdit), "user0000_changed@example.com");
        solo.enterText((EditText) solo.getView(R.id.phoneNumberEdit), "7800009999");
        solo.clickOnButton("Save Information");
        solo.goBack();

        // open the navigation bar
        drawerLayout.openDrawer(Gravity.LEFT);
        solo.clickOnMenuItem("My Account");

        assertTrue(solo.searchText("user0000_changed@example.com"));
        assertTrue(solo.searchText("7800009999"));
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

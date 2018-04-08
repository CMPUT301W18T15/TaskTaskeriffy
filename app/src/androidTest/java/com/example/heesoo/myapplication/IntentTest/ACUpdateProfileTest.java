package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Gravity;
import android.widget.EditText;

import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchUserController;
import com.example.heesoo.myapplication.entities.User;
import com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity;
import com.example.heesoo.myapplication.login_activity.MainActivity;
import com.example.heesoo.myapplication.profile_activities.EditProfileActivity;
import com.example.heesoo.myapplication.profile_activities.ViewProfileActivity;
import com.example.heesoo.myapplication.R;
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
        super(com.example.heesoo.myapplication.login_activity.MainActivity.class);

        // ensure the test accounts exist
        User user0 = new User("user0000", "user0000", "user0000@example.com", "7800000000");
        User user1 = new User("user0001", "user0001", "user0001@example.com", "7800000001");
        ElasticSearchUserController.AddUserTask addUserTask = new ElasticSearchUserController.AddUserTask();
        addUserTask.execute(user0, user1);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());

        // Login as user0000
        MainActivity activity = (MainActivity)solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0000");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);
        assertTrue(solo.searchText("Logged In"));
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testProviderUpdateProfile(){
        // get the navigation bar
        DrawerLayout drawerLayout = (DrawerLayout) solo.getView(R.id.drawerLayout);

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

        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

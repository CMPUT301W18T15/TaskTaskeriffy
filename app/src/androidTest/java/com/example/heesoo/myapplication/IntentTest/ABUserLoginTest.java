package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;

import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchUserController;
import com.example.heesoo.myapplication.entities.User;
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
        // ensure the test accounts exist
        User user0 = new User("KevinHP", "KevinHP", "KevinHP@example.com", "7800000000");
        User user1 = new User("RiyaRiya", "RiyaRiya", "RiyaRiya@example.com", "7800000001");
        User user2 = new User("ManuelaKM", "ManuelaKM", "ManuelaKM@example.com", "7800000000");
        ElasticSearchUserController.AddUserTask addUserTask = new ElasticSearchUserController.AddUserTask();
        ElasticSearchUserController.DeleteProfile deleteUser = new ElasticSearchUserController.DeleteProfile();
        addUserTask.execute(user0, user1);
        try {
            deleteUser.execute(user2);
        } catch (Exception e){
            Log.i("clear database","the user has been deleted");
        }
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

//    public void testStart() throws Exception{
//        Activity activity = getActivity();
//    }

    public void testLogin(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Please fill in Username and Password"));

        // TODO make sure user ManuelaKM not exist
        // user not exist
        solo.enterText((EditText) solo.getView(R.id.login_username), "ManuelaKM");
        solo.enterText((EditText) solo.getView(R.id.login_password), "ManuelaKM");
        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Account Does not Exist"));

        // TODO we need a existed user account to test
        // user exist
        // did not enter password
        solo.clearEditText((EditText) solo.getView(R.id.login_username));
        solo.clearEditText((EditText) solo.getView(R.id.login_password));
        solo.enterText((EditText) solo.getView(R.id.login_username), "KevinHP");
        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Please fill in Username and Password"));

        // password not match
        solo.enterText((EditText) solo.getView(R.id.login_password), "RiyaRiya");
        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Password Does not Match"));

        solo.clearEditText((EditText) solo.getView(R.id.login_username));
        solo.clearEditText((EditText) solo.getView(R.id.login_password));
        solo.enterText((EditText) solo.getView(R.id.login_username), "KevinHP");
        solo.enterText((EditText) solo.getView(R.id.login_password), "KevinHP");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);
        assertTrue(solo.searchText("Logged In"));

        // open the navigation bar
        DrawerLayout drawerLayout = (DrawerLayout) solo.getView(R.id.drawerLayout);
        drawerLayout.openDrawer(Gravity.LEFT);
        solo.clickOnMenuItem("Logout");

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

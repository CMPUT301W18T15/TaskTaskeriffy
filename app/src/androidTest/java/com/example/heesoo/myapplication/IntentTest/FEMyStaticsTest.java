package com.example.heesoo.myapplication.IntentTest;

import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;

import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchUserController;
import com.example.heesoo.myapplication.entities.User;
import com.example.heesoo.myapplication.login_activity.MainActivity;
import com.example.heesoo.myapplication.profile_activities.MyStatisticsActivity;
import com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity;
import com.robotium.solo.Solo;

/**
 * Created by echo on 2018-04-08.
 */

public class FEMyStaticsTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public FEMyStaticsTest(){
        super(com.example.heesoo.myapplication.login_activity.MainActivity.class);
        // ensure the test accounts exist
        User user0 = new User("KevinHP", "KevinHP", "KevinHP@example.com", "7800000000");
        User user1 = new User("RiyaRiya", "RiyaRiya", "RiyaRiya@example.com", "7800000001");
        User user2 = new User("ManuelaKM", "ManuelaKM", "ManuelaKM@example.com", "7800000000");
        user0.setRating(Double.valueOf(4.5));
        user0.updateTotalEarnings(Double.valueOf(170));
        user0.updateCompletedProvidedTasks();
        user0.updateCompletedProvidedTasks();
        user0.updateCompletedProvidedTasks();
        user0.updateCompletedProvidedTasks();
        user0.updateCompletedPostedTasks();
        user0.updateCompletedPostedTasks();

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
        // Login as KevinHP
        MainActivity activity = (MainActivity)solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username), "KevinHP");
        solo.enterText((EditText) solo.getView(R.id.login_password), "KevinHP");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);
        assertTrue(solo.searchText("Logged In"));
    }

    public void testWow(){
        // get the navigation bar
        DrawerLayout drawerLayout = (DrawerLayout) solo.getView(R.id.drawerLayout);

        // open the navigation bar
        drawerLayout.openDrawer(Gravity.LEFT);
        solo.clickOnMenuItem("My Statistics");
        solo.assertCurrentActivity("Wrong Activity", MyStatisticsActivity.class);

        try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }







    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }


}

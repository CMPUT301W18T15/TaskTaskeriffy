package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.example.heesoo.myapplication.task_provider_activities.FindNewTaskActivity;
import com.example.heesoo.myapplication.task_provider_activities.PlaceBidOnTaskActivity;
import com.example.heesoo.myapplication.task_provider_activities.TaskProviderViewBiddedTaskListActivity;
import com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity;
import com.example.heesoo.myapplication.login_activity.MainActivity;
import com.example.heesoo.myapplication.task_provider_activities.TaskProviderViewAssignedTaskDetailActivity;
import com.example.heesoo.myapplication.R;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/18.
 * User Story: 05.01.01
 *             05.03.01 *does not achieved
 */

/* IMPORTANT NOTE : Tests must be run in order as some tests depend on previous data.
Please clear the database before running the first intent test.
 */

public class CBPlaceBidTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public CBPlaceBidTest(){
        super(com.example.heesoo.myapplication.login_activity.MainActivity.class);
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
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testPlaceBid(){
        // Login as user0000 Requester
        solo.enterText((EditText) solo.getView(R.id.login_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0000");
        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Logged In"));

        // add sample task: user0000 task1
        solo.clickOnButton("Add Task");
        solo.enterText((EditText) solo.getView(R.id.taskName), "user0000 task1");
        solo.enterText((EditText) solo.getView(R.id.taskDescription), "user0000 task1 Description");
        solo.clickOnButton("Save");
        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);

        // back to login activity
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        // Login as user0001 Provider
        solo.clearEditText((EditText) solo.getView(R.id.login_username));
        solo.clearEditText((EditText) solo.getView(R.id.login_password));
        solo.enterText((EditText) solo.getView(R.id.login_username), "user0001");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0001");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);

        // get the navigation bar
        DrawerLayout drawerLayout = (DrawerLayout) solo.getView(R.id.drawer_layout);

        // open the navigation bar
        drawerLayout.openDrawer(Gravity.LEFT);
        solo.clickOnMenuItem("Find New Tasks");
        solo.assertCurrentActivity("Wrong Activity", FindNewTaskActivity.class);

        // * click the search button and
        // * enter search keyword
        Activity activity = solo.getCurrentActivity();
        int id = activity.getResources().getIdentifier("item_search", "id", solo.getCurrentActivity().getPackageName());
        View view = solo.getView(id);
        solo.clickOnView(view);
        //solo.typeText(0, "user0000");
        solo.enterText(0, "user0000");

        // click the list view in position 0
        solo.clickInList(0);

        // get a dialog and choose Yes
//        solo.clickOnButton("No");
//        solo.assertCurrentActivity("Wrong Activity", FindNewTaskActivity.class);
        solo.clickOnButton("Yes");
        solo.assertCurrentActivity("Wrong Activity", PlaceBidOnTaskActivity.class);

        // leave bidPrice blank
        solo.clickOnButton("Place Bid");
        assertTrue(solo.searchText("Please Fill the Bid Price"));

        // enter Bid Price
        solo.enterText((EditText) solo.getView(R.id.placeBid), "99");
        solo.clickOnButton("Place Bid");
        assertTrue(solo.searchText("Bid Placed"));

        // go back FindNewTaskActivity
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", FindNewTaskActivity.class);

        // quit search
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", FindNewTaskActivity.class);

        // go back main
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);

        // see bidded list
        // open the navigation bar
        drawerLayout.openDrawer(Gravity.LEFT);
        solo.clickOnMenuItem("My Bidded Tasks");
        solo.assertCurrentActivity("Wrong Activity", TaskProviderViewBiddedTaskListActivity.class);

//        solo.clickOnButton("View bidded list");
//        solo.assertCurrentActivity("Wrong Activity", TaskProviderViewBiddedTaskListActivity.class);
        solo.clickInList(0);
        solo.clickOnButton("Yes");
        solo.assertCurrentActivity("Wrong Activity", TaskProviderViewAssignedTaskDetailActivity.class);
        assertTrue(solo.searchText("99"));
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

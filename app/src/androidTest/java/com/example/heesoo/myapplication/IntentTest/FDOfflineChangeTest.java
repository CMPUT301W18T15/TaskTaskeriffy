package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.general_activities.ShowPhotoActivity;
import com.example.heesoo.myapplication.profile_activities.ViewProfileActivity;
import com.example.heesoo.myapplication.task_provider_activities.FindNewTaskActivity;
import com.example.heesoo.myapplication.task_provider_activities.PlaceBidOnTaskActivity;
import com.example.heesoo.myapplication.task_requester_activities.AddTaskActivity;
import com.example.heesoo.myapplication.task_requester_activities.EditTaskActivity;
import com.example.heesoo.myapplication.task_requester_activities.ShowTaskDetailActivity;
import com.example.heesoo.myapplication.task_requester_activities.TaskMapActivity;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewAssignedTasksActivity;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewBiddedTasksActivity;
import com.example.heesoo.myapplication.task_requester_activities.ViewBidDetailActivity;
import com.example.heesoo.myapplication.task_requester_activities.ViewBidsOnTaskActivity;
import com.robotium.solo.Solo;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchBidController;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchClearDatabaseController;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchUserController;
import com.example.heesoo.myapplication.entities.Bid;
import com.example.heesoo.myapplication.entities.BidList;
import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.entities.TaskList;
import com.example.heesoo.myapplication.entities.User;
import com.example.heesoo.myapplication.shared_preferences.SetPublicCurrentUser;
import com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity;
import com.example.heesoo.myapplication.login_activity.MainActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.login_activity.RegisterActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by echo on 2018-04-04.
 * User Story: 08.01.01
 */

public class FDOfflineChangeTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    public FDOfflineChangeTest() {
        super(com.example.heesoo.myapplication.login_activity.MainActivity.class);

        // ensure the test accounts exist
        User user0 = new User("KevinHP", "KevinHP", "KevinHP@example.com", "7800000000");
        User user1 = new User("RiyaRiya", "RiyaRiya", "RiyaRiya@example.com", "7800000001");
        ElasticSearchUserController.AddUserTask addUserTask = new ElasticSearchUserController.AddUserTask();
        addUserTask.execute(user0, user1);

        // deleted the created tasks
        TaskList allTasks;
        ElasticSearchTaskController.GetAllTasks getAllTasks = new ElasticSearchTaskController.GetAllTasks();
        getAllTasks.execute("");
        try {
            allTasks = getAllTasks.get();
            for(int i = 0; i < allTasks.getSize(); i++) {
                Task task = allTasks.getTask(i);
                if (task.getTaskRequester().equals("KevinHP")){
                    Log.d("REQUESTCODE", task.getTaskName());
                    ElasticSearchTaskController.DeleteTask deleteTask = new ElasticSearchTaskController.DeleteTask();
                    deleteTask.execute(task);
                }
            }
        }
        catch (Exception e) {
            Log.i("Error", "The request for tweets failed in onStart");
        }
        // delete created bids
        BidList bidList;
        ElasticSearchBidController.GetAllBids getAllBids = new ElasticSearchBidController.GetAllBids();
        getAllBids.execute("");
        try {
            bidList = getAllBids.get();
            for(int i = 0; i < bidList.size(); i++) {
                Bid bid = bidList.get(i);
                if (bid.getTaskRequester().equals("KevinHP") || bid.getTaskProvider().equals("RiyaRiya")){
                    Log.d("REQUESTCODE", bid.getTaskName());
                    ElasticSearchBidController.DeleteBidTask deleteBid = new ElasticSearchBidController.DeleteBidTask();
                    deleteBid.execute(bid);
                }
            }
        }
        catch (Exception e) {
            Log.i("Error", "The request for tweets failed in onStart");
        }
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());

        solo.setMobileData(true);

        // Login as KevinHP
        MainActivity activity = (MainActivity)solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username), "KevinHP");
        solo.enterText((EditText) solo.getView(R.id.login_password), "KevinHP");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);
        assertTrue(solo.searchText("Logged In"));
    }

    public void testOfflineBehaviour (){
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WifiManager wifiManager = (WifiManager)this.getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(false);
        // set the mobile data status offline for testing

        //Add a task
        // add new task page
        solo.clickOnButton("Add Task");
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);

        // fill both task name and description
        solo.enterText((EditText) solo.getView(R.id.taskName), "House cleaning");
        solo.enterText((EditText) solo.getView(R.id.taskDescription), "Square Feet: 2000, 3 floors, address: 11111St, 99Ave, NW");
        solo.clickOnButton("Save");

        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);
        assertTrue(solo.searchText("House cleaning"));

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ShowTaskDetailActivity.class);
        assertTrue(solo.searchText("House cleaning"));
        // edit the task with empty task name
        solo.clickOnButton("Edit Task");
        solo.assertCurrentActivity("Wrong Activity", EditTaskActivity.class);
        // edit the task name
        solo.clearEditText((EditText) solo.getView(R.id.taskNameEdit));
        solo.enterText((EditText) solo.getView(R.id.taskNameEdit), "House and garden cleaning");
        solo.clearEditText((EditText) solo.getView(R.id.descriptionEdit));
        solo.enterText((EditText) solo.getView(R.id.descriptionEdit), "Square Feet: 2000, 3 floors, garden square feet: 200, address: 11111St, 99Ave, NW");
        solo.clickOnButton("Save Changes");
        assertTrue(solo.searchText("Saving Task"));
        solo.assertCurrentActivity("Wrong Activity", ShowTaskDetailActivity.class);
        assertTrue(solo.searchText("House and garden cleaning"));
        solo.goBack();

        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wifiManager.setWifiEnabled(true);


        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);
        assertTrue(solo.searchText("House and garden cleaning"));

        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
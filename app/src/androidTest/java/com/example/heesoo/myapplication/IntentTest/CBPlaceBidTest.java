package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchBidController;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchUserController;
import com.example.heesoo.myapplication.entities.Bid;
import com.example.heesoo.myapplication.entities.BidList;
import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.entities.TaskList;
import com.example.heesoo.myapplication.entities.User;
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
        // ensure the test accounts exist
        User user0 = new User("user0000", "user0000", "user0000@example.com", "7800000000");
        User user1 = new User("user0001", "user0001", "user0001@example.com", "7800000001");
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
                if (task.getTaskRequester().equals("user0000")){
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
                if (bid.getTaskRequester().equals("user0000") || bid.getTaskProvider().equals("user0001")){
                    Log.d("REQUESTCODE", bid.getTaskName());
                    ElasticSearchBidController.DeleteBidTask deleteBid = new ElasticSearchBidController.DeleteBidTask();
                    deleteBid.execute(bid);
                }
            }
        }
        catch (Exception e) {
            Log.i("Error", "The request for tweets failed in onStart");
        }
        // create the test viewed task
        Task task = new Task("user0000", "House and garden cleaning", "Square Feet: 2000, 3 floors, garden square feet: 200, address: 11111St, 99Ave, NW");
        ElasticSearchTaskController.AddTask addTasksTask = new ElasticSearchTaskController.AddTask();
        addTasksTask.execute(task);
        try {
            Thread.currentThread().sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

//    public void testStart() throws Exception{
//        Activity activity = getActivity();
//    }

    public void testPlaceBid(){
//        // Login as user0000 Requester
//        solo.enterText((EditText) solo.getView(R.id.login_username), "user0000");
//        solo.enterText((EditText) solo.getView(R.id.login_password), "user0000");
//        solo.clickOnButton("Login");
//        assertTrue(solo.searchText("Logged In"));
//
//        // add sample task: user0000 task1
//        solo.clickOnButton("Add Task");
//        solo.enterText((EditText) solo.getView(R.id.taskName), "user0000 task1");
//        solo.enterText((EditText) solo.getView(R.id.taskDescription), "user0000 task1 Description");
//        solo.clickOnButton("Save");
//        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);
//
//        // back to login activity
//        solo.goBack();
//        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        // Login as user0001 Provider
//        solo.clearEditText((EditText) solo.getView(R.id.login_username));
//        solo.clearEditText((EditText) solo.getView(R.id.login_password));
        solo.enterText((EditText) solo.getView(R.id.login_username), "user0001");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0001");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);

        // get the navigation bar
        DrawerLayout drawerLayout = (DrawerLayout) solo.getView(R.id.drawerLayout);

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
        solo.enterText(0, "House and garden cleaning");

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
        solo.enterText((EditText) solo.getView(R.id.placeBid), "500");
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
        solo.clickOnMenuItem("Tasks I have Bidded On");
        solo.assertCurrentActivity("Wrong Activity", TaskProviderViewBiddedTaskListActivity.class);

//        solo.clickOnButton("View bidded list");
//        solo.assertCurrentActivity("Wrong Activity", TaskProviderViewBiddedTaskListActivity.class);
        solo.clickInList(0);
        solo.clickOnButton("Yes");
        solo.assertCurrentActivity("Wrong Activity", TaskProviderViewAssignedTaskDetailActivity.class);
        assertTrue(solo.searchText("500"));

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

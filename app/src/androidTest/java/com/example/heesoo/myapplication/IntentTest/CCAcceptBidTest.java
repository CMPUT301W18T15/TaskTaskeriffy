package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchBidController;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchUserController;
import com.example.heesoo.myapplication.entities.Bid;
import com.example.heesoo.myapplication.entities.BidList;
import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.entities.TaskList;
import com.example.heesoo.myapplication.entities.User;
import com.example.heesoo.myapplication.shared_preferences.SetPublicCurrentUser;
import com.example.heesoo.myapplication.task_provider_activities.FindNewTaskActivity;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewAssignedTasksActivity;
import com.example.heesoo.myapplication.task_requester_activities.ViewBidDetailActivity;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewBiddedTasksActivity;
import com.example.heesoo.myapplication.task_requester_activities.ShowTaskDetailActivity;
import com.example.heesoo.myapplication.task_requester_activities.ViewBidsOnTaskActivity;
import com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/18.
 * User Story: 05.06.01
 */

/* IMPORTANT NOTE : Tests must be run in order as some tests depend on previous data.
Please clear the database before running the first intent test.
 */

public class CCAcceptBidTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public CCAcceptBidTest(){
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

        // create the test viewed task
        Task task = new Task("KevinHP", "House and garden cleaning", "Square Feet: 2000, 3 floors, garden square feet: 200, address: 11111St, 99Ave, NW");
        Bid bid = new Bid("House and garden cleaning", "Square Feet: 2000, 3 floors, garden square feet: 200, address: 11111St, 99Ave, NW", 500f, "RiyaRiya", "KevinHP");
        ElasticSearchTaskController.AddTask addTasksTask = new ElasticSearchTaskController.AddTask();
        ElasticSearchBidController.AddBidsTask addBidsTask = new ElasticSearchBidController.AddBidsTask();
        addTasksTask.execute(task);
        try {
            Thread.currentThread().sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addBidsTask.execute(bid);
        task.addBid(bid);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

//    public void testStart() throws Exception{
//        Activity activity = getActivity();
//    }

    public void testAcceptBid(){
        // Login as KevinHP Requester
        solo.enterText((EditText) solo.getView(R.id.login_username), "KevinHP");
        solo.enterText((EditText) solo.getView(R.id.login_password), "KevinHP");
        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Logged In"));
        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);

        // get the navigation bar
        DrawerLayout drawerLayout = (DrawerLayout) solo.getView(R.id.drawerLayout);

        // open the navigation bar
        drawerLayout.openDrawer(Gravity.LEFT);
        // show requester bidded task list
        solo.clickOnMenuItem("My Bidded Tasks");
        solo.assertCurrentActivity("Wrong Activity", TaskRequesterViewBiddedTasksActivity.class);

        // click the list view in position 0
        // see detail of a task
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ShowTaskDetailActivity.class);

        // see the bid list of a task
        solo.clickOnButton("View Bids");
        solo.assertCurrentActivity("Wrong Activity", ViewBidsOnTaskActivity.class);

        // choose the first bid
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ViewBidDetailActivity.class);

        // get current bid information for later check
        TextView view1 =  (TextView) solo.getView(R.id.bidderName);
        TextView view2 =  (TextView) solo.getView(R.id.taskName);
        TextView view3 =  (TextView) solo.getView(R.id.bidAmount);
        String biderName = view1.getText().toString();
        String taskName = view2.getText().toString();
        String bidAmount = view3.getText().toString();

        // accept current bid
        solo.clickOnButton("acceptBid");
        assertTrue(solo.searchText("Bid Accepted"));

        // back to requester main activity
        solo.assertCurrentActivity("Wrong Activity", ViewBidsOnTaskActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ShowTaskDetailActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", TaskRequesterViewBiddedTasksActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);

        // open the navigation bar
        drawerLayout.openDrawer(Gravity.LEFT);
        // show requester assigned task list
        solo.clickOnMenuItem("My Assigned Tasks");
        solo.assertCurrentActivity("Wrong Activity", TaskRequesterViewAssignedTasksActivity.class);

        // choose the first assigned task
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ShowTaskDetailActivity.class);
        assertTrue(solo.searchText(biderName));
        assertTrue(solo.searchText(taskName));
        assertTrue(solo.searchText(bidAmount));

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

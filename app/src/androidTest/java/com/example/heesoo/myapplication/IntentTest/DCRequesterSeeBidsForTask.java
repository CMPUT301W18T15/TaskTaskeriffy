package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.Display;
import android.widget.EditText;

import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchBidController;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchUserController;
import com.example.heesoo.myapplication.entities.Bid;
import com.example.heesoo.myapplication.entities.BidList;
import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.entities.TaskList;
import com.example.heesoo.myapplication.entities.User;
import com.example.heesoo.myapplication.login_activity.MainActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewBiddedTasksActivity;
import com.example.heesoo.myapplication.task_requester_activities.ShowTaskDetailActivity;
import com.example.heesoo.myapplication.task_requester_activities.ViewBidsOnTaskActivity;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/18.
 * User Story: 05.05.01
 */

/* IMPORTANT NOTE : Tests must be run in order as some tests depend on previous data.
Please clear the database before running the first intent test.
 */

public class DCRequesterSeeBidsForTask extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public DCRequesterSeeBidsForTask(){
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
        Task task2 = new Task("KevinHP", "Design a Christmas tree", "I have a pine in my garden, please help me to decorate it for Christmas.");
        Task task3 = new Task("KevinHP", "Finding old books","I want 100 old books to fill my shelf.");
        Bid bid = new Bid("House and garden cleaning", "Square Feet: 2000, 3 floors, garden square feet: 200, address: 11111St, 99Ave, NW", 500f, "RiyaRiya", "KevinHP");
        Bid bid2 = new Bid("Design a Christmas tree", "I have a pine in my garden, please help me to decorate it for Christmas.", 300f, "RiyaRiya", "KevinHP");

        ElasticSearchTaskController.AddTask addTasksTask = new ElasticSearchTaskController.AddTask();
        ElasticSearchBidController.AddBidsTask addBidsTask = new ElasticSearchBidController.AddBidsTask();
        addTasksTask.execute(task, task2, task3);
        try {
            Thread.currentThread().sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        task.addBid(bid);
        task2.addBid(bid2);
        addBidsTask.execute(bid, bid2);
        task.acceptBid(bid.getTaskProvider());
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

//    public void testStart() throws Exception{
//        Activity activity = getActivity();
//    }

    public void testSeeBidsForTask(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.enterText((EditText) solo.getView(R.id.login_username), "KevinHP");
        solo.enterText((EditText) solo.getView(R.id.login_password), "KevinHP");
        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Logged In"));

        // see bidded task
        Display display = solo.getCurrentActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        solo.drag(0, width / 2, height / 2, height / 2, 1);
        solo.drag(width/3, width/3, width/2, width/10, 1);
        solo.clickOnMenuItem("My Bidded Tasks");
        solo.assertCurrentActivity("Wrong Activity", TaskRequesterViewBiddedTasksActivity.class);

        // check no Assigned and Requested task
        assertTrue(solo.searchText("Bidded"));

        // click the list view in position 0
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ShowTaskDetailActivity.class);

        // see bids for a task
        solo.clickOnButton("View Bids");
        solo.assertCurrentActivity("Wrong Activity", ViewBidsOnTaskActivity.class);

        // check if there contain placed bids or not
        assertTrue(solo.searchText("Placed"));

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

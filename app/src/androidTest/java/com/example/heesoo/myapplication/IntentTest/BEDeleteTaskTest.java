package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchBidController;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchUserController;
import com.example.heesoo.myapplication.entities.Bid;
import com.example.heesoo.myapplication.entities.BidList;
import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.entities.TaskList;
import com.example.heesoo.myapplication.entities.User;
import com.example.heesoo.myapplication.task_requester_activities.AddTaskActivity;
import com.example.heesoo.myapplication.task_requester_activities.ShowTaskDetailActivity;
import com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity;
import com.example.heesoo.myapplication.R;

import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/17.
 * User Story: 01.03.01
 */

/* IMPORTANT NOTE : Tests must be run in order as some tests depend on previous data.
Please clear the database before running the first intent test.
 */

public class BEDeleteTaskTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public BEDeleteTaskTest(){
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
        Task task = new Task("KevinHP", "House cleaning", "Square Feet: 2000, 3 floors, address: 11111St, 99Ave, NW");
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

        // Login as KevinHP
        solo.enterText((EditText) solo.getView(R.id.login_username), "KevinHP");
        solo.enterText((EditText) solo.getView(R.id.login_password), "KevinHP");
        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Logged In"));
    }

//    public void testStart() throws Exception{
//        Activity activity = getActivity();
//    }

    public void testDeleteTask(){
        // click the list view in position 0
        // show the task details
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ShowTaskDetailActivity.class);
        assertTrue(solo.searchText("House cleaning"));
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);

        // delete the task
        solo.clickInList(0);
        solo.clickOnButton("Delete Task");
        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);
        assertFalse(solo.searchText("House cleaning"));
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

package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

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
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/17.
 * User Story: 03.01.01
 *             03.01.02
 *             03.01.03
 */

/* IMPORTANT NOTE : Tests must be run in order as some tests depend on previous data.
Please clear the database before running the first intent test.
 */


public class AAUserRegistrationTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public AAUserRegistrationTest(){
        super(com.example.heesoo.myapplication.login_activity.MainActivity.class);
        // clear the database
        ElasticSearchClearDatabaseController deleteTask0 = new ElasticSearchClearDatabaseController();
        try {
            deleteTask0.deleteIndex();
        } catch (Exception e) {
            Log.i("Error", "Fail to delete the database");
        }

        // delete the created user
        User user0 = new User("KevinHP", "KevinHP", "KevinHP@example.com", "7800000000");
        User user1 = new User("RiyaRiya", "RiyaRiya", "RiyaRiya@example.com", "7800000001");
        User user2 = new User("ManuelaKM", "ManuelaKM", "ManuelaKM@example.com", "7800000000");
        ElasticSearchUserController.DeleteProfile deleteUser0 = new ElasticSearchUserController.DeleteProfile();
        try {
            deleteUser0.execute(user0, user1, user2);
        } catch (Exception e){
            Log.i("clear database","the user has been deleted");
        }
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

    }

//    public void testStart() throws Exception{
//        Activity activity = getActivity();
//    }

    public void testClickRegister(){
        MainActivity activity = (MainActivity)solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("register");
        solo.assertCurrentActivity("Wrong Activity", RegisterActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    public void testRegister(){
        // prepare a existed account
        solo.clickOnButton("register");
        solo.enterText((EditText) solo.getView(R.id.enter_username), "KevinHP");
        solo.enterText((EditText) solo.getView(R.id.enter_password), "KevinHP");
        solo.enterText((EditText) solo.getView(R.id.enter_repeat_password), "KevinHP");
        solo.enterText((EditText) solo.getView(R.id.enter_email), "KevinHP@example.com");
        solo.enterText((EditText) solo.getView(R.id.enter_phone), "7800000000");
        solo.clickOnButton("Submit");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnButton("register");
        solo.assertCurrentActivity("Wrong Activity", RegisterActivity.class);

        // Did not fill anything
        solo.clickOnButton("Submit");
        assertTrue(solo.searchText("All Fields Must Be Filled"));

        // The username has been used
        solo.enterText((EditText) solo.getView(R.id.enter_username), "KevinHP");
        solo.enterText((EditText) solo.getView(R.id.enter_password), "KevinHP");
        solo.enterText((EditText) solo.getView(R.id.enter_repeat_password), "KevinHP");
        solo.enterText((EditText) solo.getView(R.id.enter_email), "KevinHP@example.com");
        solo.enterText((EditText) solo.getView(R.id.enter_phone), "7800000000");
        solo.clickOnButton("Submit");
        assertTrue(solo.searchText("Username Already Exists"));

        // Create a new account
        solo.clearEditText((EditText) solo.getView(R.id.enter_username));
        solo.clearEditText((EditText) solo.getView(R.id.enter_password));
        solo.clearEditText((EditText) solo.getView(R.id.enter_repeat_password));
        solo.clearEditText((EditText) solo.getView(R.id.enter_email));
        solo.clearEditText((EditText) solo.getView(R.id.enter_phone));
        solo.enterText((EditText) solo.getView(R.id.enter_username), "RiyaRiya");
        solo.clickOnButton("Submit");
        assertTrue(solo.searchText("All Fields Must Be Filled"));
        solo.enterText((EditText) solo.getView(R.id.enter_password), "RiyaRiya");
        solo.clickOnButton("Submit");
        assertTrue(solo.searchText("All Fields Must Be Filled"));
        solo.enterText((EditText) solo.getView(R.id.enter_repeat_password), "RiyaRiya");
        solo.clickOnButton("Submit");
        assertTrue(solo.searchText("All Fields Must Be Filled"));
        solo.enterText((EditText) solo.getView(R.id.enter_email), "RiyaRiya@example.com");
        solo.clickOnButton("Submit");
        assertTrue(solo.searchText("All Fields Must Be Filled"));
        solo.enterText((EditText) solo.getView(R.id.enter_phone), "7800000001");
        solo.clickOnButton("Submit");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        assertTrue(solo.searchText("Account Registered"));

        // try to login use the account created just now
        solo.enterText((EditText) solo.getView(R.id.login_username), "RiyaRiya");
        solo.enterText((EditText) solo.getView(R.id.login_password), "RiyaRiya");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity", ViewRequestedTasksActivity.class);
        assertTrue(solo.searchText("Logged In"));
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

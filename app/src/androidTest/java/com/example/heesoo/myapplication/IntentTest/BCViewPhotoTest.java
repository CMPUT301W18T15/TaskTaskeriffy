package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
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
import com.example.heesoo.myapplication.task_requester_activities.ShowTaskDetailActivity;
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

/**
 * Created by echo on 2018-04-04.
 *
 */

public class BCViewPhotoTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    public BCViewPhotoTest() {
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
        //sample picture
        String pic = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAFA3PEY8MlBGQUZaVVBfeMiCeG5uePWvuZHI////////\n////////////////////////////////////////////2wBDAVVaWnhpeOuCguv/////////////\n////////////////////////////////////////////////////////////wAARCAGQAZADASIA\nAhEBAxEB/8QAGAABAQEBAQAAAAAAAAAAAAAAAAECAwT/xAAyEAACAgAGAAUEAgICAQUAAAAAAQIR\nAxIhMUFRE1JhcZEEIoGhMkIzYiNT4RSxwdHw/8QAFwEBAQEBAAAAAAAAAAAAAAAAAAECA//EABsR\nAQEBAQEBAQEAAAAAAAAAAAABEQIhMUES/9oADAMBAAIRAxEAPwDgCFNgAAAAAAAAAAAAAAAAAAAA\nAAAAAAAAAAAAAAAAAAADcUgyIrZFQyzRlgQABEBRQELQoAKFFBQFAACkKUCoIqLGWJLLJrohrF/y\nyMmI0oBTSICgKgKAiAoAgKAqAoAgAIAA3JasiWTMG17mdDOjWYZjNAaN5inMpdRsETKaAAAABQAq\nQSLsA2JYbIAIUEEKAUAAAAoqQChRqhQVmhRoUBmi0WgWIUdMGGafojB68GGWHqx1ciPDi/5ZGTWL\n/lkZMxVKQpUAAUAAAAAUAAQAAAjdINpbmZNv0MWrGVJ2au9DFFTMqpKLRALTQC9C77gShVF1j6oq\np7P8AQqYrXpk5LKNAIptELYouwEsllIAIUAAAAAAAAoBKzokZibIsQllsywFggKNAhUajNdMGGaf\noj17IxgwywJjzyx9WYvtHhxf8sjJrF/ySMoT4qlIUqAAKAACgAAAAAACCPYxuWW76Ikc1HsWMJS1\n4EVnmlwelLQLI4PDkvUzs6f7PTRmUE1qiLjhl5QRqnF09iyjpaKjNNbajSXowm/yi0pK+QFtaSQa\nr1QT4ZY6aP8AAGUatkSqVdmmbiVLYAKgAAICgCApYxbAlFUTeQ0o6DVxyoUdHEZaBjCVFNMy9CKb\nEslkCNNksllNRKp1wIZpXwjklZ7cKGSHqOrkRvZHjxp5p+x6MeeWNcnjZOZ+lZxf8kjJrE/ySIiR\nQoBpAFRUiKlCjdEaBjAK4+pKAAAAR7GiNWKMDZBR+6mJqnRzVvAWrZ3OeCqijqStRAaSI0Fc5RUl\n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\n";
        task.addPicture(pic);
        Bid bid = new Bid("House and garden cleaning", "Square Feet: 2000, 3 floors, garden square feet: 200, address: 11111St, 99Ave, NW", 500f, "RiyaRiya", "KevinHP");
        ElasticSearchTaskController.AddTask addTasksTask = new ElasticSearchTaskController.AddTask();
        ElasticSearchBidController.AddBidsTask addBidsTask = new ElasticSearchBidController.AddBidsTask();
        addTasksTask.execute(task);
        try {
            Thread.currentThread().sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        task.addBid(bid);
        addBidsTask.execute(bid);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }



    public void testViewPhoto (){

        // Login as Riya Provider
        solo.enterText((EditText) solo.getView(R.id.login_username), "RiyaRiya");
        solo.enterText((EditText) solo.getView(R.id.login_password), "RiyaRiya");
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
        //solo.typeText(0, "KevinHP");
        solo.enterText(0, "House and garden cleaning");

        // click the list view in position 0
        solo.clickInList(0);

        // get a dialog and choose Yes
        solo.clickOnButton("Yes");
        solo.assertCurrentActivity("Wrong Activity", PlaceBidOnTaskActivity.class);

        // click view photo button;
        solo.clickOnButton("View Photo");
        solo.assertCurrentActivity("Wrong Activity", ShowPhotoActivity.class);

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

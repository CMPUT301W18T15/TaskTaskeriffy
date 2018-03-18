package com.example.heesoo.myapplication.IntentTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.heesoo.myapplication.ChooseMode.ChooseModeActivity;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.Requestor.RequesterMainActivity;
import com.example.heesoo.myapplication.Requestor.RequestorAddTaskActivity;
import com.example.heesoo.myapplication.Requestor.RequestorShowTaskDetailActivity;
import com.robotium.solo.Solo;

/**
 * Created by chengze on 2018/3/17.
 */

public class ViewTaskTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public ViewTaskTest(){
        super(com.example.heesoo.myapplication.Main_LogIn.MainActivity.class);
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

        // Login as user0000
        solo.enterText((EditText) solo.getView(R.id.login_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0000");
        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Logged In"));
        solo.clickOnButton("Would you like a task performed for you?");
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testViewTask(){
        // add sample task: user0000 task1
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);
        solo.clickOnButton("Add new task");
        solo.assertCurrentActivity("Wrong Activity", RequestorAddTaskActivity.class);
        solo.enterText((EditText) solo.getView(R.id.taskName), "user0000 task1");
        solo.clickOnButton("Save");
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        // click the list view in position 0
        // show the task details
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", RequestorShowTaskDetailActivity.class);
        assertTrue(solo.searchText("user0000 task1"));
        solo.goBack();
        // clear the garbage
        solo.clickInList(0);
        solo.clickOnButton("Delete Task");
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

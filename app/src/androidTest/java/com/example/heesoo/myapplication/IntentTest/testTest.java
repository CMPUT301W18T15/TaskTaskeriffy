package com.example.heesoo.myapplication.IntentTest;

/**
 * Created by chengze on 2018/4/2.
 */

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.example.heesoo.myapplication.task_provider_activities.FindNewTaskActivity;
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

public class testTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public testTest(){
        super(com.example.heesoo.myapplication.login_activity.MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void test1(){
        // Login as user0000 Requester
        solo.enterText((EditText) solo.getView(R.id.login_username), "user0000");
        solo.enterText((EditText) solo.getView(R.id.login_password), "user0000");
        solo.clickOnButton("Login");
        assertTrue(solo.searchText("Logged In"));

        // get the navigation bar
        DrawerLayout drawerLayout = (DrawerLayout) solo.getView(R.id.drawerLayout);

        // open the navigation bar
        drawerLayout.openDrawer(Gravity.LEFT);
        solo.clickOnMenuItem("Find New Tasks");
        solo.assertCurrentActivity("Wrong Activity", FindNewTaskActivity.class);

        // click the search button and enter search keyword
        Activity activity = solo.getCurrentActivity();
        int id = activity.getResources().getIdentifier("item_search", "id", solo.getCurrentActivity().getPackageName());
        View view = solo.getView(id);
        solo.clickOnView(view);
        //solo.typeText(0, "user0000");
        solo.enterText(0, "user0000");

        solo.clickInList(0);

//        //solo.clickOnActionBarItem(0);
//        solo.clickLongOnScreen();
//        solo.enterText(R.id.item_search, "user0000");

//        solo.enterText((EditText) solo.getEditText(R.id.text1), "user0000");
//        solo.enterText((SearchView) MenuItemCompat.getActionView(searchItem));

    }

}

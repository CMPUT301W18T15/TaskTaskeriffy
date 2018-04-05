package com.example.heesoo.myapplication.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.heesoo.myapplication.Entities.User;
import com.example.heesoo.myapplication.Requester.MainTaskActivity;
import com.example.heesoo.myapplication.Provider.ProviderFindNewTaskActivity;
import com.example.heesoo.myapplication.Provider.ProviderMainActivity;
import com.example.heesoo.myapplication.Provider.ProviderViewBiddedTaskList;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.Requester.RequesterAssignedTaskListActivity;
import com.example.heesoo.myapplication.Requester.RequesterBiddedTasksListActivity;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;

/*
This activity gives the user the option to edit their profile. The user can navigate to this activity through the
 "Edit Profile" button which is embedded in the ViewProfileActivity, when they are viewing their own profile.
 */

/**
 * Created by manuelakm on 2018-03-13.
 */

public class MyStatsActivity extends AppCompatActivity {

    private TextView totalEarnings;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private RatingBar ratingBar;

    private TextView myRating;
    private TextView completedPostedTasks;
    private TextView completedProvidedTasks;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Intent i = getIntent();

        ratingBar = findViewById(R.id.rating_bar);
        totalEarnings = findViewById(R.id.totalEarnings);
        myRating = findViewById(R.id.myRating);
        completedPostedTasks = findViewById(R.id.completedPostedTasks);
        completedProvidedTasks = findViewById(R.id.completedProvidedTasks);

        user = SetCurrentUser.getCurrentUser();
        ratingBar.setRating(Float.parseFloat(user.getRating().toString()));
        ratingBar.setFocusable(false);
        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        int posted_tasks = user.getCompletedPostedTasks();
        completedPostedTasks.setText(String.valueOf(posted_tasks));
        int provided_tasks = user.getCompletedProvidedTasks();
        completedProvidedTasks.setText(String.valueOf(provided_tasks));
        myRating.setText(user.getRating().toString());
        totalEarnings.setText(user.getTotalEarnings().toString());

        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        if ( menuItem.getItemId() == R.id.nav_myAccount ) {
                            startActivity(new Intent(getApplicationContext(), ViewProfileActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myStatistics ) {
                            startActivity(new Intent(getApplicationContext(), MyStatsActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myTasks ) {
                            startActivity(new Intent(getApplicationContext(), MainTaskActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myRequestedBiddedTasks ) {
                            startActivity(new Intent(getApplicationContext(), RequesterBiddedTasksListActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myRequestedAssignedTasks ) {
                            startActivity(new Intent(getApplicationContext(), RequesterAssignedTaskListActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_findNewTasks ) {
                            startActivity(new Intent(getApplicationContext(), ProviderFindNewTaskActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myAssignedTasks ) {
                            startActivity(new Intent(getApplicationContext(), ProviderMainActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myBiddedTasks ) {
                            startActivity(new Intent(getApplicationContext(), ProviderViewBiddedTaskList.class));
                        }
                        return true;
                    }
                });

    }

}
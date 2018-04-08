package com.example.heesoo.myapplication.profile_activities;

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

import com.example.heesoo.myapplication.entities.User;
import com.example.heesoo.myapplication.login_activity.MainActivity;
import com.example.heesoo.myapplication.task_provider_activities.FindNewTaskActivity;
import com.example.heesoo.myapplication.task_provider_activities.TaskProviderViewAssignedTasksActivity;
import com.example.heesoo.myapplication.task_provider_activities.TaskProviderViewBiddedTaskListActivity;
import com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewAssignedTasksActivity;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewBiddedTasksActivity;
import com.example.heesoo.myapplication.shared_preferences.SetPublicCurrentUser;

/*
This activity shows the user their statistics: rating, total earnings, total jobs done, total jobs
requested etc... This activity can be accessed from the navigation bar of the app and is therefore
called in all the main activities of our app (those that display lists of tasks).
 */

public class MyStatisticsActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_user_statistics);
        Intent i = getIntent();

        ratingBar = findViewById(R.id.rating_bar);
        totalEarnings = findViewById(R.id.totalEarnings);
        myRating = findViewById(R.id.myRating);
        completedPostedTasks = findViewById(R.id.completedPostedTasks);
        completedProvidedTasks = findViewById(R.id.completedProvidedTasks);

        user = SetPublicCurrentUser.getCurrentUser();
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

        navigationView = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();

                        if ( menuItem.getItemId() == R.id.nav_myAccount ) {
                            startActivity(new Intent(getApplicationContext(), ViewProfileActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myStatistics ) {
                            startActivity(new Intent(getApplicationContext(), MyStatisticsActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myTasks ) {
                            startActivity(new Intent(getApplicationContext(), ViewRequestedTasksActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myRequestedBiddedTasks ) {
                            startActivity(new Intent(getApplicationContext(), TaskRequesterViewBiddedTasksActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myRequestedAssignedTasks ) {
                            startActivity(new Intent(getApplicationContext(), TaskRequesterViewAssignedTasksActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_findNewTasks ) {
                            startActivity(new Intent(getApplicationContext(), FindNewTaskActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myAssignedTasks ) {
                            startActivity(new Intent(getApplicationContext(), TaskProviderViewAssignedTasksActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myBiddedTasks ) {
                            startActivity(new Intent(getApplicationContext(), TaskProviderViewBiddedTaskListActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.logout ) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        return true;
                    }
                });

    }

}
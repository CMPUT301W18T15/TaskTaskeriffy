package com.example.heesoo.myapplication.profile_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.heesoo.myapplication.entities.User;
import com.example.heesoo.myapplication.login_activity.MainActivity;
import com.example.heesoo.myapplication.task_provider_activities.FindNewTaskActivity;
import com.example.heesoo.myapplication.task_provider_activities.TaskProviderViewBiddedTaskListActivity;
import com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity;
import com.example.heesoo.myapplication.task_provider_activities.TaskProviderViewAssignedTasksActivity;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewAssignedTasksActivity;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewBiddedTasksActivity;
import com.example.heesoo.myapplication.shared_preferences.SetPublicCurrentUser;
import com.example.heesoo.myapplication.R;

import static com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity.checkNetwork;


/*
This activity gives the user an interface to view their profile which contains information about their account such as
 the username, the email address and the phone number. It contains button to navigate to the ChooseModeActivity
 where the user can switch between Task Provider or Task Requester mode. It also contains a button to navigate to the EditProfileActivity
 where the user may edit their own profile information. The User can reach this activity when clicking on any username to
 show (for eg. the bidder's details) their profile or clicking at the My Account button on the main dashboard in
 either the provider or the requester mode.
 */

public class ViewProfileActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private TextView usernameView;
    private TextView EmailAddressView;
    private TextView PhoneNumberView;
    private TextView RatingView;

    private Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);

        usernameView = findViewById(R.id.UsernameView);
        EmailAddressView = findViewById(R.id.EmailAddressView);
        PhoneNumberView = findViewById(R.id.phoneNumberView);
        RatingView = findViewById(R.id.rating);


        editButton = findViewById(R.id.editInfoButton);

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

    @Override
    protected void onStart() {

        checkNetwork(this);
        super.onStart();
        User user_received = (User) getIntent().getSerializableExtra("USER");
        User user;
        if (user_received == null) {
            user = SetPublicCurrentUser.getCurrentUser();
            editButton.setVisibility(View.VISIBLE);
        } else {
            user = user_received;
        }

        usernameView.setText(user.getUsername());
        EmailAddressView.setText(user.getEmailAddress());
        PhoneNumberView.setText(user.getPhoneNumber());
        RatingView.setText(user.getRating().toString());

        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);

                Intent edit_information = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(edit_information);
            }
        });

    }

}

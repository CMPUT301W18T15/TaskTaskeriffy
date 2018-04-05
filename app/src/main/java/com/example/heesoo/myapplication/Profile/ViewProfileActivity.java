package com.example.heesoo.myapplication.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.heesoo.myapplication.Entities.User;
import com.example.heesoo.myapplication.Requester.MainTaskActivity;
import com.example.heesoo.myapplication.Provider.ProviderFindNewTaskActivity;
import com.example.heesoo.myapplication.Provider.ProviderMainActivity;
import com.example.heesoo.myapplication.Provider.ProviderViewBiddedTaskList;
import com.example.heesoo.myapplication.Requester.RequesterAssignedTaskListActivity;
import com.example.heesoo.myapplication.Requester.RequesterBiddedTasksListActivity;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;
import com.example.heesoo.myapplication.R;

import static com.example.heesoo.myapplication.Requester.RequesterMainActivity.checkNetwork;

/**
 * Created by manuelakm on 2018-03-13.
 */

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

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        usernameView = findViewById(R.id.UsernameView);
        EmailAddressView = findViewById(R.id.EmailAddressView);
        PhoneNumberView = findViewById(R.id.phoneNumberView);
        RatingView = findViewById(R.id.rating);


        editButton = findViewById(R.id.editInfoButton);

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
    @Override
    protected void onStart() {
        // offline check
        checkNetwork(this);
        super.onStart();
        User user_recieved = (User) getIntent().getSerializableExtra("USER");
        User user;
        if (user_recieved == null) {
            user = SetCurrentUser.getCurrentUser();
            editButton.setVisibility(View.VISIBLE);
        } else {
            user = user_recieved;
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
                //finish();
            }
        });

//        changeModeButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                setResult(RESULT_OK);
//
//                Intent change_mode = new Intent(getApplicationContext(), ChooseModeActivity.class);
//                startActivity(change_mode);
//            }
//        });
    }

}

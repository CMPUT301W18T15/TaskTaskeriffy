package com.example.heesoo.myapplication.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heesoo.myapplication.Constraints.UserConstraints;
import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchUserController;
import com.example.heesoo.myapplication.Entities.User;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;

import static com.example.heesoo.myapplication.Requester.RequesterMainActivity.checkNetwork;

/*
This activity gives the user the option to edit their profile. The user can navigate to this activity through the
 "Edit Profile" button which is embedded in the ViewProfileActivity, when they are viewing their own profile.
 */

/**
 * Created by manuelakm on 2018-03-13.
 */

public class MyStatsActivity extends AppCompatActivity {

    private TextView totalEarnings;
    private TextView myRating;
    private TextView completedPostedTasks;
    private TextView completedProvidedTasks;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Intent i = getIntent();
        totalEarnings = findViewById(R.id.totalEarnings);
        myRating = findViewById(R.id.myRating);
        completedPostedTasks = findViewById(R.id.completedPostedTasks);
        completedProvidedTasks = findViewById(R.id.completedProvidedTasks);

        user = SetCurrentUser.getCurrentUser();
        int posted_tasks = user.getCompletedPostedTasks();
        completedPostedTasks.setText(String.valueOf(posted_tasks));
        int provided_tasks = user.getCompletedProvidedTasks();
        completedProvidedTasks.setText(String.valueOf(provided_tasks));
        myRating.setText(user.getRating().toString());
        totalEarnings.setText(user.getTotalEarnings().toString());

    }

}
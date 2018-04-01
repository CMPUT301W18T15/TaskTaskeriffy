package com.example.heesoo.myapplication.Provider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchUserController;
import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.Entities.User;
import com.example.heesoo.myapplication.Profile.ViewProfileActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.Requester.ShowPhotoActivity;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;

import java.util.ArrayList;

/*
This activity is navigated to when the provider clicks on any task that was assigned to them in the
list view of the ProvideMainActivity.  It shows them the details of the task as well as the accepted bid.
 */

public class ProviderViewAssignedTaskDetail extends AppCompatActivity {
    private Button seePhotoButton;
    private Task task;
    private String requester_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_view_assigned_task_detail);
        final Task task = (Task) getIntent().getSerializableExtra("task");

        TextView taskName = findViewById(R.id.taskName);
        taskName.setText(task.getTaskName());
        TextView taskDescription = findViewById(R.id.taskDescription);
        taskDescription.setText(task.getTaskDescription());
        TextView taskStatus = findViewById(R.id.taskStatus);
        taskStatus.setText(task.getStatus());
        TextView bidStatus = findViewById(R.id.myBidStatus);
        TextView taskLowestBid = findViewById(R.id.taskLowestBid);
        TextView myBidPrice = findViewById(R.id.myBidPrice);

        TextView requesterUsername = findViewById(R.id.requester_username);
        requesterUsername.setText(task.getUserName());

        requester_string = requesterUsername.getText().toString();

        // TODO assume every bidder can only make 1 bid for each task.
        // TODO: ^^ Incorrect Assumption.
        for (Bid bids:task.getBids()){
            if (bids.getTaskProvider().equals(SetCurrentUser.getCurrentUser().getUsername())){
                myBidPrice.setText(bids.getBidPrice().toString());
                bidStatus.setText(bids.getStatus());
                taskLowestBid.setText(task.getLowestBid());
            }
        }

        seePhotoButton = findViewById(R.id.seePicture);
        seePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowPhotoActivity.class);
                ArrayList<String> encodedPhoto = task.getPictures();
                intent.putExtra("photo", encodedPhoto);
                startActivity(intent);
            }
        });
    }

    public void clickHandler(View view){
        ElasticSearchUserController.GetUserTask getUser = new ElasticSearchUserController.GetUserTask();
        getUser.execute(requester_string);
        User user = new User();
        try {
            user = getUser.get();
        } catch (Exception e) {
            //Log.d
        }
        Intent intent = new Intent(ProviderViewAssignedTaskDetail.this, ViewProfileActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

}

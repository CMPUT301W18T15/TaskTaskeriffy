package com.example.heesoo.myapplication.task_provider_activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchBidController;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.entities.Bid;
import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.login_activity.MainActivity;
import com.example.heesoo.myapplication.task_requester_activities.TaskMapActivity;
import com.example.heesoo.myapplication.general_activities.ShowPhotoActivity;
import com.example.heesoo.myapplication.shared_preferences.SetPublicCurrentUser;
import com.example.heesoo.myapplication.R;

import java.util.ArrayList;

/*
This activity is navigated to when the provider wants to place a bid on a task. It is reached from the FindNewTaskActivity.
This activity provides an interface for the provider to see the details of a task and it's lowest bid and where
the provider can place his own bid.
 */

public class PlaceBidOnTaskActivity extends AppCompatActivity {
    private TextView titleView;
    private TextView descriptionView;
    private TextView statusView;
    private TextView lowestBidView;
    private EditText placeBidView;
    private Button placeBidButton;
    private Button viewMap;
    private String newBidPrice;
    private Task task;
    private Button seePhotoButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_bid_on_task);

        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("TaskToBidOn");

        titleView = findViewById(R.id.viewTitle);
        titleView.setText(task.getTaskName());
        descriptionView = findViewById(R.id.viewDescription);
        descriptionView.setText(task.getTaskDescription());
        statusView = findViewById(R.id.viewStatus);
        statusView.setText(task.getStatus());
        lowestBidView = findViewById(R.id.viewLowestBid);
        lowestBidView.setText(task.getLowestBid());
        placeBidView = findViewById(R.id.placeBid);
        placeBidButton = findViewById(R.id.placeBidButton);

        placeBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                newBidPrice = placeBidView.getText().toString();
                float bidPrice;
                Bid newBid, earlierBid;

                Boolean editing = false;
                Task currentTask;

                ElasticSearchTaskController.GetTask mayEdit = new ElasticSearchTaskController.GetTask();
                mayEdit.execute(task.getId());
                try {
                    currentTask = mayEdit.get();
                    editing = currentTask.getEditStatus();
                }
                catch (Exception e) {
                    Log.i("Error", "Did not find the task!");
                }

                if (newBidPrice.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Fill the Bid Price", Toast.LENGTH_SHORT).show();
                }

                else if(editing){
                    Toast.makeText(getApplicationContext(), "The Requester is editing this task, please try to place bid later!", Toast.LENGTH_SHORT).show();
                }

                else{
                    bidPrice = Float.parseFloat(newBidPrice);
                    earlierBid = task.getBids().bidByUsername(SetPublicCurrentUser.getCurrentUser().getUsername());
                    if(earlierBid!=null){
                        task.deleteBid(earlierBid);
                        newBid = earlierBid;
                        newBid.setBidPrice(bidPrice);
                        ElasticSearchBidController.EditBidTask editBid = new ElasticSearchBidController.EditBidTask();
                        editBid.execute(newBid);
                    }else {
                        newBid = new Bid(task.getTaskName(), task.getTaskDescription(), bidPrice, SetPublicCurrentUser.getCurrentUser().getUsername(), task.getTaskRequester());
                        ElasticSearchBidController.AddBidsTask addBidsTask = new ElasticSearchBidController.AddBidsTask();
                        addBidsTask.execute(newBid);
                    }

                    task.addBid(newBid);

                    // offline behavior
                    for (int i = 0; i < MainActivity.user.getRequesterTasks().getSize(); i++) {
                        Task changedTask = MainActivity.user.getRequesterTasks().getTask(i);

                        if (changedTask.getId().equals(task.getId())){
                            changedTask.addBid(newBid);
                        }
                    }
                    Intent new_bid = new Intent(getApplicationContext(), FindNewTaskActivity.class);
                    new_bid.putExtra("bidPlaced", newBid);
                    setResult(Activity.RESULT_OK, new_bid);
                    Toast.makeText(getApplicationContext(), "Bid Placed", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1500);
                }
            }
        });

        if (!task.getLatitude().equals(-1.0) && !task.getLongitude().equals(-1.0)) {
            viewMap = findViewById(R.id.viewMap);
            viewMap.setVisibility(View.VISIBLE);
            viewMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), TaskMapActivity.class);
                    intent.putExtra("Task", task);
                    intent.putExtra("Mode", "ViewMarker");
                    startActivity(intent);
                }
            });

        }

        seePhotoButton = findViewById(R.id.viewPhoto);
        seePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowPhotoActivity.class);
                ArrayList<String> encodedPhotos = task.getPictures();
                intent.putExtra("photos", encodedPhotos);
                startActivity(intent);
            }
        });
    }
}

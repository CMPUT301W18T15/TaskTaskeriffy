package com.example.heesoo.myapplication.Provider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchBidController;
import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.MapsActivity;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;
import com.example.heesoo.myapplication.R;


/**
 * Created by manuelakm on 2018-03-15.
 */

/*
This activity is navigated to when the provider wants to place a bid on a task. It is reached from the ProviderFindNewTaskActivity.
This activity provides an interface for the provider to see the details of a task and it's lowest bid and where
the provider can place his own bid.
 */

public class ProviderPlaceBidActivity extends AppCompatActivity {
    private TextView titleView;
    private TextView descriptionView;
    private TextView statusView;
    private TextView lowestBidView;
    private EditText placeBidView;
    private Button placeBidButton;
    private Button viewMap;
    private String newBidPrice;
    private Task task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_on_task);

        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("TaskToBidOn");

        titleView = findViewById(R.id.task_view_Title_content);
        titleView.setText(task.getTaskName());
        descriptionView = findViewById(R.id.task_view_Description_content);
        descriptionView.setText(task.getTaskDescription());
        statusView = findViewById(R.id.task_view_Status_content);
        statusView.setText(task.getStatus());
        lowestBidView = findViewById(R.id.task_view_lowest_bid_content);
        lowestBidView.setText(task.getLowestBid());
        placeBidView = findViewById(R.id.placeBid);
        placeBidButton = findViewById(R.id.place_bid_button);

        placeBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                newBidPrice = placeBidView.getText().toString();
                float bidPrice;

                if (newBidPrice.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Fill the Bid Price", Toast.LENGTH_SHORT).show();
                } else {
                    bidPrice = Float.parseFloat(newBidPrice);
                    Bid newBid = new Bid(task.getTaskName(), task.getTaskDescription(), bidPrice, SetCurrentUser.getCurrentUser().getUsername(), task.getUserName());
                    ElasticSearchBidController.AddBidsTask addBidsTask = new ElasticSearchBidController.AddBidsTask();
                    addBidsTask.execute(newBid);
                    task.addBid(newBid);
                    // offline behavior
                    for (Task changedTask : MainActivity.user.getRequesterTasks()){
                        if (changedTask.getId().equals(task.getId())){
                            changedTask.addBid(newBid);
                        }
                    }
                    Intent new_bid = new Intent(getApplicationContext(), ProviderFindNewTaskActivity.class);
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
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("Task", task);
                    intent.putExtra("Mode", "ViewMarker");
                    startActivity(intent);
                }
            });

        }
    }
}

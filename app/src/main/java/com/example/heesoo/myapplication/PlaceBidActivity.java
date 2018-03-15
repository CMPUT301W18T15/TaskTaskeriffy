package com.example.heesoo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by manuelakm on 2018-03-15.
 */

public class PlaceBidActivity extends AppCompatActivity {
    private TextView titleView;
    private TextView descriptionView;
    private TextView statusView;
    private TextView lowestBidView;
    private EditText placeBidView;
    private Button placeBidButton;
    private String newBidPrice;
    private Task task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_on_task);

        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("TaskToBidOn");

        titleView = (TextView) findViewById(R.id.task_view_Title_content);
        titleView.setText(task.getTaskName());
        descriptionView = (TextView) findViewById(R.id.task_view_Description_content);
        descriptionView.setText(task.getTaskDescription());
        statusView = (TextView) findViewById(R.id.task_view_Status_content);
        statusView.setText(task.getStatus());
        lowestBidView = (TextView) findViewById(R.id.task_view_lowest_bid_content);
        lowestBidView.setText(task.getLowestBid().getBidPrice().toString());
        placeBidView = (EditText) findViewById(R.id.placeBid);
        placeBidButton = (Button) findViewById(R.id.place_bid_button);

        placeBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                newBidPrice = placeBidView.getText().toString();
                float bidPrice = Float.parseFloat(newBidPrice);
                Bid newBid = new Bid(task.getTaskName(), task.getTaskDescription(), task.getStatus(), bidPrice, MyApplication.getCurrentUser().getUsername());
                ElasticSearchBidController.AddBidsTask addBidsTask = new ElasticSearchBidController.AddBidsTask();
                addBidsTask.execute(newBid);
                task.addBid(newBid);
                Intent new_bid = new Intent(getApplicationContext(), FindNewTaskActivity.class);
                new_bid.putExtra("bidPlaced", newBid);
                setResult(Activity.RESULT_OK, new_bid);
                finish();
            }
        });
    }


        @Override
        protected void onStart() {
            super.onStart();
        }
}

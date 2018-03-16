package com.example.heesoo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.heesoo.myapplication.ElasticSearchControllers.*;
import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.Entities.User;

import java.util.ArrayList;


public class BiddedTaskDetailActivity extends AppCompatActivity {
    private Button editTask;
    private Button deleteTask;
    private Button acceptBid;
    private Button declineBid;
    private Button viewBidderProfile;
    private Task task;
    private Bid bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidded_task_detail);


        task = (Task) getIntent().getSerializableExtra("task");
        bid = (Bid) getIntent().getSerializableExtra("bid");

        TextView bidderName = findViewById(R.id.bidderName);
        bidderName.setText(bid.getTaskProvider());

        TextView bidAmount = findViewById(R.id.bidAmount);
        bidAmount.setText(bid.getBidPrice().toString());

        TextView taskName = findViewById(R.id.taskName);
        taskName.setText(bid.getTaskName());

        TextView taskStatus = findViewById(R.id.taskStatus);
        taskStatus.setText(bid.getStatus());

        editTask = findViewById(R.id.editTask);
        editTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditTaskActivity.class);
                intent.putExtra("task", task);
                finish();
            }
        });

        deleteTask = (Button) findViewById(R.id.deleteTask);
        deleteTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO delete the task by elastic search
                ElasticSearchTaskController.DeleteTasksTask deleteTask = new ElasticSearchTaskController.DeleteTasksTask();
                deleteTask.execute(task);

                ArrayList<Bid> allBids = task.getBids();
                for (int i = 0; i < allBids.size(); i++ ) {
                    ElasticSearchBidController.DeleteBidTask deleteBidTask = new ElasticSearchBidController.DeleteBidTask();
                    deleteBidTask.execute(allBids.get(i));
                }

            }
        });

        acceptBid = findViewById(R.id.acceptBid);
        acceptBid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO change the status of the task
                task.acceptBid(bid.getTaskProvider());
                bid.setStatus("Accepted");
                ElasticSearchTaskController.EditTasksTask editTask = new ElasticSearchTaskController.EditTasksTask();
                editTask.execute(task);
                ElasticSearchBidController.EditBidTask editBid = new ElasticSearchBidController.EditBidTask();
                editBid.execute(bid);

            }
        });

        declineBid = findViewById(R.id.declineBid);
        declineBid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO delete this bid by elastic search
                bid.setStatus("Declined");
                ElasticSearchBidController.EditBidTask editBid = new ElasticSearchBidController.EditBidTask();
                editBid.execute(bid);
            }
        });

        viewBidderProfile = findViewById(R.id.viewBidderProfile);
        viewBidderProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO find user in elasticsearch user table by userame.equals(bid.getTaskProvider())
                ElasticSearchUserController.GetUserTask getUser = new ElasticSearchUserController.GetUserTask();
                getUser.execute(bid.getTaskProvider());
                User user = new User;
                try {
                    user = getUser.get();
                }
                catch (Exception e){
                    //Log.d
                }
                Intent intent = new Intent(BiddedTaskDetailActivity.this, ViewProfileActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
            }
        });

    }
}
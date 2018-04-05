package com.example.heesoo.myapplication.Requester;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heesoo.myapplication.ElasticSearchControllers.*;
import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.Entities.User;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.Profile.ViewProfileActivity;

import java.util.ArrayList;

import static com.example.heesoo.myapplication.Requester.RequesterMainActivity.checkNetwork;

/*
This activity is navigated to when the requester clicks on any bid in the list of bids on a particular task.
This activity is reached by clicking on a particular list item from the RequesterViewBidsOnTask.
It shows them the details of the bid as well as gives options to accept the bid, decline the bid,
delete the task or view the bidder's profile.
 */

public class RequesterBidDetailActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_bid_detail);


        task = (Task) getIntent().getSerializableExtra("task");
        bid = (Bid) getIntent().getSerializableExtra("bid");

        TextView bidderName = findViewById(R.id.bidderName);
        bidderName.setText(bid.getTaskProvider());

        TextView bidAmount = findViewById(R.id.bidAmount);
        bidAmount.setText(bid.getBidPrice().toString());

        TextView taskName = findViewById(R.id.taskName);
        taskName.setText(bid.getTaskName());

        TextView bidStatus = findViewById(R.id.bidStatus);
        bidStatus.setText(bid.getStatus());


    }
    @Override
    protected void onStart() {
        super.onStart();
        checkNetwork(this);
        deleteTask = (Button) findViewById(R.id.deleteTask);
        deleteTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ElasticSearchTaskController.DeleteTask deleteTask = new ElasticSearchTaskController.DeleteTask();
                deleteTask.execute(task);

                ArrayList<Bid> allBids = task.getBids();
                for (int i = 0; i < allBids.size(); i++) {
                    ElasticSearchBidController.DeleteBidTask deleteBidTask = new ElasticSearchBidController.DeleteBidTask();
                    deleteBidTask.execute(allBids.get(i));
                }
                Toast.makeText(RequesterBidDetailActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                Intent deleteTaskIntent = new Intent(getApplicationContext(), RequesterMainActivity.class);
                deleteTaskIntent.putExtra("TaskDeleted", task);
                setResult(Activity.RESULT_OK, deleteTaskIntent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);

            }
        });


        acceptBid = findViewById(R.id.acceptBid);
        acceptBid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<Bid> allBids = task.getBids();
                for (Bid task_bid : allBids) {
                    ElasticSearchBidController.DeleteBidTask deleteBidTask = new ElasticSearchBidController.DeleteBidTask();
                    deleteBidTask.execute(task_bid);
                }
                task.deleteAllBids();
                bid.setStatus("Accepted");
                ElasticSearchBidController.AddBidsTask addBid = new ElasticSearchBidController.AddBidsTask();
                addBid.execute(bid);
                task.addBid(bid);
                task.acceptBid(bid.getTaskProvider());
                ElasticSearchTaskController.EditTask editTask = new ElasticSearchTaskController.EditTask();
                editTask.execute(task);
                Toast.makeText(getApplicationContext(),"Bid Accepted", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);

            }
        });

        declineBid = findViewById(R.id.declineBid);
        declineBid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                task.deleteBid(bid);
                bid.setStatus("Declined");
                ElasticSearchBidController.EditBidTask editBid = new ElasticSearchBidController.EditBidTask();
                editBid.execute(bid);
                task.addBid(bid);
                ElasticSearchTaskController.EditTask editTask = new ElasticSearchTaskController.EditTask();
                editTask.execute(task);
                Toast.makeText(getApplicationContext(),"Task Declined", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
            }
        });

        viewBidderProfile = findViewById(R.id.viewBidderProfile);
        viewBidderProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ElasticSearchUserController.GetUserTask getUser = new ElasticSearchUserController.GetUserTask();
                getUser.execute(bid.getTaskProvider());
                User user = new User();
                try {
                    user = getUser.get();
                } catch (Exception e) {
                    Log.i("Error", "The request for user failed in onStart");

                }
                Intent intent = new Intent(RequesterBidDetailActivity.this, ViewProfileActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
            }
        });

    }

    public void bidderNameClicked(View view) {

        //alter text of textview widget
        ElasticSearchUserController.GetUserTask getUser = new ElasticSearchUserController.GetUserTask();
        getUser.execute(bid.getTaskProvider());
        User user = new User();
        try {
            user = getUser.get();
        } catch (Exception e) {
            Log.i("Error", "The request for user failed in bidderNameClicked");
        }
        Intent intent = new Intent(RequesterBidDetailActivity.this, ViewProfileActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

}
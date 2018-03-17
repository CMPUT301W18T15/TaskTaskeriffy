package com.example.heesoo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchBidController;
import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.Entities.Task;


import java.util.ArrayList;

public class RequestorShowTaskDetailActivity extends AppCompatActivity {

    private Button editTask;
    private Button deleteTask;
    private Button viewBidsButton;
    private TextView taskName;
    private TextView taskDescription;
    private TextView taskStatus;
    private TextView taskLowestBid;
    private TextView textView;
    private TextView taskProvider;

    private ElasticSearchTaskController elasticSearchTaskController;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestor_show_task_detail);

        elasticSearchTaskController = new ElasticSearchTaskController();


        String lowestBid;
        task = (Task) getIntent().getSerializableExtra("task");

        taskName = findViewById(R.id.taskName);
        taskName.setText(task.getTaskName());
        taskDescription = findViewById(R.id.taskDescription);
        taskDescription.setText(task.getTaskDescription());
        taskStatus = findViewById(R.id.taskStatus);
        taskStatus.setText(task.getStatus());
        taskLowestBid = findViewById(R.id.taskLowestBid);
        taskLowestBid.setText(task.getLowestBid());
        viewBidsButton = findViewById(R.id.view_bids);
        textView = findViewById(R.id.textView);
        taskProvider = findViewById(R.id.taskProvider);


        editTask = findViewById(R.id.editTask);
        editTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RequestorShowTaskDetailActivity.this, RequestorEditTaskActivity.class);
                intent.putExtra("TaskToEdit", task);
                startActivity(intent);
            }
        });

        deleteTask = findViewById(R.id.deleteTask);
        deleteTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ElasticSearchTaskController.DeleteTask deleteTask = new ElasticSearchTaskController.DeleteTask();
                deleteTask.execute(task);

                ArrayList<Bid> allBids = task.getBids();
                for (int i = 0; i < allBids.size(); i++ ) {
                    ElasticSearchBidController.DeleteBidTask deleteBidTask = new ElasticSearchBidController.DeleteBidTask();
                    deleteBidTask.execute(allBids.get(i));
                }

                Toast.makeText(RequestorShowTaskDetailActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        if (task.getStatus().equals("Bidded")){
            viewBidsButton.setVisibility(View.VISIBLE);
            viewBidsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), RequestorViewBidsOnTaskActivity.class);
                    intent.putExtra("task", task);
                    startActivity(intent);
                }
            });
        }
        if (task.getStatus().equals("Assigned")){
            textView.setVisibility(View.VISIBLE);
            taskProvider.setVisibility(View.VISIBLE);

            taskProvider.setText(task.getTaskProvider());

        }


    }
/*
    public String findLowestBid(ArrayList<Bid> bids){
        if (bids.isEmpty()){
            return "NA";
        }
        else{
            Float maxValue = bids.get(0).getBidPrice();
            for (Bid bid:bids){
                if (bid.getBidPrice() < maxValue){
                    maxValue = bid.getBidPrice();
                }
            }
            return maxValue.toString();
        }
    }
*/

}
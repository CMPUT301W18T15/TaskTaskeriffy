package com.example.heesoo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class RequestorShowTaskDetailActivity extends AppCompatActivity {

    private Button editTask;
    private Button deleteTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestor_show_task_detail);

        String lowestBid;
        final Task task = (Task) getIntent().getSerializableExtra("task");

        TextView taskName = (TextView)findViewById(R.id.taskName);
        taskName.setText(task.getTaskName());
        TextView taskDescription = (TextView)findViewById(R.id.taskDescription);
        taskDescription.setText(task.getTaskDescription());
        TextView taskStatus = (TextView)findViewById(R.id.taskStatus);
        taskStatus.setText(task.getStatus());
        TextView taskLowestBid = (TextView)findViewById(R.id.taskLowestBid);
        taskLowestBid.setText(findLowestBid(task.getBids()));

        editTask = (Button) findViewById(R.id.editTask);
        editTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*
                Intent intent = new Intent(getApplicationContext(), AddEditTaskActivity.class);
                intent.putExtra("task", task);
                finish();
                */
            }
        });

        deleteTask = (Button) findViewById(R.id.deleteTask);
        deleteTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO delete the task by elastic search
            }
        });


    }

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


}
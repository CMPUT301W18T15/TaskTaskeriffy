package com.example.heesoo.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.Entities.Task;


import java.util.ArrayList;

public class ProviderViewAssignedTaskDetail extends AppCompatActivity {
    private Button finishTask;

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
        TextView taskLowestBid = findViewById(R.id.taskLowestBid);

        taskLowestBid.setText(task.getLowestBid());

        //TODO: provider should not be able to change status of task.
//        finishTask = findViewById(R.id.finishTask);
//        finishTask.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                task.setStatus("Finished");
//            }
//        });
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

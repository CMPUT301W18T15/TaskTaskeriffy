package com.example.heesoo.myapplication.Provider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;


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
        TextView bidStatus = findViewById(R.id.myBidStatus);
        TextView taskLowestBid = findViewById(R.id.taskLowestBid);
        TextView myBidPrice = findViewById(R.id.myBidPrice);

        // TODO assume every bidder can only make 1 bid for each task.
        // TODO: ^^ Incorrect Assumption.
        for (Bid bids:task.getBids()){
            if (bids.getTaskProvider().equals(SetCurrentUser.getCurrentUser().getUsername())){
                myBidPrice.setText(bids.getBidPrice().toString());
                bidStatus.setText(bids.getStatus());
            }
        }

        //TODO: Again, provider should not be able to change status of task or "finish" a task.

//        Button finishTask = findViewById(R.id.finishTask);
//
//        taskLowestBid.setText(task.getLowestBid());
//        if (task.getStatus().equals("Assigned")){
//            finishTask.setVisibility(View.VISIBLE);
//        }
        //TODO: provider should not be able to change status of task.
//        finishTask = findViewById(R.id.finishTask);
//        finishTask.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                task.setStatus("Finished");
//            }
//        });
    }
}

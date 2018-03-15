package com.example.heesoo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BiddedTaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidded_task_detail);

        final Task task = (Task) getIntent().getSerializableExtra("task");
        final Bid bid = (Bid) getIntent().getSerializableExtra("bid");
        final TaskProvider taskProvider= (TaskProvider) getIntent().getSerializableExtra("TaskProvider");

        // TODO how to get bidder name?
        TextView bidAmount = (TextView)findViewById(R.id.bidAmount);
        bidAmount.setText(bid.getBidPrice().toString());

        TextView taskName = (TextView)findViewById(R.id.taskName);
        taskName.setText(bid.getTaskName());

        TextView taskStatus = (TextView)findViewById(R.id.taskStatus);
        taskStatus.setText(bid.getStatus());

        Button editTask = (Button) findViewById(R.id.editTask);
        editTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*
                Intent intent = new Intent(getApplicationContext(), AddEditTaskActivity.class);
                intent.putExtra("task", task);
                finish();
                */
            }
        });

        Button deleteTask = (Button) findViewById(R.id.deleteTask);
        deleteTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO delete the task by elastic search
            }
        });

        Button acceptBid = (Button) findViewById(R.id.acceptBid);
        acceptBid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO accept the bid and change the status of the task
                task.acceptBid(taskProvider.getUsername());
            }
        });

        Button declineBid = (Button) findViewById(R.id.declineBid);
        declineBid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO delete this bid by elastic search
                task.deleteBid(bid, taskProvider);
            }
        });
    }
}

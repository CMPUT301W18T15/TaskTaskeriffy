package com.example.heesoo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

<<<<<<< HEAD
import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.Entities.Task;
=======
import com.example.heesoo.modelclasses.Bid;
import com.example.heesoo.modelclasses.Task;
>>>>>>> 44a22492f696b3ec835da725f9daf182c819c159

public class BiddedTaskDetailActivity extends AppCompatActivity {
    private Button editTask;
    private Button deleteTask;
    private Button acceptBid;
    private Button declineBid;
    private Button viewBidderProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidded_task_detail);

        final Task task = (Task) getIntent().getSerializableExtra("task");
        final Bid bid = (Bid) getIntent().getSerializableExtra("bid");
        //final TaskProvider taskProvider= (TaskProvider) getIntent().getSerializableExtra("TaskProvider");

        TextView bidderName = findViewById(R.id.bidderName);
        bidderName.setText(bid.getTaskProvider());

        TextView bidAmount = findViewById(R.id.bidAmount);
        bidAmount.setText(bid.getBidPrice().toString());

        TextView taskName = findViewById(R.id.taskName);
        taskName.setText(bid.getTaskName());

        TextView taskStatus = findViewById(R.id.taskStatus);
        taskStatus.setText(bid.getStatus());

//        editTask = (Button) findViewById(R.id.editTask);
//        editTask.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), AddEditTaskActivity.class);
//                intent.putExtra("task", task);
//                finish();
//            }
//        });

//        deleteTask = (Button) findViewById(R.id.deleteTask);
//        deleteTask.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // TODO delete the task by elastic search
//            }
//        });

//        acceptBid = (Button) findViewById(R.id.acceptBid);
//        acceptBid.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // TODO change the status of the task
//                task.acceptBid(bid.getTaskProvider());
//            }
//        });

//        declineBid = (Button) findViewById(R.id.declineBid);
//        declineBid.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // TODO delete this bid by elastic search
//                task.deleteBid(bid);
//            }
//        });

        viewBidderProfile = findViewById(R.id.viewBidderProfile);
        viewBidderProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO find user in elasticsearch user table by userame.equals(bid.getTaskProvider())
                Intent intent = new Intent(BiddedTaskDetailActivity.this, ViewProfileActivity.class);
//                intent.putExtra("USER", user);
//                startActivity(intent);
            }
        });

    }
}

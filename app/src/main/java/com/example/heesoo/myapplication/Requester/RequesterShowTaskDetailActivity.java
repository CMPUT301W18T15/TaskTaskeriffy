package com.example.heesoo.myapplication.Requester;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
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
import com.example.heesoo.myapplication.R;


import java.util.ArrayList;

public class RequesterShowTaskDetailActivity extends AppCompatActivity {

    private Button editTask;
    private Button deleteTask;
    private Button viewBidsButton;
    private Button markDone;
    private Button markRequested;

    private TextView taskName;
    private TextView taskDescription;
    private TextView taskStatus;
    private TextView taskLowestBid;
    private TextView textView;
    private TextView taskProvider;
    private TextView bidTextView;



    private ElasticSearchTaskController elasticSearchTaskController;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_show_task_detail);

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
        bidTextView = findViewById(R.id.bidTextView);
        taskProvider = findViewById(R.id.taskProvider);

        if (task.getStatus().equals("Requested")) {
            editTask = findViewById(R.id.editTask);
            editTask.setVisibility(View.VISIBLE);
            editTask.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(RequesterShowTaskDetailActivity.this, RequesterEditTaskActivity.class);
                    intent.putExtra("TaskToEdit", task);
                    startActivityForResult(intent, 1);
                }
            });
        }


        deleteTask = findViewById(R.id.deleteTask);
        deleteTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ElasticSearchTaskController.DeleteTask deleteTask = new ElasticSearchTaskController.DeleteTask();
                deleteTask.execute(task);

                ArrayList<Bid> allBids = task.getBids();
                for (int i = 0; i < allBids.size(); i++) {
                    ElasticSearchBidController.DeleteBidTask deleteBidTask = new ElasticSearchBidController.DeleteBidTask();
                    deleteBidTask.execute(allBids.get(i));
                }

                Toast.makeText(RequesterShowTaskDetailActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
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

        if (task.getStatus().equals("Bidded")) {
            viewBidsButton.setVisibility(View.VISIBLE);
            viewBidsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), RequesterViewBidsOnTaskActivity.class);
                    intent.putExtra("task", task);
                    startActivity(intent);
                }
            });
        }
        if (task.getStatus().equals("Assigned")) {
            textView.setVisibility(View.VISIBLE);
            taskProvider.setVisibility(View.VISIBLE);
            taskProvider.setText(task.getTaskProvider());
            bidTextView.setText("Accepted Bid");

            markDone = findViewById(R.id.mark_done_button);
            markDone.setVisibility(View.VISIBLE);
            markDone.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    task.setStatus("Done");
                    ElasticSearchTaskController.EditTask editTask = new ElasticSearchTaskController.EditTask();
                    editTask.execute(task);
                    Toast.makeText(RequesterShowTaskDetailActivity.this, "Task Marked as Done", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);
                }
            });

            markRequested = findViewById(R.id.mark_requested_button);
            markRequested.setVisibility(View.VISIBLE);
            markRequested.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    task.setStatus("Requested");
                    task.setTaskProvider("");
                    //TODO delete the particlar bid from the task and database

                    ElasticSearchTaskController.EditTask editTask = new ElasticSearchTaskController.EditTask();
                    editTask.execute(task);
                    Toast.makeText(RequesterShowTaskDetailActivity.this, "Task Marked as Requested", Toast.LENGTH_SHORT).show();
                    //startActivity(intent);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);

                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if (i == null) {

        }
        else if (requestCode == 1) {
            Task task = (Task) i.getSerializableExtra("TaskEdited");
            taskName.setText(task.getTaskName());
            taskDescription.setText(task.getTaskDescription());
            taskStatus.setText(task.getStatus());
            taskLowestBid.setText(task.getLowestBid());
        }
    }

}
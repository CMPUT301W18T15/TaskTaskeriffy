package com.example.heesoo.myapplication.Requestor;

import android.app.Activity;
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
import com.example.heesoo.myapplication.R;


import java.util.ArrayList;

public class RequestorShowTaskDetailActivity extends AppCompatActivity {

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
                startActivityForResult(intent, 1);
            }
        });

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

                Toast.makeText(RequestorShowTaskDetailActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                Intent deleteTaskIntent = new Intent(getApplicationContext(), RequesterMainActivity.class);
                deleteTaskIntent.putExtra("TaskDeleted", task);
                setResult(Activity.RESULT_OK, deleteTaskIntent);
                finish();
            }
        });

        if (task.getStatus().equals("Bidded")) {
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
        if (task.getStatus().equals("Assigned")) {
            textView.setVisibility(View.VISIBLE);
            taskProvider.setVisibility(View.VISIBLE);
            taskProvider.setText(task.getTaskProvider());
            markDone = findViewById(R.id.mark_done_button);
            markDone.setVisibility(View.VISIBLE);
            markDone.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    task.setStatus("Done");
                    ElasticSearchTaskController.EditTask editTask = new ElasticSearchTaskController.EditTask();
                    editTask.execute(task);
                    Toast.makeText(RequestorShowTaskDetailActivity.this, "Task Marked as Done", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(RequestorShowTaskDetailActivity.this, "Task Marked as Requested", Toast.LENGTH_SHORT).show();

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
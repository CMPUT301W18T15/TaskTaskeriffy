package com.example.heesoo.myapplication.task_requester_activities;

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

import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchBidController;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchUserController;
import com.example.heesoo.myapplication.entities.BidList;
import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.entities.User;
import com.example.heesoo.myapplication.general_activities.ShowPhotoActivity;
import com.example.heesoo.myapplication.login_activity.MainActivity;
import com.example.heesoo.myapplication.shared_preferences.SetPublicCurrentUser;
import com.example.heesoo.myapplication.profile_activities.ViewProfileActivity;
import com.example.heesoo.myapplication.R;


import java.util.ArrayList;

/*
This activity is navigated to when the provider clicks on any list view where they are able to see their tasks.
It serves as a custom show page that is varied slightly based on the status of the task that was clicked on.
In all cases, the details of the task along with it's lowest bid and the option to delete the task is shown.
When the task is "requested", there is an additional button to navigate to EditTAskActivity where the
task may be edited. When the task is "bidded", there is an additional button to navigate to
ViewBidsOnTaskActivity where a list of all the bids on a particular task is shown. When the task
is "assigned", there are two additional buttons to mark the status of the task as either "Done" or "Requested".
 */

public class ShowTaskDetailActivity extends AppCompatActivity {

    private Button editTask;
    private Button deleteTask;
    private Button viewBidsButton;
    private Button markDone;
    private Button markRequested;
    private Button mapButton;
    private Button seePhotoButton;


    private TextView taskName;
    private TextView taskDescription;
    private TextView taskStatus;
    private TextView taskLowestBid;
    private TextView textView;
    private TextView taskProvider;
    private TextView bidTextView;

    private String provider_string;
    private User currentTaskProvider, currentUser;

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task_detail);

        task = (Task) getIntent().getSerializableExtra("task");
        currentUser = SetPublicCurrentUser.getCurrentUser();

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

        taskProvider.setText(task.getTaskProvider());

        provider_string = taskProvider.getText().toString();

        if (task.getStatus().equals("Requested")) {
            editTask = findViewById(R.id.editTask);
            editTask.setVisibility(View.VISIBLE);
            editTask.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(ShowTaskDetailActivity.this, EditTaskActivity.class);
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

                // offline behavior
                MainActivity.user.deleteRequesterTasks(task);

                BidList allBids = task.getBids();
                for (int i = 0; i < allBids.size(); i++) {
                    ElasticSearchBidController.DeleteBidTask deleteBidTask = new ElasticSearchBidController.DeleteBidTask();
                    deleteBidTask.execute(allBids.get(i));
                }

                Toast.makeText(ShowTaskDetailActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                Intent deleteTaskIntent = new Intent(getApplicationContext(), ViewRequestedTasksActivity.class);
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
                    Intent intent = new Intent(getApplicationContext(), ViewBidsOnTaskActivity.class);
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
                    updateStatistics(task.getTaskProvider(), task.getLowestBid());
                    Toast.makeText(ShowTaskDetailActivity.this, "Task Marked as Done", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(ShowTaskDetailActivity.this, RateTaskProviderActivity.class),3);
                }

            });


            markRequested = findViewById(R.id.mark_requested_button);
            markRequested.setVisibility(View.VISIBLE);
            markRequested.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    task.setStatus("Requested");
                    task.setTaskProvider("");

                    ElasticSearchTaskController.EditTask editTask = new ElasticSearchTaskController.EditTask();
                    editTask.execute(task);
                    Toast.makeText(ShowTaskDetailActivity.this, "Task Marked as Requested", Toast.LENGTH_SHORT).show();
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

        mapButton = findViewById(R.id.seeMap);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TaskMapActivity.class);
                intent.putExtra("Task", task);
                intent.putExtra("Mode", "AddMarker");
                startActivityForResult(intent, 2);
            }
        });

        seePhotoButton = findViewById(R.id.seePicture);
        seePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowPhotoActivity.class);
                ArrayList<String> encodedPhotos = task.getPictures();
                intent.putExtra("photos", encodedPhotos);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if (i == null) {

        }
        else if (requestCode == 1) {
            Task newTask = (Task) i.getSerializableExtra("TaskEdited");
            taskName.setText(newTask.getTaskName());
            task.setTaskName(newTask.getTaskName());
            taskDescription.setText(newTask.getTaskDescription());
            task.setTaskDescription(newTask.getTaskDescription());
            taskStatus.setText(newTask.getStatus());
            task.setStatus(newTask.getStatus());
            taskLowestBid.setText(newTask.getLowestBid());
        }
        else if (requestCode == 2) {
            Task newTask = (Task) i.getSerializableExtra("TaskWithLoc");
            task.setLongitude(newTask.getLongitude());
            task.setLatitude(newTask.getLatitude());
        }
        else if (requestCode == 3) {
            Double rating = (Double) i.getSerializableExtra("Rating");
            currentTaskProvider.updateRating(rating);
            ElasticSearchUserController.EditUserTask editCurrentTaskProvider = new ElasticSearchUserController.EditUserTask();
            editCurrentTaskProvider.execute(currentTaskProvider);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1000);
        }
    }
    public void updateStatistics(String taskProviderUsername, String earning){
        Toast.makeText(ShowTaskDetailActivity.this, "Task Marked as Done", Toast.LENGTH_LONG).show();
        ElasticSearchUserController.GetUserTask getUser = new ElasticSearchUserController.GetUserTask();
        getUser.execute(taskProviderUsername);
        try {
            currentTaskProvider = getUser.get();
        } catch (Exception e) {
            Log.d("Error", "Cannot find task provider in database");
        }
        currentUser.updateCompletedPostedTasks();
        currentTaskProvider.updateCompletedProvidedTasks();
        currentTaskProvider.updateTotalEarnings(Double.parseDouble(earning));
        ElasticSearchUserController.EditUserTask editCurrentUser = new ElasticSearchUserController.EditUserTask();
        ElasticSearchUserController.EditUserTask editCurrentTaskProvider = new ElasticSearchUserController.EditUserTask();
        editCurrentUser.execute(currentUser);
        editCurrentTaskProvider.execute(currentTaskProvider);
    }

    public void clickHandler_provider(View v){
        ElasticSearchUserController.GetUserTask getUser = new ElasticSearchUserController.GetUserTask();
        getUser.execute(provider_string);
        User user = new User();
        try {
            user = getUser.get();
        } catch (Exception e) {
            //Log.d
        }
        Intent intent = new Intent(ShowTaskDetailActivity.this, ViewProfileActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }
}
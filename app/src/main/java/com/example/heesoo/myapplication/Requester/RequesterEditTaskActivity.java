package com.example.heesoo.myapplication.Requester;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.heesoo.myapplication.Constraints.TaskConstraints;
import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;

import static com.example.heesoo.myapplication.Requester.RequesterMainActivity.checkNetwork;


/**
 * Created by manuelakm on 2018-03-15.
 */

/*
This activity is navigated to when the requester clicks on the edit task button when viewing a task with status requested
through the RequesterShowTaskDetailActivity. This activity provides an interface to the task requester
where they can edit the details of a task that they requested earlier.
 */

public class RequesterEditTaskActivity extends AppCompatActivity {

    private EditText taskName;
    private EditText taskDescription;
    private Button addPictureButton;
    private Button saveChangesButton;
    private Task task;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        addPictureButton = findViewById(R.id.addPicture);
        taskName = findViewById(R.id.taskNameEdit);
        taskDescription = findViewById(R.id.descriptionEdit);
        saveChangesButton = findViewById(R.id.saveChangesButton);

        Intent intent = getIntent();
        task = (Task)intent.getSerializableExtra("TaskToEdit");
        taskName.setText(task.getTaskName());
        taskDescription.setText(task.getTaskDescription());

        // set editing
        task.setEditStatus(true);
        ElasticSearchTaskController.EditTask setEditing = new ElasticSearchTaskController.EditTask();
        setEditing.execute(task);

        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequesterEditTaskActivity.this, AddPictureActivity.class);
                intent.putExtra("Task", task);
                startActivity(intent);
            }
        });

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                setResult(RESULT_OK);
                String name = taskName.getText().toString();
                String description = taskDescription.getText().toString();

                // Check if Required fields are entered
                TaskConstraints taskConstraints = new TaskConstraints();
                if (taskConstraints.checkEmpty(name, description)) {
                    if (taskConstraints.titleLength(name)) {
                        if (taskConstraints.descriptionLength(description)) {

                            // offline behavior
                            for (Task changedTask : MainActivity.user.getRequesterTasks()){
                                if(changedTask.getId().equals(task.getId())){
                                    changedTask.setTaskName(name);
                                    changedTask.setTaskDescription(description);
                                }
                            }
                            if (!checkNetwork(RequesterEditTaskActivity.this)) {
                                MainActivity.needSync = true;
                            }
                            task.setTaskName(name);
                            task.setTaskDescription(description);

                            ElasticSearchTaskController.EditTask editTask =
                                    new ElasticSearchTaskController.EditTask();
                            editTask.execute(task);

                            Intent edited_task = new Intent(getApplicationContext(), RequesterShowTaskDetailActivity.class);
                            edited_task.putExtra("TaskEdited", task);
                            setResult(Activity.RESULT_OK, edited_task);
                            CharSequence text = "Saving Task";
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            // set not editing
                            task.setEditStatus(false);
                            ElasticSearchTaskController.EditTask setNotEditing = new ElasticSearchTaskController.EditTask();
                            setNotEditing.execute(task);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 1000);
                        }else{
                            Toast.makeText(getApplicationContext(),"Maximum length of Description (300 characters)", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Maximum length of Title (30 characters)", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    CharSequence text = "Missing Required Fields";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });


    }
}


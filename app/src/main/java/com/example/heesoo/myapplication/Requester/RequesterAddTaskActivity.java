package com.example.heesoo.myapplication.Requester;

import android.content.Context;
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
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;
import com.example.heesoo.myapplication.R;


/**
 * Created by riyariya on 2018-03-12.
 */

/*
This activity is navigated to when the requester clicks on the button to add a task in the RequesterMainActivity.
This activity provides an interface to the task requester where they can enter the details of a task
that they want to request and add that task.
 */

public class RequesterAddTaskActivity extends AppCompatActivity {
    private EditText taskName;
    private EditText taskDescription;
    private Button saveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskName = findViewById(R.id.taskName);
        taskDescription = findViewById(R.id.taskDescription);
        saveButton = findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                setResult(RESULT_OK);
                String name = taskName.getText().toString();
                String description = taskDescription.getText().toString();


                // Save all the fields
                    TaskConstraints taskConstraints = new TaskConstraints();
                    if (taskConstraints.checkEmpty(name, description)) {
                        if (taskConstraints.titleLength(name)) {
                            if (taskConstraints.descriptionLength(description)) {
                                Task task = new Task(SetCurrentUser.getCurrentUser().getUsername(), name, description);
//                                ElasticSearchTaskController.AddTask addTasksTask = new ElasticSearchTaskController.AddTask();
//                                addTasksTask.execute(task);
                                // offline behavior
                                MainActivity.user.addRequesterTasks(task);
                                MainActivity.user.sync();
                                CharSequence text = "Saving Task";
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();

                                //Clear all the views
                                taskName.getText().clear();
                                taskDescription.getText().clear();
                                //final Intent intent = new Intent(RequesterAddTaskActivity.this, RequesterMainActivity.class);
                                //startActivity(intent);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 1000);
                            } else {
                                Toast.makeText(getApplicationContext(), "Maximum length of Description (300 characters)", Toast.LENGTH_SHORT).show();
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

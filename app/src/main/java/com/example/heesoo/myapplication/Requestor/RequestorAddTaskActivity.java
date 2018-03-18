package com.example.heesoo.myapplication.Requestor;

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
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;
import com.example.heesoo.myapplication.R;


/**
 * Created by riyariya on 2018-03-12.
 */


public class RequestorAddTaskActivity extends AppCompatActivity {
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

                // Check if Required fields are entered
                if (name.isEmpty()){
                    CharSequence text = "Missing Required Fields";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }else{
                // Save all the fields
                    TaskConstraints taskConstraints = new TaskConstraints();
                    if (taskConstraints.titleLength(name)){
                        if (taskConstraints.descriptionLength(description)) {
                            Task task = new Task(SetCurrentUser.getCurrentUser().getUsername(), name, description);
                            ElasticSearchTaskController.AddTask addTasksTask = new ElasticSearchTaskController.AddTask();
                            addTasksTask.execute(task);
                            CharSequence text = "Saving Task";
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            //Clear all the views
                            taskName.getText().clear();
                            taskDescription.getText().clear();
                            //final Intent intent = new Intent(RequestorAddTaskActivity.this, RequesterMainActivity.class);
                            //startActivity(intent);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 1000);
                        }else{
                            Toast.makeText(getApplicationContext(),"Maximum length of Description (300 characters)", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Maximum length of Title (30 characters)", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

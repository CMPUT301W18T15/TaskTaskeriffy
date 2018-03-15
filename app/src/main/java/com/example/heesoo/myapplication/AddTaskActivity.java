package com.example.heesoo.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by riyariya on 2018-03-12.
 */


public class AddTaskActivity extends AppCompatActivity {
    private EditText taskName;
    private EditText taskDescription;
    private Button saveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LifeCycle ---->", "onCreate is called");
        setContentView(R.layout.addtask);

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
                }
                // Save all the fields
                else{
                    Task task = new Task(MyApplication.getCurrentUser().getUsername(), name, description, "Requested");
                    ElasticSearchTaskController.AddTasksTask addTasksTask = new ElasticSearchTaskController.AddTasksTask();
                    addTasksTask.execute(task);
                    CharSequence text = "Saving Task";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    //Clear all the views
                    taskName.getText().clear();
                    taskDescription.getText().clear();
                }
            }
        });
    }
}
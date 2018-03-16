package com.example.heesoo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.Entities.Task;


/**
 * Created by manuelakm on 2018-03-15.
 */

public class EditTaskActivity extends AppCompatActivity {
    private EditText taskNameEdit;
    private EditText descriptionEdit;
    private Button saveChangesButton;
    private Task task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        taskNameEdit = findViewById(R.id.taskNameEdit);
        descriptionEdit = findViewById(R.id.descriptionEdit);
        saveChangesButton = findViewById(R.id.saveChangesButton);

        Intent intent = getIntent();
        task = (Task)intent.getSerializableExtra("TaskToEdit");
        taskNameEdit.setText(task.getTaskName());
        descriptionEdit.setText(task.getTaskDescription());

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTaskName = taskNameEdit.getText().toString();
                String newTaskDescription = descriptionEdit.getText().toString();

                task.setTaskName(newTaskName);
                task.setTaskDescription(newTaskDescription);

                ElasticSearchTaskController.EditTask editTask =
                        new ElasticSearchTaskController.EditTask();
                editTask.execute(task);
                finish();
            }
        });


    }
}

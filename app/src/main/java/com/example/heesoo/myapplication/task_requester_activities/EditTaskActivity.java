package com.example.heesoo.myapplication.task_requester_activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.heesoo.myapplication.constraints.TaskConstraints;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.login_activity.MainActivity;
import com.example.heesoo.myapplication.R;

import static com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity.checkNetwork;


/**
 * Created by manuelakm on 2018-03-15.
 */

/*
This activity is navigated to when the requester clicks on the edit task button when viewing a task with status requested
through the ShowTaskDetailActivity. This activity provides an interface to the task requester
where they can edit the details of a task that they requested earlier.
 */

public class EditTaskActivity extends AppCompatActivity {

    private EditText taskName;
    private EditText taskDescription;
    private Button addPictureButton;
    private Button saveChangesButton;
    public String ImageDecode;
    private ImageView mImageView;


    private static int IMG_RESULT = 1;
    private Task task;
    private Bitmap bitmap;


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
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, IMG_RESULT);
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
                            for (int i = 0; i < MainActivity.user.getRequesterTasks().getSize(); i++) {
                                Task changedTask = MainActivity.user.getRequesterTasks().getTask(i);

                                if (task.getId() != null) {
                                    if (changedTask.getId().equals(task.getId())) {
                                        changedTask.setTaskName(name);
                                        changedTask.setTaskDescription(description);
                                    }
                                }else {
                                    if(changedTask.getTaskName().equals(task.getTaskName()) && changedTask.getTaskDescription().equals(task.getTaskDescription())){
                                        changedTask.setTaskName(name);
                                        changedTask.setTaskDescription(description);
                                    }
                                }
                            }
                            if (!checkNetwork(EditTaskActivity.this)) {
                                MainActivity.needSync = true;
                            }
                            task.setTaskName(name);
                            task.setTaskDescription(description);
                            if (bitmap != null) {
                                String base64String = ConvertImageActivity.convert(bitmap);
                                task.addPicture(base64String);
                            }

                            ElasticSearchTaskController.EditTask editTask =
                                    new ElasticSearchTaskController.EditTask();
                            editTask.execute(task);

                            Intent edited_task = new Intent(getApplicationContext(), ShowTaskDetailActivity.class);
                            edited_task.putExtra("TaskEdited", task);
                            setResult(Activity.RESULT_OK, edited_task);
                            CharSequence text = "Saving Task";
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();


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


    protected void onDestroy() {
        super.onDestroy();
        // set not editing
        task.setEditStatus(false);
        ElasticSearchTaskController.EditTask setNotEditing = new ElasticSearchTaskController.EditTask();
        setNotEditing.execute(task);
        Log.d("Log", "On Destroy called!");
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == IMG_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = { MediaStore.Images.Media.DATA };


                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                ImageDecode = cursor.getString(columnIndex);
                cursor.close();

                Log.e("NEW","NEW"+ URI);

                //TODO: Returns bitmap = null. Unsure how to fix. Critical for multiple photographs
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(URI));

                mImageView.setImageBitmap(bitmap);

            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }
    }
}


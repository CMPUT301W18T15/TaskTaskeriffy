package com.example.heesoo.myapplication.task_requester_activities;

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
import com.example.heesoo.myapplication.shared_preferences.SetPublicCurrentUser;
import com.example.heesoo.myapplication.R;


import static com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity.checkNetwork;


/**
 * Created by riyariya on 2018-03-12.
 */

/*
This activity is navigated to when the requester clicks on the button to add a task in the RequesterMainActivity.
This activity provides an interface to the task requester where they can enter the details of a task
that they want to request and add that task.
 */

public class AddTaskActivity extends AppCompatActivity {
    private EditText taskName;
    private EditText taskDescription;
    private Button saveButton;
    private Button addPhoto;
    private Bitmap bitmap;

    private Task task;

    private ImageView mImageView;


    private static int IMG_RESULT = 1;
    public String ImageDecode;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskName = findViewById(R.id.taskName);
        taskDescription = findViewById(R.id.taskDescription);
        saveButton = findViewById(R.id.save);
        addPhoto = findViewById(R.id.addPhoto);
        mImageView = findViewById(R.id.mImageView);


        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                setResult(RESULT_OK);
                String name = taskName.getText().toString();
                String description = taskDescription.getText().toString();

                    TaskConstraints taskConstraints = new TaskConstraints();
                    if (taskConstraints.checkEmpty(name, description)) {
                        if (taskConstraints.titleLength(name)) {
                            if (taskConstraints.descriptionLength(description)) {
                                Task task = new Task(SetPublicCurrentUser.getCurrentUser().getUsername(), name, description);
                                if (bitmap != null) {
                                    String base64String = ConvertImageActivity.convert(bitmap);
                                    if(base64String!=null){
                                        task.addPicture(base64String);
                                    }else{
                                        CharSequence text = "Image too big, saving without it!";
                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();
                                    }
                                }
                                ElasticSearchTaskController.AddTask addTasksTask = new ElasticSearchTaskController.AddTask();
                                addTasksTask.execute(task);
                                MainActivity.user.addRequesterTasks(task);

                                if (!checkNetwork(AddTaskActivity.this)) {
                                    MainActivity.needSync = true;
                                }

                                CharSequence text = "Saving Task";
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                try {
                                    Thread.currentThread().sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                taskName.getText().clear();
                                taskDescription.getText().clear();
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

        addPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /* Taken from https://stackoverflow.com/questions/11144783/how-to-access-an-image-from-the-phones-photo-gallery
                on Mar 28, 2018. Takes picture from gallery to show in image view.
                 */

                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, IMG_RESULT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == IMG_RESULT && resultCode == RESULT_OK
                    && null != data) {

                /*Reference taken from https://stackoverflow.com/questions/3401579/get-filename-and-path-from-uri-from-mediastore?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
                on April 4,2018 */
                Uri URI = data.getData();
                String[] FILE = { MediaStore.Images.Media.DATA };


                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                ImageDecode = cursor.getString(columnIndex);
                cursor.close();

                Log.e("NEW","NEW"+ URI);

                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(URI));

                mImageView.setImageBitmap(bitmap);

            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }
    }

}

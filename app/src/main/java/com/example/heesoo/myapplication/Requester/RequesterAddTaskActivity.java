package com.example.heesoo.myapplication.Requester;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.heesoo.myapplication.Constraints.TaskConstraints;
import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;
import com.example.heesoo.myapplication.R;

import org.apache.commons.lang3.ObjectUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.example.heesoo.myapplication.Requester.RequesterMainActivity.checkNetwork;


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
    private Button addPhoto;
    private Bitmap bitmap;

    private ImageView mImageView;

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


                // Save all the fields
                    TaskConstraints taskConstraints = new TaskConstraints();
                    if (taskConstraints.checkEmpty(name, description)) {
                        if (taskConstraints.titleLength(name)) {
                            if (taskConstraints.descriptionLength(description)) {
                                Task task = new Task(SetCurrentUser.getCurrentUser().getUsername(), name, description);
                                if(bitmap!= null){
                                    String base64String = ImageUtil.convert(bitmap);
                                    task.addPicture(base64String);
                                }
                                MainActivity.user.addRequesterTasks(task);
                                MainActivity.user.sync();



                                CharSequence text = "Saving Task";
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();

                                //Clear all the views
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
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);

                /* Another implementation is at Taken from https://stackoverflow.com/questions/12995185/android-taking-photos-and-saving-them-with-a-custom-name-to-a-custom-destinati
                 on Mar 28, 2018. Opens camera to snap picture and store in external file*/
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* Taken from https://stackoverflow.com/questions/11144783/how-to-access-an-image-from-the-phones-photo-gallery
                on Mar 28, 2018
         */
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK ){
            Uri targetUri = data.getData();
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                mImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

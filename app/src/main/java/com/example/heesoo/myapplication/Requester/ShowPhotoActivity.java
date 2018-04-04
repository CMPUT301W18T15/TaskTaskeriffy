package com.example.heesoo.myapplication.Requester;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.R;

import java.util.ArrayList;

/**
 * Created by riyariya on 2018-03-29.
 */

public class ShowPhotoActivity extends AppCompatActivity {
    private ImageView showPhoto;
    private Task task;
    private String my_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_photo);

        my_new = task.getPicture();

        Log.e("MEME","MEME"+my_new);

    }
}

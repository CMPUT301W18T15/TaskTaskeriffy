package com.example.heesoo.myapplication.general_activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.task_requester_activities.ConvertImageActivity;

import java.util.ArrayList;

/**
 * Created by riyariya on 2018-03-29.
 */

public class ShowPhotoActivity extends AppCompatActivity {

    private LinearLayout showPhoto;
    private ArrayList<String> encodedPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        encodedPhotos = (ArrayList<String>) getIntent().getSerializableExtra("photos");

        showPhoto = findViewById(R.id.showPhoto);

        for(int i = 0; i < encodedPhotos.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Bitmap bitmap = ConvertImageActivity.convert(encodedPhotos.get(i));
            imageView.setImageBitmap(bitmap);
            showPhoto.addView(imageView);
        }
    }
}
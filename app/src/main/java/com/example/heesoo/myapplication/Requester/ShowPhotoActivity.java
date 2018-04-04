package com.example.heesoo.myapplication.Requester;

import android.graphics.Bitmap;
import android.media.Image;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_photo);
        String encodedPhoto = (String) getIntent().getSerializableExtra("photo");

        showPhoto = findViewById(R.id.showPhoto);

        Bitmap bitmap = ImageUtil.convert(encodedPhoto);
        showPhoto.setImageBitmap(bitmap);

    }
}

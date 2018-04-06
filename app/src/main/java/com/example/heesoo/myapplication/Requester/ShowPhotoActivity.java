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
    private LinearLayout showPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_photo);
        ArrayList<String> encodedPhotos = (ArrayList<String>) getIntent().getSerializableExtra("photos");

        showPhoto = findViewById(R.id.showPhoto);
        for(int i=0; i<encodedPhotos.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Bitmap bitmap = ImageUtil.convert(encodedPhotos.get(i));
            imageView.setImageBitmap(bitmap);
            showPhoto.addView(imageView);
        }
    }
}

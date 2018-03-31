package com.example.heesoo.myapplication.Requester;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        ArrayList<String> encodedPhotos = (ArrayList<String>) getIntent().getSerializableExtra("photo");
        /* Reference: https://stackoverflow.com/questions/15356473/create-imageviews-dynamically-inside-a-loop/15356823 */
        LinearLayout layout = (LinearLayout)findViewById(R.id.showPhoto);
        for(int i=0;i<encodedPhotos.size();i++)
        {
            ImageView image = new ImageView(this);
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(80,60));
            image.setMaxHeight(20);
            image.setMaxWidth(20);
            Bitmap bitmap = ImageUtil.convert(encodedPhotos.get(i));
            image.setImageBitmap(bitmap);

            // Adds the view to the layout
            layout.addView(image);
        }
    }
}

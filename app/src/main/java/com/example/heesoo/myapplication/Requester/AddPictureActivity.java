package com.example.heesoo.myapplication.Requester;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.Provider.ProviderMainActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;

import java.io.FileNotFoundException;

/**
 * Created by manuelakm on 2018-03-31.
 */

public class AddPictureActivity extends AppCompatActivity {

    private Task task;
    private Button addPhoto;
    private Button savePhoto;
    private ImageView mImageView;
    private Bitmap bitmap;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);

        addPhoto = findViewById(R.id.addPhotoButton);
        savePhoto = findViewById(R.id.savePhotoButton);
        mImageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("Task");

        savePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bitmap != null) {
                    String base64String = ImageUtil.convert(bitmap);
                    task.addPicture(base64String);
                    Log.d("PHOTO", Integer.toString(task.getPictures().size()));
                }
                //MainActivity.user.addRequesterTasks(task);
                MainActivity.user.sync();
                startActivity(new Intent(AddPictureActivity.this, RequesterMainActivity.class));
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                        /* Taken from https://stackoverflow.com/questions/11144783/how-to-access-an-image-from-the-phones-photo-gallery
                        on Mar 28, 2018. Takes picture from gallery to show in image view.
                         */

                setResult(RESULT_OK);

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

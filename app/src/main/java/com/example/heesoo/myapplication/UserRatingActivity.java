package com.example.heesoo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.Entities.User;
import com.example.heesoo.myapplication.Requester.RequesterShowTaskDetailActivity;

/**
 * Created by heesoopark on 2018-04-03.
 */

public class UserRatingActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rating);
        // Initialize RatingBar
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                Intent intent = new Intent(getApplicationContext(), RequesterShowTaskDetailActivity.class);
                intent.putExtra("Rating", rating);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        //User user = new User();
        //Double user_rating = user.getRating();

        //Log.i("YourApp", "I have the RATING! "+ user_rating);

    }



}

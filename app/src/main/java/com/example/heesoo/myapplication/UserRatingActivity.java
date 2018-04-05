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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rating);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Intent intent = new Intent(getApplicationContext(), RequesterShowTaskDetailActivity.class);
                Double double_rating  = Double.parseDouble(new Float(ratingBar.getRating()).toString());
                intent.putExtra("Rating", double_rating);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }



}

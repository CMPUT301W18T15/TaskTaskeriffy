package com.example.heesoo.myapplication.task_requester_activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;

import com.example.heesoo.myapplication.R;

/*
This activity prompts the user to rate a task provider's performance. This activity is called in when
a task is marked as "Completed" in the following activity : ShowTaskDetailActivity.
 */

public class RateTaskProviderActivity extends AppCompatActivity {

    private RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_provider);

        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Intent intent = new Intent(getApplicationContext(), ShowTaskDetailActivity.class);
                Double double_rating  = Double.parseDouble(new Float(ratingBar.getRating()).toString());
                intent.putExtra("Rating", double_rating);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
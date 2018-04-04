package com.example.heesoo.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.heesoo.myapplication.Entities.User;

/**
 * Created by heesoopark on 2018-04-03.
 */

public class UserRatingActivity extends AppCompatActivity {
    public RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rating);
        // Initialize RatingBar
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        User user = new User();
        Double user_rating = user.getRating();

        Log.i("YourApp", "I have the RATING! "+ user_rating);

    }

    /**
     * Display rating by calling getRating() method.
     * @param view
     */
    public void rateMe(View view){

        Toast.makeText(getApplicationContext(),
                String.valueOf(ratingBar.getRating()), Toast.LENGTH_LONG).show();
    }


}

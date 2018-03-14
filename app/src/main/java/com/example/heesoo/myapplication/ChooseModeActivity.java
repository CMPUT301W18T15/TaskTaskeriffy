package com.example.heesoo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by heesoopark on 2018-03-13.
 */

public class ChooseModeActivity extends AppCompatActivity {

    private String testing;
    private Button request_button;
    private Button provide_button;


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_mode_acitivity);
        Log.d("KEVIN", "vvv");

        testing = MyApplication.getCurrentUser();

        Log.d("TESTING USER", testing);

        request_button = findViewById(R.id.request_mode_button);
        request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.setCurrentMode("Requestor");
                startActivity(new Intent(ChooseModeActivity.this, RequesterMainActivity.class ));
            }
        });

        provide_button = findViewById(R.id.provide_mode_button);
        provide_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.setCurrentMode("Provider");
                startActivity(new Intent(ChooseModeActivity.this, ProviderMainActivity.class));
            }
        });

    }
}



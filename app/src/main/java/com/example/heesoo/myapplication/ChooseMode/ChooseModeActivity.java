package com.example.heesoo.myapplication.ChooseMode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.heesoo.myapplication.Provider.ProviderMainActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.Requester.RequesterMainActivity;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;

/**
 * Created by heesoopark on 2018-03-13.
 */

public class ChooseModeActivity extends AppCompatActivity {

    private Button request_button;
    private Button provide_button;


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mode);


        request_button = findViewById(R.id.request_mode_button);
        request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetCurrentUser.setCurrentMode("Requester");
                startActivity(new Intent(ChooseModeActivity.this, RequesterMainActivity.class ));
            }
        });

        provide_button = findViewById(R.id.provide_mode_button);
        provide_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetCurrentUser.setCurrentMode("Provider");
                startActivity(new Intent(ChooseModeActivity.this, ProviderMainActivity.class));
            }
        });

    }
}



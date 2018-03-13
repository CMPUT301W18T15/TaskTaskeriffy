package com.example.heesoo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ChooseModeActivity extends AppCompatActivity {
    private Button providerButton;
    private Button requesterButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mode);

        // choose mode button
        Button providerButton = (Button) findViewById(R.id.provider_button);
        Button requesterButton = (Button) findViewById(R.id.requester_button);

        // go to provider mode
        providerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (ChooseModeActivity.this, ProviderMainActivity.class);
                startActivity(intent);
            }
        });

        //go to requester mode
        requesterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (ChooseModeActivity.this, RequesterMainActivity.class);
                startActivity(intent);
            }
        });

    }
    private void gotoRequester(){

    }

    private void gotoProvider(){

    }
}

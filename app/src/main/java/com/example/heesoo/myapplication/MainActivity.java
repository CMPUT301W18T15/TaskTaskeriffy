package com.example.heesoo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.heesoo.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private EditText edit_username;
    private String login_username_str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edit_username = (EditText) findViewById(R.id.login_username);

        Button register_button = (Button) findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, com.example.heesoo.myapplication.RegisterActivity.class));
            }
        });

        Button login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_username_str = edit_username.getText().toString();
                MyApplication.setCurrentUser(login_username_str);
                startActivity(new Intent(MainActivity.this, ChooseModeActivity.class));
            }
        });


    }


}

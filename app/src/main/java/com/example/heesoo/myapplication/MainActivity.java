package com.example.heesoo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.heesoo.myapplication.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText enter_username;
    private EditText enter_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        enter_username = findViewById(R.id.login_username);
        enter_password = findViewById(R.id.login_password);
        Button register_button = findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, com.example.heesoo.myapplication.RegisterActivity.class));
            }
        });

        Button login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ArrayList<String> user_information = new ArrayList<String>();
                    user_information.add(enter_username.getText().toString());
                    user_information.add(enter_password.getText().toString());

                    ElasticSearchController.GetUserTask getUserTask = new ElasticSearchController.GetUserTask();
                    getUserTask.execute(user_information);

                    //User user = new User("RiyaRiya", "123","manuela@manuela.com", "0000000000");
                    //MyApplication.setCurrentUser(user);

                    try{
                        User user = getUserTask.get();
                        MyApplication.setCurrentUser(user);
                        startActivity(new Intent(MainActivity.this, ChooseModeActivity.class));
                    } catch(Exception e){
                        Log.i("ERROR", "Failed to pull account from Database");
                    }
                    startActivity(new Intent(MainActivity.this, ChooseModeActivity.class));

                }
        });


    }


}

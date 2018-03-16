package com.example.heesoo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchUserController;
import com.example.heesoo.myapplication.Entities.User;
import com.example.heesoo.myapplication.Register.RegisterActivity;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText enter_username;
    private EditText enter_password;
    private ElasticSearchUserController elasticSearchUserController;

    private String user_str,pwd_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        elasticSearchUserController = new ElasticSearchUserController();


        enter_username = findViewById(R.id.login_username);
        enter_password = findViewById(R.id.login_password);
        Button register_button = findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        Button login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<String> user_information = new ArrayList<String>();
                user_information.add(enter_username.getText().toString());
                user_information.add(enter_password.getText().toString());

                user_str = enter_username.getText().toString();
                pwd_str = enter_password.getText().toString();

                //User user = new User("RiyaRiya", "123", "manuela@manuela.com", "0000000000");

                if (elasticSearchUserController.profileExists(user_str)) {
                    startActivity(new Intent(MainActivity.this, ChooseModeActivity.class));
                    User user = new User(user_str, pwd_str, "heesoo@ualberta.ca", "3065719977");
                    MyApplication.setCurrentUser(user);
                    Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, ChooseModeActivity.class));

                } else {
                    Toast.makeText(getApplicationContext(), "Account Does not Exist", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


}

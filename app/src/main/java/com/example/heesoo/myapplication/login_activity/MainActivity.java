package com.example.heesoo.myapplication.login_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchUserController;
import com.example.heesoo.myapplication.entities.User;
import com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity;
import com.example.heesoo.myapplication.general_activities.NotificationThread;
import com.example.heesoo.myapplication.shared_preferences.SetPublicCurrentUser;
import com.example.heesoo.myapplication.R;


import java.util.ArrayList;

/*
This activity gives the user the interface to log in. It contains a button that navigates to register
instead in case, the user is not registered. This is the main starting screen for the application.
The user must be registered and the correct username password combination must be provided for a
successful login.
 */

public class MainActivity extends AppCompatActivity {

    private EditText enter_username;
    private EditText enter_password;
    private ElasticSearchUserController elasticSearchUserController;

    private Context context = this;
    public static User user;
    private NotificationThread monitorBidsThread;
    public static Boolean needSync = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        elasticSearchUserController = new ElasticSearchUserController();
        monitorBidsThread = new NotificationThread(context);

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

                String user_str = enter_username.getText().toString();
                String pwd_str = enter_password.getText().toString();

                if (checkEmpty(user_str,pwd_str)){

                    if (elasticSearchUserController.profileExists(user_str)) {
                        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
                        getUserTask.execute(user_str);
                        user = null;
                        try {
                            user = getUserTask.get();
                        } catch (Exception e) {
                            Log.i("ERROR", "The request for user from database failed");
                        }

                        user.initializeOffline();
                        SetPublicCurrentUser.setCurrentUser(user);

                        if (pwd_str.equals(user.getPassword())) {
                            Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();

                            monitorBidsThread.start();
                            startActivity(new Intent(MainActivity.this, ViewRequestedTasksActivity.class));

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Password Does not Match", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {
                        Toast.makeText(getApplicationContext(), "Account Does not Exist", Toast.LENGTH_SHORT).show();

                    }
            }
            else{
                    Toast.makeText(getApplicationContext(), "Please fill in Username and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        monitorBidsThread.stop();
    }

    private boolean checkEmpty(String username,String password) {
        return !(username.equals("") || password.equals(""));
    }

}

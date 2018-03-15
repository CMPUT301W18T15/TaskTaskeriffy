package com.example.heesoo.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.heesoo.myapplication.ElasticSearchController;
import com.example.heesoo.myapplication.MainActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.User;

import java.util.concurrent.ExecutionException;


/**
 * Created by heesoopark on 2018-03-12.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameTxt, passwordTxt, repeat_passwordTxt, emailTxt, phoneTxt;

    private Button submit_button;

    private String usernameStr, passwordStr, repeat_passwordStr, emailStr, phoneStr;

    private AsyncTask<String, Void, User> profile;

    private ElasticSearchController elasticSearchController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        elasticSearchController = new ElasticSearchController();


        submit_button = findViewById(R.id.register_submit);
        usernameTxt = findViewById(R.id.enter_username);
        passwordTxt = findViewById(R.id.enter_password);
        repeat_passwordTxt = findViewById(R.id.enter_repeat_password);
        emailTxt = findViewById(R.id.enter_email);
        phoneTxt = findViewById(R.id.enter_phone);

        init();

    }

    private void init() {

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usernameStr = usernameTxt.getText().toString();
                passwordStr = passwordTxt.getText().toString();
                repeat_passwordStr = repeat_passwordTxt.getText().toString();
                emailStr = emailTxt.getText().toString();
                phoneStr = phoneTxt.getText().toString();

                if (checkEmpty(usernameStr, passwordStr, repeat_passwordStr, emailStr, phoneStr)) {
                    if(pwdMatch(passwordStr, repeat_passwordStr)){
                        if (elasticSearchController.profileExists(usernameStr)) {
                            Toast.makeText(getApplicationContext(),"Username already exists", Toast.LENGTH_SHORT).show();
                        }else{
                            User user = new User(usernameStr, passwordStr, emailStr, phoneStr);
                            RegisterTask(user);
                            Toast.makeText(getApplicationContext(), "Account Registered", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Password Does Not Match", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();

                }

                Log.d("username:", usernameStr);
                Log.d("password:", passwordStr);
                Log.d("repeat:", repeat_passwordStr);
                Log.d("email:", emailStr);
                Log.d("phone:", phoneStr);
            }
        });

    }


    private void RegisterTask(User user) {
        MyApplication.setCurrentUser(user);
        ElasticSearchController.AddUserTask addUserTask = new ElasticSearchController.AddUserTask();
        addUserTask.execute(user);

        //ElasticSearchController.GetUserTask getUserTask = new ElasticSearchController.GetUserTask();
        //getUserTask.execute(usernameStr);



        //ElasticSearchController EC = new ElasticSearchController();
        //EC.AddUser(user);

    }



    private boolean pwdMatch(String pwd, String repeat_pwd) {
        return pwd.equals(repeat_pwd);
    }

    private boolean checkEmpty(String username, String password, String repeat_password, String email, String phone) {
        return !(username.equals("") || password.equals("") || repeat_password.equals("") || email.equals("") || phone.equals(""));
    }

}

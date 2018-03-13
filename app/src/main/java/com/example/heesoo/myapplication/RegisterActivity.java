package com.example.heesoo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.heesoo.myapplication.R;

import java.util.concurrent.ExecutionException;


/**
 * Created by heesoopark on 2018-03-12.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText nameTxt, userTxt, passwordTxt, repeat_passwordTxt, emailTxt, addressTxt;

    private Button submit_button;

    private String nameStr, userStr, passwordStr, repeat_passwordStr, emailStr, addressStr;

    private UserLoginTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        submit_button = (Button) findViewById(R.id.register_submit);
        nameTxt = (EditText) findViewById(R.id.enter_name);
        userTxt = (EditText) findViewById(R.id.enter_username);
        passwordTxt = (EditText) findViewById(R.id.enter_password);
        repeat_passwordTxt = (EditText) findViewById(R.id.enter_repeat_password);
        emailTxt = (EditText) findViewById(R.id.enter_email);
        addressTxt = (EditText) findViewById(R.id.enter_address);

        init();

    }

    private void init() {

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nameStr = nameTxt.getText().toString();
                userStr = userTxt.getText().toString();
                passwordStr = passwordTxt.getText().toString();
                repeat_passwordStr = repeat_passwordTxt.getText().toString();
                emailStr = emailTxt.getText().toString();
                addressStr = addressTxt.getText().toString();

                if (checkEmpty(nameStr, userStr, passwordStr, repeat_passwordStr, emailStr, addressStr)) {
                    if(pwdMatch(passwordStr, repeat_passwordStr)){
                        //mAuthTask = new UserLoginTask(userStr, nameStr);
                        Toast.makeText(getApplicationContext(), "Account Registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(), "Password Does Not Match", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();

                }

                Log.d("NAME:", nameStr);
                Log.d("username:", userStr);
                Log.d("password:", passwordStr);
                Log.d("repeat:", repeat_passwordStr);
                Log.d("email:", emailStr);
                Log.d("address:", addressStr);
            }
        });

    }

    private class UserLoginTask {
        //private ElasticSearchController.addUserTask addUserTask = new ElasticSearchController.addUserTask();
        private ElasticSearchController.GetUserTask getUserTask = new ElasticSearchController.GetUserTask();

        private final String mUsername;
        private final String mName;
        private User user;


        UserLoginTask(String username, String name) {
            mUsername = username;
            mName = name;
        }

        UserLoginTask(String username){
            mUsername = username;
            mName = null;
        }

        private boolean usernameExists(String username) {
            User user = null;
            //addUserTask.execute(username);
            getUserTask.execute(username);

            try {
                user = getUserTask.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (user == null) {
                return false;
            }
            return true;
        }




    }


    private boolean pwdMatch(String pwd, String repeat_pwd) {
        return pwd.equals(repeat_pwd);
    }

    private boolean checkEmpty(String name, String username, String password, String repeat_password, String email, String address) {
        if(name.equals("") || username.equals("") || password.equals("") || repeat_password.equals("") || email.equals("") || address.equals("")){
            return false;
        }
        return true;
    }

}

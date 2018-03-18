package com.example.heesoo.myapplication.Register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchUserController;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.Entities.User;
import com.example.heesoo.myapplication.Constraints.UserConstraints;


/**
 * Created by heesoopark on 2018-03-12.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameTxt, passwordTxt, repeat_passwordTxt, emailTxt, phoneTxt;
    private Button submit_button;
    private String usernameStr, passwordStr, repeat_passwordStr, emailStr, phoneStr;
    private ElasticSearchUserController elasticSearchUserController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        elasticSearchUserController = new ElasticSearchUserController();

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

                UserConstraints userConstraints = new UserConstraints();

                if (userConstraints.checkEmpty(usernameStr, passwordStr, repeat_passwordStr, emailStr, phoneStr)) {
                    if (userConstraints.usernameLength(usernameStr)) {
                        if (!(elasticSearchUserController.profileExists(usernameStr))) {
                            if (userConstraints.PasswordMatch(passwordStr, repeat_passwordStr)) {
                                if (userConstraints.emailFormat(emailStr)) {
                                    User user = new User(usernameStr, passwordStr, emailStr, phoneStr);
                                    RegisterTask(user);
                                    Toast.makeText(getApplicationContext(), "Account Registered", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Not A Valid Email Address", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Password Does Not Match", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Username Already Exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Username Must Be At Most 8 Characters", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "All Fields Must Be Filled", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void RegisterTask(User user) {
        SetCurrentUser.setCurrentUser(user);
        ElasticSearchUserController.AddUserTask addUserTask = new ElasticSearchUserController.AddUserTask();
        addUserTask.execute(user);
    }
}

package com.example.heesoo.myapplication.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.heesoo.myapplication.Constraints.UserConstraints;
import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchUserController;
import com.example.heesoo.myapplication.Entities.User;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;
import com.example.heesoo.myapplication.R;


/**
 * Created by manuelakm on 2018-03-13.
 */

public class EditProfileActivity extends AppCompatActivity {

    private EditText emailAddressEdit;
    private EditText phoneNumberEdit;
    private Button saveButton;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        emailAddressEdit = findViewById(R.id.emailAddressEdit);
        phoneNumberEdit = findViewById(R.id.phoneNumberEdit);
        saveButton = findViewById(R.id.saveInfoButton);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = getIntent();
        user = SetCurrentUser.getCurrentUser();

        emailAddressEdit.setText(user.getEmailAddress());
        phoneNumberEdit.setText(user.getPhoneNumber());

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                String emailAddress = emailAddressEdit.getText().toString();
                String phoneNumber = phoneNumberEdit.getText().toString();

                user.setEmailAddress(emailAddress);
                user.setPhoneNumber(phoneNumber);

                UserConstraints userConstraints = new UserConstraints();

                if(userConstraints.emailFormat(emailAddress)){
                    ElasticSearchUserController.EditUserTask editUser = new ElasticSearchUserController.EditUserTask();
                    editUser.execute(user);
                    Toast.makeText(EditProfileActivity.this, "Profile Edited", Toast.LENGTH_SHORT).show();
                    SetCurrentUser.setCurrentUser(user);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 0000);
                }else{
                    Toast.makeText(getApplicationContext(), "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
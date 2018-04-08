package com.example.heesoo.myapplication.profile_activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.heesoo.myapplication.constraints.UserConstraints;
import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchUserController;
import com.example.heesoo.myapplication.entities.User;
import com.example.heesoo.myapplication.shared_preferences.SetPublicCurrentUser;
import com.example.heesoo.myapplication.R;

import static com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity.checkNetwork;

/*
This activity gives the user the option to edit their profile. The user can navigate to this activity through the
 "Edit Profile" button which is embedded in the ViewProfileActivity, when they are viewing their own profile.
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
        checkNetwork(this);
        super.onStart();
        Intent i = getIntent();
        user = SetPublicCurrentUser.getCurrentUser();

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
                    SetPublicCurrentUser.setCurrentUser(user);
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
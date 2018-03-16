package com.example.heesoo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchUserController;
import com.example.heesoo.myapplication.Entities.User;


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

        Intent i = getIntent();
        user = MyApplication.getCurrentUser();

        emailAddressEdit.setText(user.getEmailAddress());
        phoneNumberEdit.setText(user.getPhoneNumber());

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                String emailAddress = emailAddressEdit.getText().toString();
                String phoneNumber = phoneNumberEdit.getText().toString();

                user.setEmailAddress(emailAddress);
                user.setPhoneNumber(phoneNumber);

                MyApplication.setCurrentUser(user);
                ElasticSearchUserController.EditUserTask editUser = new ElasticSearchUserController.EditUserTask();
                editUser.execute(user);
                finish();
            }
        });
    }
}
package com.example.heesoo.myapplication.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.heesoo.myapplication.ChooseMode.ChooseModeActivity;
import com.example.heesoo.myapplication.Entities.User;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;
import com.example.heesoo.myapplication.R;

/**
 * Created by manuelakm on 2018-03-13.
 */

/*
This activity gives the user an interface to view their profile which contains information about their account such as
 the username, the email address and the phone number. It contains button to navigate to the ChooseModeActivity
 where the user can switch between Task Provider or Task Requester mode. It also contains a button to navigate to the EditProfileActivity
 where the user may edit their own profile information. The User can reach this activity when clicking on any username to
 show (for eg. the bidder's details) their profile or clicking at the My Account button on the main dashboard in
 either the provider or the requester mode.
 */

public class ViewProfileActivity extends AppCompatActivity {

    private TextView usernameView;
    private TextView EmailAddressView;
    private TextView PhoneNumberView;
    private Button editButton;
    private Button changeModeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        usernameView = findViewById(R.id.UsernameView);
        EmailAddressView = findViewById(R.id.EmailAddressView);
        PhoneNumberView = findViewById(R.id.phoneNumberView);
        editButton = findViewById(R.id.editInfoButton);
        changeModeButton = findViewById(R.id.changeModeButton);


    }
    @Override
    protected void onStart() {
        super.onStart();
        User user_recieved = (User) getIntent().getSerializableExtra("USER");
        User user;
        if (user_recieved == null) {
            user = SetCurrentUser.getCurrentUser();
            editButton.setVisibility(View.VISIBLE);
            changeModeButton.setVisibility(View.VISIBLE);
        } else {
            user = user_recieved;
        }


        usernameView.setText(user.getUsername());
        EmailAddressView.setText(user.getEmailAddress());
        PhoneNumberView.setText(user.getPhoneNumber());

        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);

                Intent edit_information = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(edit_information);
                //finish();
            }
        });

        changeModeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);

                Intent change_mode = new Intent(getApplicationContext(), ChooseModeActivity.class);
                startActivity(change_mode);
            }
        });
    }

}

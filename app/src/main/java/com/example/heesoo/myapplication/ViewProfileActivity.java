package com.example.heesoo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.heesoo.myapplication.Entities.User;

/**
 * Created by manuelakm on 2018-03-13.
 */

public class ViewProfileActivity extends AppCompatActivity {

    private TextView usernameView;
    private TextView EmailAddressView;
    private TextView PhoneNumberView;
    private Button editButton;
    private Button changeModeButton;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        usernameView = findViewById(R.id.UsernameView);
        EmailAddressView = findViewById(R.id.EmailAddressView);
        PhoneNumberView = findViewById(R.id.phoneNumberView);
        editButton = findViewById(R.id.editInfoButton);
        changeModeButton = findViewById(R.id.changeModeButton);

        User user_recieved = (User) getIntent().getSerializableExtra("USER");
        if(user_recieved == null){
            user = MyApplication.getCurrentUser();
            editButton.setVisibility(View.VISIBLE);
            changeModeButton.setVisibility(View.VISIBLE);
        }else{
           user  = user_recieved;
        }

        usernameView.setText(user.getUsername());
        EmailAddressView.setText(user.getEmailAddress());
        PhoneNumberView.setText(user.getPhoneNumber());

        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);

                Intent edit_information = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(edit_information);
                finish();
            }
        });

        changeModeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);

                Intent change_mode = new Intent(getApplicationContext(), ChooseModeActivity.class);
                startActivity(change_mode);
                finish();

            }
        });
    }

}

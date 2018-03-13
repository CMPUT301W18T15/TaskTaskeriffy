package com.example.heesoo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ProviderMainActivity extends AppCompatActivity {

    private Button myHistoryButton;
    private Button myAccountButton;
    private Button findNearbyTaskButton;
    private Button viewBiddedListButton;
    private Button searchNewTaskButton;
    private ListView myAssignedTasklist;
    private TextView taskLabel;
    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> taskAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_main);

        //myHistory button
        Button myHistoryButton = (Button) findViewById(R.id.my_history_button);

        myHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProviderMainActivity.this, ViewProviderHistoryActivity.class);
                startActivity(intent);
            }
        });


        //myAccountButton
        Button myAccountButton = (Button) findViewById(R.id.my_account_button);
        myAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                Intent intent = new Intent(ProviderMainActivity.this, ViewProfileActivity.class);
                startActivity(intent);
            }
        });



    }
}

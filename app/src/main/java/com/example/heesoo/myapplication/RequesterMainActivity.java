package com.example.heesoo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class RequesterMainActivity extends AppCompatActivity {

    private Button addNewTaskButton;
    private Button myAccountButton;
    private Button showBiddedButton;
    private Button showAssignedTaskButton;


    private ListView myPostTasklist;

    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> taskAdapter;
    private ListView clickablelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_main);

        //add new task button
        Button addNewTaskButton = (Button) findViewById(R.id.add_new_task_button);
        addNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequesterMainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });


        // show my Account Button
        Button myAccountButton = (Button) findViewById(R.id.my_account_button);
        myAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                Intent intent = new Intent(RequesterMainActivity.this, ViewProfileActivity.class);
                startActivity(intent);
            }
        });




        // view requester's bidded button
        Button showBidedButton = (Button) findViewById(R.id.show_bidded_button);
        showBidedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                Intent intent = new Intent(RequesterMainActivity.this, ViewRequesterBiddedTasksListActivity.class);
                startActivity(intent);
            }

        });

        // requestor show assigned task button
        Button showAssignedTaskButton = (Button) findViewById(R.id.show_assigned_task_button);
        showAssignedTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                Intent intent = new Intent(RequesterMainActivity.this, ViewRequestorAssignedTasksListActivity.class);
                startActivity(intent);
            }

        });



        // when click on list
        clickablelist = (ListView) findViewById(R.id.provider_assigned_task_list);
        clickablelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent taskinfo = new Intent(RequesterMainActivity.this, RequestorShowTaskDetailActivity.class);
                Task task = taskList.get(index);
                taskinfo.putExtra("task", (Serializable)task);
                startActivity(taskinfo);
            }
        });



    }
}

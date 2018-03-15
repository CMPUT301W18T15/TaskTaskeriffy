package com.example.heesoo.myapplication;

// Harry

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

    private ArrayList<Task> taskList; // the list of tasks that requester posted
    private ArrayList<Task> allTasks;
    private ArrayAdapter<Task> taskAdapter;
    private ListView clickableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_main);


        //add new task button
        addNewTaskButton = (Button) findViewById(R.id.add_new_task_button);
        addNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequesterMainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });


        // show my Account Button
        myAccountButton = (Button) findViewById(R.id.my_account_button);
        myAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                Intent intent = new Intent(RequesterMainActivity.this, ViewProfileActivity.class);
                startActivity(intent);
            }
        });




        // view requester's bidded button
        showBiddedButton = (Button) findViewById(R.id.show_bidded_button);
        showBiddedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                Intent intent = new Intent(RequesterMainActivity.this, RequesterBiddedTasksListActivity.class);
                startActivity(intent);
            }

        });

        // requestor show assigned task button
        showAssignedTaskButton = (Button) findViewById(R.id.show_assigned_task_button);
        showAssignedTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                Intent intent = new Intent(RequesterMainActivity.this, RequestorAssignedTaskListActivity.class);
                startActivity(intent);
            }

        });



        // when click on list
        clickableList = (ListView) findViewById(R.id.requester_posted_task_list);
        clickableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent taskinfo = new Intent(RequesterMainActivity.this, RequestorShowTaskDetailActivity.class);
                Task task = taskList.get(index);
                taskinfo.putExtra("task", (Serializable)task);
                startActivity(taskinfo);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        //@todo need to pull all the tasks posted by this requester
        // will return an arraylist of tasks,
        // @todo get user's name
        //String thisRequesterName = getCurrentUser
        taskList = new ArrayList<Task>();
        allTasks = new ArrayList<Task>();

        // dummy tasks:
        Task dTask1 = new Task("Requestname1","dTaskNameshouldnotappear" ,"dTask1Description","Assigned");
        Task dTask12 = new Task("Requestname2","dTaskName12" ,"dTask12Description","Assigned");
        Task dTask13 = new Task("Requestname2","dTaskName13" ,"dTask13Description","Requested");
        Task dTask123 = new Task("Requestname2","dTaskName123" ,"dTask123Description","Bidded");
        // dummy user's name

        String thisRequesterName = "Requestname2";

        // TODO use elastic search to get the Task Table
        // I suppose the name of arraylist that get from database is allTasks

        allTasks.add(dTask1);
        allTasks.add(dTask12);
        allTasks.add(dTask13);
        allTasks.add(dTask123);

        for (Task task:allTasks){
            if (thisRequesterName == task.getUserName()){
                taskList.add(task);
            }
        }


        ArrayList<String> requesterPostTasksNames = new ArrayList<String>();

        for(int i = 0; i < taskList.size(); i++){
            requesterPostTasksNames.add("Name: "+taskList.get(i).getTaskName()+" Status: " + taskList.get(i).getStatus());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, requesterPostTasksNames);
        clickableList.setAdapter(adapter);

    }
}

package com.example.heesoo.myapplication;

// Harry

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
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
    private ListView clickableList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_main);

        //show my History button
        Button myHistoryButton = (Button) findViewById(R.id.my_history_button);
//        myHistoryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ProviderMainActivity.this, ViewProviderHistoryActivity.class);
//                startActivity(intent);
//            }
//        });


        // show my Account Button
        Button myAccountButton = (Button) findViewById(R.id.my_account_button);
        myAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                Intent intent = new Intent(ProviderMainActivity.this, ViewProfileActivity.class);
                startActivity(intent);
            }
        });


        //find Nearby task button
        // @TODO need user's geo location to get nearby tasks
        // Button findNearbyTaskButton


        // view provider's bidded list button
        Button viewBiddedListButton = (Button) findViewById(R.id.view_bidded_list_button);
//        viewBiddedListButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //setResult(RESULT_OK);
//                Intent intent = new Intent(ProviderMainActivity.this, ViewBidListActivity.class);
//                startActivity(intent);
//            }
//        });


        // provider search new task button

        Button searchNewTaskButton = (Button) findViewById(R.id.search_new_task_button);
//        searchNewTaskButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //setResult(RESULT_OK);
//                Intent intent = new Intent(ProviderMainActivity.this, ProviderFindNewTaskActivity.class);
//                startActivity(intent);
//            }
//        });




        // when click on list
        clickableList = (ListView) findViewById(R.id.provider_assigned_task_list);
        clickableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent taskinfo = new Intent(ProviderMainActivity.this, ProviderViewAssignedTaskDetail.class);
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

        // dummy tasks:
        Task dTask1 = new Task("Requestname1","dTask" ,"dTask1Description","Assigned");
        Task dTask12 = new Task("Requestname2","dTaskName12" ,"dTask12Description","Assigned");
        Task dTask13 = new Task("Requestname2","dTaskName13" ,"dTask13Description","Requested");
        Task dTask123 = new Task("Requestname2","dTaskName123Nameshouldnotappear" ,"dTask123Description","Assigned");
        // dummy user's name

        dTask1.setTaskProvider("Requestname3");
        dTask12.setTaskProvider("Requestname3");
        dTask13.setTaskProvider("Requestname3");


        String thisTaskProvider = "Requestname3";
        String status ="Assigned";

        taskList.add(dTask1);
        taskList.add(dTask12);
        taskList.add(dTask13);
        taskList.add(dTask123);



        ArrayList<String> requesterPostTasksNames = new ArrayList<String>();

        for(int i = 0; i < taskList.size(); i++){
            if(thisTaskProvider == taskList.get(i).getTaskProvider() && status == taskList.get(i).getStatus() ){
                requesterPostTasksNames.add("Name: "+taskList.get(i).getTaskName()+" Status: " + taskList.get(i).getStatus());
            }
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, requesterPostTasksNames);
        clickableList.setAdapter(adapter);

    }

}

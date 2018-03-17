package com.example.heesoo.myapplication;

// Harry

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.Entities.Task;


import java.util.ArrayList;

public class ProviderMainActivity extends AppCompatActivity {

    private Button myHistoryButton;
    private Button myAccountButton;
    private Button findNearbyTaskButton;
    private Button viewBiddedListButton;
    private Button searchNewTaskButton;
    private ListView myAssignedTasklist;
    private TextView taskLabel;
    private ArrayList<Task> tempTaskList;
    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> taskAdapter;
    private ListView clickableList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_main);
        searchNewTaskButton = findViewById(R.id.search_new_task_button);
        viewBiddedListButton = findViewById(R.id.view_bidded_list_button);

        //show my History button
//        Button myHistoryButton = findViewById(R.id.my_history_button);
//        myHistoryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ProviderMainActivity.this, ProviderViewBiddedTaskList.class);
//                startActivity(intent);
//            }
//        });


        // show my Account Button
        Button myAccountButton = findViewById(R.id.my_account_button);
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

        viewBiddedListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(getApplicationContext(), ProviderViewBiddedTaskList.class);
                startActivity(intent);
            }
        });


        // provider search new task button

        searchNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(getApplicationContext(), ProviderFindNewTaskActivity.class);
                startActivity(intent);
            }
        });




        // when click on list
        clickableList = findViewById(R.id.provider_assigned_task_list);
        clickableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent taskinfo = new Intent(ProviderMainActivity.this, ProviderViewAssignedTaskDetail.class);
                Task task = taskList.get(index);
                taskinfo.putExtra("task", task);
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

        tempTaskList = new ArrayList<Task>();
        taskList = new ArrayList<Task>();

        // dummy tasks:
//        Task dTask1 = new Task("Requestname1","dTask" ,"dTask1Description","Assigned");
//        Task dTask12 = new Task("Requestname2","dTaskName12" ,"dTask12Description","Assigned");
//        Task dTask13 = new Task("Requestname2","dTaskName13" ,"dTask13Description","Requested");
//        Task dTask123 = new Task("Requestname2","dTaskName123Nameshouldnotappear" ,"dTask123Description","Assigned");
//
//        dTask1.setTaskProvider("RiyaRiya");
//        dTask12.setTaskProvider("Requestname3");
//        dTask13.setTaskProvider("RiyaRiya");
//
//        tempTaskList.add(dTask1);
//        tempTaskList.add(dTask12);
//        tempTaskList.add(dTask13);
//        tempTaskList.add(dTask123);



        ElasticSearchTaskController.GetAllTasks getAllTasks = new ElasticSearchTaskController.GetAllTasks();
        getAllTasks.execute("");

        try {
            tempTaskList = getAllTasks.get();
        }
        catch (Exception e) {
            Log.i("Error", "The request for tweets failed in onStart");
        }

        ArrayList<String> tasksNames = new ArrayList<String>();


        Log.d("HEREEEE", MyApplication.getCurrentUser().getUsername());
        for(int i = 0; i < tempTaskList.size(); i++){
            Log.d("HEREEEE", tempTaskList.get(i).getTaskProvider());
            if ( tempTaskList.get(i).getTaskProvider().equals(MyApplication.getCurrentUser().getUsername())
                    && tempTaskList.get(i).getStatus().equals("Assigned")) {
                Log.d("HEREEEE", "IHATE");
                taskList.add(tempTaskList.get(i));
                tasksNames.add("Name: "+tempTaskList.get(i).getTaskName()+" Status: " + tempTaskList.get(i).getStatus());

            }
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasksNames);
        clickableList.setAdapter(adapter);

    }

}

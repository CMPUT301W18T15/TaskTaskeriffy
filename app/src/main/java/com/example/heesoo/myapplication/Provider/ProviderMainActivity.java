package com.example.heesoo.myapplication.Provider;

import android.app.SearchManager;
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
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.Profile.ViewProfileActivity;


import java.util.ArrayList;

/*
This activity serves as the main dashboard for the provider mode and shows a list of tasks that are assigned to the provider.
It also contains buttons to view profile, view tasks that the provider has bidded on and find new tasks to bid on.
 */

public class ProviderMainActivity extends AppCompatActivity {

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

        // show my Account Button
        Button myAccountButton = findViewById(R.id.my_account_button);
        myAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProviderMainActivity.this, ViewProfileActivity.class);
                startActivity(intent);
            }
        });

        findNearbyTaskButton = findViewById(R.id.find_nearby_task_button);
        findNearbyTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProviderMainActivity.this, ProviderFindNearbyTasksActivity.class);
                startActivity(intent);
            }
        });


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

        tempTaskList = new ArrayList<Task>();
        taskList = new ArrayList<Task>();

        ElasticSearchTaskController.GetAllTasks getAllTasks = new ElasticSearchTaskController.GetAllTasks();
        getAllTasks.execute("");

        try {
            tempTaskList = getAllTasks.get();
        }
        catch (Exception e) {
            Log.i("Error", "The request for tweets failed in onStart");
        }

        ArrayList<String> tasksNames = new ArrayList<String>();


        for(int i = 0; i < tempTaskList.size(); i++){
            if ( tempTaskList.get(i).getStatus().equals("Assigned") && tempTaskList.get(i).getTaskProvider().equals(SetCurrentUser.getCurrentUser().getUsername())) {
                taskList.add(tempTaskList.get(i));
                tasksNames.add("Name: "+tempTaskList.get(i).getTaskName()+" Status: " + tempTaskList.get(i).getStatus());

            }
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasksNames);
        clickableList.setAdapter(adapter);

    }
}
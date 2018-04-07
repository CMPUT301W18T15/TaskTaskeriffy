package com.example.heesoo.myapplication.task_provider_activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.entities.Bid;
import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.entities.TaskList;
import com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity;
import com.example.heesoo.myapplication.profile_activities.MyStatisticsActivity;
import com.example.heesoo.myapplication.profile_activities.ViewProfileActivity;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewAssignedTasksActivity;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewBiddedTasksActivity;
import com.example.heesoo.myapplication.shared_preferences.SetPublicCurrentUser;
import com.example.heesoo.myapplication.R;

import java.util.ArrayList;

import static com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity.checkNetwork;

/**
 * Created by manuelakm on 2018-03-15.
 */

/*
This activity is navigated to when the provider clicks on the button to view bidded list
in the TaskProviderViewAssignedTasksActivity. This activity shows the list of tasks that the provider has bidded on. By clicking an
item in the list, the provider may view the details of the task and it's lowest bid.
 */

public class TaskProviderViewBiddedTaskListActivity extends AppCompatActivity {

    private TaskList tempTaskList;
    private TaskList taskList;
    private ListView listView;
    private Task selectedTask;
    private ArrayAdapter<String> adapter;

    private DrawerLayout drawerLayout;
    private TextView noTasksMessage;

    private NavigationView navigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);
        noTasksMessage = findViewById(R.id.noTasksMessage);


        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);

        listView = findViewById(R.id.tasksListView);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //final Object t = listView.getItemAtPosition(i);
                //selectedTask = (Task) t;
                selectedTask = taskList.getTask(i);


                AlertDialog.Builder popUp = new AlertDialog.Builder(TaskProviderViewBiddedTaskListActivity.this);
                popUp.setMessage("Would you like to see details about '" + selectedTask.getTaskName() + "' ?");
                popUp.setCancelable(true);


                popUp.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                setResult(RESULT_OK);
                                Intent intent = new Intent(getApplicationContext(), TaskProviderViewAssignedTaskDetailActivity.class);
                                intent.putExtra("task", selectedTask);
                                startActivity(intent);
                            }
                        });

                popUp.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = popUp.create();
                alert11.show();

            }
        });

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        if ( menuItem.getItemId() == R.id.nav_myAccount ) {
                            startActivity(new Intent(getApplicationContext(), ViewProfileActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myStatistics ) {
                            startActivity(new Intent(getApplicationContext(), MyStatisticsActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myTasks ) {
                            startActivity(new Intent(getApplicationContext(), ViewRequestedTasksActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myRequestedBiddedTasks ) {
                            startActivity(new Intent(getApplicationContext(), TaskRequesterViewBiddedTasksActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myRequestedAssignedTasks ) {
                            startActivity(new Intent(getApplicationContext(), TaskRequesterViewAssignedTasksActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_findNewTasks ) {
                            startActivity(new Intent(getApplicationContext(), FindNewTaskActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myAssignedTasks ) {
                            startActivity(new Intent(getApplicationContext(), TaskProviderViewAssignedTasksActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myBiddedTasks ) {
                            startActivity(new Intent(getApplicationContext(), TaskProviderViewBiddedTaskListActivity.class));
                        }
                        return true;
                    }
                });
    }

    // Objects.equals() is only accepted in new Api's
    @SuppressLint("NewApi")
    @Override
    protected void onStart() {
        super.onStart();
        noTasksMessage.setVisibility(View.GONE);


        checkNetwork(this);
        tempTaskList = new TaskList();
        taskList = new TaskList();
        ArrayList<String> displayedTasks = new ArrayList<>();

        ElasticSearchTaskController.GetAllTasks getAllTasks = new ElasticSearchTaskController.GetAllTasks();
        getAllTasks.execute("");

        try{
            tempTaskList = getAllTasks.get();
        } catch(Exception e){
            Log.i("ERROR", "Failed to pull tasks from Database");
        }


        for(int i = 0; i < tempTaskList.getSize(); i++){
            if (tempTaskList.getTask(i).getStatus().equals("Bidded")) {

                for (int j = 0; j < tempTaskList.getTask(i).getBids().size(); j++) {
                    Bid pbid = tempTaskList.getTask(i).getBids().get(j);

                    if (pbid.getTaskProvider().equals(SetPublicCurrentUser.getCurrentUser().getUsername())){
                        taskList.addTask(tempTaskList.getTask(i));
                        displayedTasks.add("Name: "+tempTaskList.getTask(i).getTaskName()+" Status: " + tempTaskList.getTask(i).getStatus());
                    }
                }
            }
        }
        if (taskList.getSize() == 0){
            noTasksMessage.setVisibility(View.VISIBLE);
            noTasksMessage.setText("You have not bidded on any tasks!");
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, displayedTasks);
        listView.setAdapter(adapter);

    }

}
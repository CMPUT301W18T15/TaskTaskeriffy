package com.example.heesoo.myapplication.task_provider_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.entities.TaskList;
import com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity;
import com.example.heesoo.myapplication.login_activity.MainActivity;
import com.example.heesoo.myapplication.profile_activities.MyStatisticsActivity;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewAssignedTasksActivity;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewBiddedTasksActivity;
import com.example.heesoo.myapplication.shared_preferences.SetPublicCurrentUser;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.profile_activities.ViewProfileActivity;


import java.util.ArrayList;

import static com.example.heesoo.myapplication.login_activity.MainActivity.needSync;
import static com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity.checkNetwork;

/*
This activity serves as the main dashboard for the provider mode and shows a list of tasks that are assigned to the provider.
It also contains buttons to view profile, view tasks that the provider has bidded on and find new tasks to bid on.
 */

public class TaskProviderViewAssignedTasksActivity extends AppCompatActivity {

    private ListView myAssignedTasklist;
    private TextView taskLabel;
    private TaskList tempTaskList;
    private TaskList taskList;
    private ArrayAdapter<Task> taskAdapter;
    private ListView clickableList;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView noTasksMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);
        noTasksMessage = findViewById(R.id.noTasksMessage);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // when click on list
        clickableList = findViewById(R.id.tasksListView);
        clickableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent taskinfo = new Intent(TaskProviderViewAssignedTasksActivity.this, TaskProviderViewAssignedTaskDetailActivity.class);
                Task task = taskList.getTask(index);
                taskinfo.putExtra("task", task);
                startActivity(taskinfo);
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

    @Override
    protected void onStart() {
        super.onStart();
        noTasksMessage.setVisibility(View.GONE);


        tempTaskList = new TaskList();
        taskList = new TaskList();

        // offline behavior
        // sync
        if (checkNetwork(this)) {
            if (needSync == true){
                Toast.makeText(getApplicationContext(),"The database is syncing", Toast.LENGTH_SHORT).show();
                MainActivity.user.sync();
                MainActivity.needSync = false;
            }
        }else{
            MainActivity.needSync = true;
        }

        if(checkNetwork(this)){
            ElasticSearchTaskController.GetAllTasks getAllTasks = new ElasticSearchTaskController.GetAllTasks();
            getAllTasks.execute("");
            try {
                tempTaskList = getAllTasks.get();
            }
            catch (Exception e) {
                Log.i("Error", "The request for tweets failed in onStart");
            }
        }else {
            tempTaskList = MainActivity.user.getProviderTasks();
        }
        ArrayList<String> tasksNames = new ArrayList<String>();


        for(int i = 0; i < tempTaskList.getSize(); i++){
            if ( tempTaskList.getTask(i).getStatus().equals("Assigned") && tempTaskList.getTask(i).getTaskProvider().equals(SetPublicCurrentUser.getCurrentUser().getUsername())) {
                taskList.addTask(tempTaskList.getTask(i));
                tasksNames.add("Name: "+tempTaskList.getTask(i).getTaskName()+" Status: " + tempTaskList.getTask(i).getStatus());

            }
        }
        if (taskList.getSize() == 0){
            noTasksMessage.setVisibility(View.VISIBLE);
            noTasksMessage.setText("You are not assigned to any tasks!");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasksNames);
        clickableList.setAdapter(adapter);

    }
}
package com.example.heesoo.myapplication.task_requester_activities;

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

import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.entities.TaskList;
import com.example.heesoo.myapplication.login_activity.MainActivity;
import com.example.heesoo.myapplication.profile_activities.MyStatisticsActivity;
import com.example.heesoo.myapplication.profile_activities.ViewProfileActivity;
import com.example.heesoo.myapplication.task_provider_activities.FindNewTaskActivity;
import com.example.heesoo.myapplication.task_provider_activities.TaskProviderViewAssignedTasksActivity;
import com.example.heesoo.myapplication.task_provider_activities.TaskProviderViewBiddedTaskListActivity;
import com.example.heesoo.myapplication.shared_preferences.SetPublicCurrentUser;
import com.example.heesoo.myapplication.R;


import java.util.ArrayList;

import static com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity.checkNetwork;


/*
This activity serves to show the task requester a list of the tasks requested by them that are bidded on by providers.
This activity is reached through the show bidded task list button on the dashboard (RequesterMainActivity).
 */

public class TaskRequesterViewBiddedTasksActivity extends AppCompatActivity{

    private TaskList taskList;
    private ListView clickableList;
    private TaskList allTasks;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView noTasksMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
        noTasksMessage = findViewById(R.id.noTasksMessage);

        clickableList = findViewById(R.id.tasksListView);
        clickableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent taskinfo = new Intent(TaskRequesterViewBiddedTasksActivity.this, ShowTaskDetailActivity.class);
                Task task = taskList.getTask(index);
                taskinfo.putExtra("task", task);
                startActivity(taskinfo);
            }
        });

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();

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
                        if ( menuItem.getItemId() == R.id.logout ) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        return true;
                    }
                });


    }


    @Override
    protected void onStart() {
        super.onStart();
        noTasksMessage.setVisibility(View.GONE);
        taskList = new TaskList();
        allTasks = new TaskList();
        checkNetwork(this);
        ArrayList<String> requesterBiddedTasksNames = new ArrayList<String>();

        ElasticSearchTaskController.GetAllTasks getAllTasks = new ElasticSearchTaskController.GetAllTasks();
        getAllTasks.execute("");

        try {
            allTasks = getAllTasks.get();
        }
        catch (Exception e) {
            Log.i("Error", "The request for tasks failed in onStart");
        }

        for (int i = 0; i < allTasks.getSize(); i++) {
            Task task = allTasks.getTask(i);

            if (SetPublicCurrentUser.getCurrentUser().getUsername().equals(task.getTaskRequester()) && task.getStatus().equals("Bidded")){
                taskList.addTask(task);
                requesterBiddedTasksNames.add("Name: "+task.getTaskName()+" Status: " + task.getStatus());

            }
        }

        if (taskList.getSize() == 0){
            noTasksMessage.setVisibility(View.VISIBLE);
            noTasksMessage.setText("None of your tasks are currently bidded on!");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, requesterBiddedTasksNames);
        clickableList.setAdapter(adapter);

    }

}

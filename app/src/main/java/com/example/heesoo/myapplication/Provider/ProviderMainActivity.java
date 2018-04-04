package com.example.heesoo.myapplication.Provider;

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

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.MainTaskActivity;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.Profile.MyStatsActivity;
import com.example.heesoo.myapplication.Requester.RequesterAssignedTaskListActivity;
import com.example.heesoo.myapplication.Requester.RequesterBiddedTasksListActivity;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.Profile.ViewProfileActivity;


import java.util.ArrayList;

import static com.example.heesoo.myapplication.Main_LogIn.MainActivity.needSync;
import static com.example.heesoo.myapplication.Requester.RequesterMainActivity.checkNetwork;

/*
This activity serves as the main dashboard for the provider mode and shows a list of tasks that are assigned to the provider.
It also contains buttons to view profile, view tasks that the provider has bidded on and find new tasks to bid on.
 */

public class ProviderMainActivity extends AppCompatActivity {

    private ListView myAssignedTasklist;
    private TextView taskLabel;
    private ArrayList<Task> tempTaskList;
    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> taskAdapter;
    private ListView clickableList;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);;

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // when click on list
        clickableList = findViewById(R.id.tasksListView);
        clickableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent taskinfo = new Intent(ProviderMainActivity.this, ProviderViewAssignedTaskDetail.class);
                Task task = taskList.get(index);
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
                            startActivity(new Intent(getApplicationContext(), MyStatsActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myTasks ) {
                            startActivity(new Intent(getApplicationContext(), MainTaskActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myRequestedBiddedTasks ) {
                            startActivity(new Intent(getApplicationContext(), RequesterBiddedTasksListActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myRequestedAssignedTasks ) {
                            startActivity(new Intent(getApplicationContext(), RequesterAssignedTaskListActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_findNewTasks ) {
                            startActivity(new Intent(getApplicationContext(), ProviderFindNewTaskActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myAssignedTasks ) {
                            startActivity(new Intent(getApplicationContext(), ProviderMainActivity.class));
                        }
                        if ( menuItem.getItemId() == R.id.nav_myBiddedTasks ) {
                            startActivity(new Intent(getApplicationContext(), ProviderViewBiddedTaskList.class));
                        }
                        return true;
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        tempTaskList = new ArrayList<Task>();
        taskList = new ArrayList<Task>();

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
package com.example.heesoo.myapplication.Provider;

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


import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.MainTaskActivity;
import com.example.heesoo.myapplication.Profile.ViewProfileActivity;
import com.example.heesoo.myapplication.Requester.RequesterAssignedTaskListActivity;
import com.example.heesoo.myapplication.Requester.RequesterBiddedTasksListActivity;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;
import com.example.heesoo.myapplication.R;

import java.util.ArrayList;

import static com.example.heesoo.myapplication.Requester.RequesterMainActivity.checkNetwork;

/**
 * Created by manuelakm on 2018-03-15.
 */

/*
This activity is navigated to when the provider clicks on the button to view bidded list
in the ProviderMainActivity. This activity shows the list of tasks that the provider has bidded on. By clicking an
item in the list, the provider may view the details of the task and it's lowest bid.
 */

public class ProviderViewBiddedTaskList extends AppCompatActivity {

    private ArrayList<Task> tempTaskList;
    private ArrayList<Task> taskList;
    private ListView listView;
    private Task selectedTask;
    private ArrayAdapter<Task> adapter;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        listView = findViewById(R.id.tasksListView);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Object t = listView.getItemAtPosition(i);
                selectedTask = (Task) t;

                AlertDialog.Builder popUp = new AlertDialog.Builder(ProviderViewBiddedTaskList.this);
                popUp.setMessage("Would you like to see details about '" + selectedTask.getTaskName() + "' ?");
                popUp.setCancelable(true);


                popUp.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                setResult(RESULT_OK);
                                Intent intent = new Intent(getApplicationContext(), ProviderViewAssignedTaskDetail.class);
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

    // Objects.equals() is only accepted in new Api's
    @SuppressLint("NewApi")
    @Override
    protected void onStart() {
        super.onStart();

        checkNetwork(this);
        tempTaskList = new ArrayList<Task>();
        taskList = new ArrayList<Task>();

        ElasticSearchTaskController.GetAllTasks getAllTasks = new ElasticSearchTaskController.GetAllTasks();
        getAllTasks.execute("");

        try{
            tempTaskList = getAllTasks.get();
        } catch(Exception e){
            Log.i("ERROR", "Failed to pull tasks from Database");
        }


        for(int i = 0; i < tempTaskList.size(); i++){
            if (tempTaskList.get(i).getStatus().equals("Bidded")) {
                for (Bid pbid:tempTaskList.get(i).getBids()){
                    if (pbid.getTaskProvider().equals(SetCurrentUser.getCurrentUser().getUsername())){
                        taskList.add(tempTaskList.get(i));
                    }
                }
            }
        }

        adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, android.R.id.text1, taskList);
        listView.setAdapter(adapter);

    }

}
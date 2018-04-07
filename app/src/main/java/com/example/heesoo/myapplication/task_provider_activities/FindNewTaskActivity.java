package com.example.heesoo.myapplication.task_provider_activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

/**
 * Created by manuelakm on 2018-03-14.
 */

/*
This activity is navigated to when the provider wants to find a new task that they can bid on. It shows a list view of
 all the tasks that are in a requested or bidded state which are created by other users. BY clicking on a list item, the
 provider can navigate to a particular and see it's details.
 */

public class FindNewTaskActivity extends AppCompatActivity {
    private TaskList tempTaskList;
    private TaskList taskList;
    private ListView listView;
    private Task selectedTask;
    private ArrayList<String> searchResults;
    private ArrayAdapter<String> adapter;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Button findNearbyTasks;
    private TextView noTasksMessage;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);
        noTasksMessage = findViewById(R.id.noTasksMessage);


        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);

        findNearbyTasks = findViewById(R.id.nearbyTaskButton);
        findNearbyTasks.setVisibility(View.VISIBLE);
        findNearbyTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindNewTaskActivity.this, FindNearbyTasksActivity.class));
            }
        });

        listView = findViewById(R.id.tasksListView);
        listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //final Object t = listView.getItemAtPosition(i);
                //selectedTask = (Task) t;
                selectedTask = taskList.getTask(i);

                AlertDialog.Builder popUp = new AlertDialog.Builder(FindNewTaskActivity.this);
                popUp.setMessage("Would you like to see details about '"  + selectedTask.getTaskName() + "' ?");
                popUp.setCancelable(true);


                popUp.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                setResult(RESULT_OK);
                                Intent intent = new Intent(getApplicationContext(), PlaceBidOnTaskActivity.class);
                                intent.putExtra("TaskToBidOn", selectedTask);
                                startActivityForResult(intent, 1);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                TaskList tempList = new TaskList();
                searchResults = new ArrayList<String>();

                for(int i = 0; i < taskList.getSize(); i++) {

                    Task temp = taskList.getTask(i);

                    if (temp.getTaskName().toLowerCase().contains(newText.toLowerCase())) {
                        tempList.addTask(temp);
                        searchResults.add("Name: " + temp.getTaskName() +" \n Status: " + temp.getStatus());
                    }
                }

                adapter = new ArrayAdapter<>(FindNewTaskActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, searchResults);
                listView.setAdapter(adapter);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        noTasksMessage.setVisibility(View.GONE);

        tempTaskList = new TaskList();
        taskList = new TaskList();

        ElasticSearchTaskController.GetAllTasks getAllTasks = new ElasticSearchTaskController.GetAllTasks();
        getAllTasks.execute("");

        try{
            tempTaskList = getAllTasks.get();
        }
        catch(Exception e){
            Log.i("ERROR", "Failed to pull tasks from Database");
        }

        ArrayList<String> displayedTasks = new ArrayList<>();
        tempTaskList = tempTaskList.getAvailableTasks();


        for(int i = 0; i < tempTaskList.getSize(); i++){
            if ( !(tempTaskList.getTask(i).getTaskRequester().equals( SetPublicCurrentUser.getCurrentUser().getUsername())) ) {
                taskList.addTask(tempTaskList.getTask(i));
                displayedTasks.add("Name: " + tempTaskList.getTask(i).getTaskName() +" \n Status: " + tempTaskList.getTask(i).getStatus());
            }
        }
        if (taskList.getSize() == 0) {
            noTasksMessage.setVisibility(View.VISIBLE);
            noTasksMessage.setText("No Tasks available!");
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, displayedTasks);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {

        if (i == null) {
        }

        else if (requestCode == 1) {

            Bid bidPlaced = (Bid) i.getSerializableExtra("bidPlaced");

        }

    }
}
package com.example.heesoo.myapplication.Provider;

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

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.MainTaskActivity;
import com.example.heesoo.myapplication.Profile.MyStatsActivity;
import com.example.heesoo.myapplication.Profile.ViewProfileActivity;
import com.example.heesoo.myapplication.Requester.RequesterAssignedTaskListActivity;
import com.example.heesoo.myapplication.Requester.RequesterBiddedTasksListActivity;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;
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

public class ProviderFindNewTaskActivity extends AppCompatActivity {
    private ArrayList<Task> tempTaskList;
    private ArrayList<Task> taskList;
    private ListView listView;
    private Task selectedTask;
    private ArrayAdapter<Task> adapter;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Button findNearbyTasks;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        findNearbyTasks = findViewById(R.id.nearbyTaskButton);
        findNearbyTasks.setVisibility(View.VISIBLE);
        findNearbyTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProviderFindNewTaskActivity.this, ProviderFindNearbyTasksActivity.class));
            }
        });

        listView = findViewById(R.id.tasksListView);
        listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Object t = listView.getItemAtPosition(i);
                selectedTask = (Task) t;

                AlertDialog.Builder popUp = new AlertDialog.Builder(ProviderFindNewTaskActivity.this);
                popUp.setMessage("Would you like to see details about '"  + selectedTask.getTaskName() + "' ?");
                popUp.setCancelable(true);


                popUp.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                setResult(RESULT_OK);
                                Intent intent = new Intent(getApplicationContext(), ProviderPlaceBidActivity.class);
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

                ArrayList<Task> templist = new ArrayList<Task>();

                for(Task temp : taskList){
                    if (temp.getTaskName().toLowerCase().contains(newText.toLowerCase())) {
                        templist.add(temp);
                    }
                }
                adapter = new ArrayAdapter<Task>(ProviderFindNewTaskActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, templist);
                //taskAdapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
            if (!(tempTaskList.get(i).getUserName().equals( SetCurrentUser.getCurrentUser().getUsername()))
                    && !tempTaskList.get(i).getStatus().equals("Assigned") && !tempTaskList.get(i).getStatus().equals("Done")) {
                taskList.add(tempTaskList.get(i));
            }
        }

        adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, android.R.id.text1, taskList);
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

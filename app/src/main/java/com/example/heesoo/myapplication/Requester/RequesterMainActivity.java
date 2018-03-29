package com.example.heesoo.myapplication.Requester;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
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
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.Profile.ViewProfileActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;

import java.util.ArrayList;

/*
This activity serves as the main dashboard for the requestor mode and shows a list of tasks that were added by the requester
along with their statuses. It also contains buttons to view profile, add new task, view tasks that have been bidded on and
tasks that are assigned to a provider.
 */

public class RequesterMainActivity extends AppCompatActivity {

    private Button addNewTaskButton;
    private Button myAccountButton;
    private Button showBiddedButton;
    private Button showAssignedTaskButton;

    private ArrayList<Task> taskList; // the list of tasks that requester posted
    private ArrayList<Task> allTasks;
    private ArrayAdapter<Task> taskAdapter;
    private ListView clickableList;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_main);

        //add new task button
        addNewTaskButton = findViewById(R.id.add_new_task_button);
        addNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequesterMainActivity.this, RequesterAddTaskActivity.class);
                startActivity(intent);
            }
        });


        // show my Account Button
        myAccountButton = findViewById(R.id.my_account_button);
        myAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequesterMainActivity.this, ViewProfileActivity.class);
                startActivity(intent);
            }
        });


        // view requester's bidded button
        showBiddedButton = findViewById(R.id.show_bidded_button);
        showBiddedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequesterMainActivity.this, RequesterBiddedTasksListActivity.class);
                startActivity(intent);
            }

        });

        // requestor show assigned task button
        showAssignedTaskButton = findViewById(R.id.show_assigned_task_button);
        showAssignedTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequesterMainActivity.this, RequesterAssignedTaskListActivity.class);
                startActivity(intent);
            }

        });


        // when click on list
        clickableList = findViewById(R.id.requester_posted_task_list);
        clickableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent taskinfo = new Intent(RequesterMainActivity.this, RequesterShowTaskDetailActivity.class);
                task = taskList.get(index);
                taskinfo.putExtra("task", task);
                startActivityForResult(taskinfo, 2);
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
                taskAdapter = new ArrayAdapter<Task>(RequesterMainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, templist);
                //taskAdapter.notifyDataSetChanged();
                clickableList.setAdapter(taskAdapter);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //@todo need to pull all the tasks posted by this requester
        // will return an arraylist of tasks,
        // @todo get user's name
        //String thisRequesterName = getCurrentUser
        taskList = new ArrayList<Task>();
        allTasks = new ArrayList<Task>();

        // TODO use elastic search to get the Task Table

        Log.d("REQUESTCODE", "UPDATING LIST FROM DATABASE");
        taskList = getUserTasksFromDatabase();
        taskAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, android.R.id.text1, taskList);
        taskAdapter.notifyDataSetChanged();
        //clickableList.invalidateViews();
        clickableList.setAdapter(taskAdapter);

    }

    protected ArrayList<Task> getUserTasksFromDatabase() {
        ElasticSearchTaskController.GetAllTasks getAllTasks = new ElasticSearchTaskController.GetAllTasks();
        getAllTasks.execute("");
        taskList.clear();

        try {
            allTasks = getAllTasks.get();
        }
        catch (Exception e) {
            Log.i("Error", "The request for tweets failed in onStart");
        }

        ArrayList<String> requesterPostTasksNames = new ArrayList<String>();

        for (Task task : allTasks){
            if (SetCurrentUser.getCurrentUser().getUsername().equals(task.getUserName())){
                Log.d("REQUESTCODE", task.getTaskName());
                taskList.add(task);
                requesterPostTasksNames.add("Name: "+task.getTaskName()+" Status: " + task.getStatus());
            }
        }
        return taskList;
    }
}
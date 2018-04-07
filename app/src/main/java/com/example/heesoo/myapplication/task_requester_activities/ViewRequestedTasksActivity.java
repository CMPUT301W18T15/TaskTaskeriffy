package com.example.heesoo.myapplication.task_requester_activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.entities.TaskList;
import com.example.heesoo.myapplication.login_activity.MainActivity;
import com.example.heesoo.myapplication.profile_activities.MyStatisticsActivity;
import com.example.heesoo.myapplication.profile_activities.ViewProfileActivity;
import com.example.heesoo.myapplication.task_provider_activities.FindNewTaskActivity;
import com.example.heesoo.myapplication.task_provider_activities.TaskProviderViewAssignedTasksActivity;
import com.example.heesoo.myapplication.task_provider_activities.TaskProviderViewBiddedTaskListActivity;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.shared_preferences.SetPublicCurrentUser;

import java.util.ArrayList;

import static com.example.heesoo.myapplication.login_activity.MainActivity.needSync;

/**
 * Created by manuelakm on 2018-03-29.
 */

public class ViewRequestedTasksActivity extends AppCompatActivity {

    private TaskList allTasks;
    private TaskList taskList;
    private ArrayAdapter<String> taskAdapter;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ListView myTasks;
    private Button addTask;
    private Task task;
    private TextView noTasksMessage;


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Please Use Logout Button", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        myTasks = findViewById(R.id.tasksListView);
        addTask = findViewById(R.id.addTaskButton);
        addTask.setVisibility(View.VISIBLE);
        noTasksMessage = findViewById(R.id.noTasksMessage);


        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewRequestedTasksActivity.this, AddTaskActivity.class));
            }
        });

        // when click on list
        myTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent taskInfo = new Intent(ViewRequestedTasksActivity.this, ShowTaskDetailActivity.class);
                task = taskList.getTask(index);
                taskInfo.putExtra("task", task);
                startActivityForResult(taskInfo, 2);
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
        ArrayList<String> displayedTasks = new ArrayList<>();

        if (checkNetwork(this)) {
            if (needSync == true){
                Toast.makeText(getApplicationContext(),"The database is syncing", Toast.LENGTH_SHORT).show();
                MainActivity.user.sync();
                MainActivity.needSync = false;
            }
        }else{
            MainActivity.needSync = true;
        }

        taskList = new TaskList();
        allTasks = new TaskList();

        if(checkNetwork(this)){
            taskList = getUserTasksFromDatabase();
        }else{
            taskList =  MainActivity.user.getRequesterTasks();
        }
        if (taskList.getSize() == 0){
            noTasksMessage.setVisibility(View.VISIBLE);
            noTasksMessage.setText("You have no tasks at the moment!");
        }

        for (int i = 0; i < taskList.getSize(); i++) {
            Task task = taskList.getTask(i);
            displayedTasks.add("Name: " + task.getTaskName() + "\n Status: " + task.getStatus());
        }

        taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, displayedTasks);
        //taskAdapter.notifyDataSetChanged();

        myTasks.setAdapter(taskAdapter);

    }

    protected TaskList getUserTasksFromDatabase() {
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

        for (int i = 0; i < allTasks.getSize(); i++) {
            Task task = allTasks.getTask(i);

            if (SetPublicCurrentUser.getCurrentUser().getUsername().equals(task.getTaskRequester())){
                Log.d("REQUESTCODE", task.getTaskName());
                taskList.addTask(task);
                requesterPostTasksNames.add("Name: " + task.getTaskName() + "\n Status: " + task.getStatus());
            }
        }
        return taskList;

    }

    public static boolean checkNetwork(Context context) {
        int duration = Toast.LENGTH_SHORT;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }

        CharSequence text = "No Internet!";
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        return false;
    }

}

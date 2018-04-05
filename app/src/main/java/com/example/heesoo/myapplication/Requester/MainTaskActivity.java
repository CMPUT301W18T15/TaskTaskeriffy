package com.example.heesoo.myapplication.Requester;

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

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.Profile.MyStatsActivity;
import com.example.heesoo.myapplication.Profile.ViewProfileActivity;
import com.example.heesoo.myapplication.Provider.ProviderFindNewTaskActivity;
import com.example.heesoo.myapplication.Provider.ProviderMainActivity;
import com.example.heesoo.myapplication.Provider.ProviderViewBiddedTaskList;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;

import java.util.ArrayList;

import static com.example.heesoo.myapplication.Main_LogIn.MainActivity.needSync;

/**
 * Created by manuelakm on 2018-03-29.
 */

public class MainTaskActivity extends AppCompatActivity {

    private ArrayList<Task> allTasks;
    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> taskAdapter;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ListView myTasks;
    private Button addTask;
    private Task task;
    private TextView noTasksMessage;



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
                startActivity(new Intent(MainTaskActivity.this, RequesterAddTaskActivity.class));
            }
        });

        // when click on list
        myTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent taskInfo = new Intent(MainTaskActivity.this, RequesterShowTaskDetailActivity.class);
                task = taskList.get(index);
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
        noTasksMessage.setVisibility(View.GONE);

        //SetCurrentUser.setCurrentContext(getApplicationContext());

        if (checkNetwork(this)) {
            if (needSync == true){
                Toast.makeText(getApplicationContext(),"The database is syncing", Toast.LENGTH_SHORT).show();
                MainActivity.user.sync();
                MainActivity.needSync = false;
            }
        }else{
            MainActivity.needSync = true;
        }

        taskList = new ArrayList<Task>();
        allTasks = new ArrayList<Task>();

        if(checkNetwork(this)){
            taskList = getUserTasksFromDatabase();
        }else{
            taskList =  MainActivity.user.getRequesterTasks();
        }
        if (taskList.size() == 0){
            noTasksMessage.setVisibility(View.VISIBLE);
            noTasksMessage.setText("You have no tasks at the moment!");
        }

        taskAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, android.R.id.text1, taskList);
        //taskAdapter.notifyDataSetChanged();

        myTasks.setAdapter(taskAdapter);

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

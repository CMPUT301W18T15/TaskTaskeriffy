package com.example.heesoo.myapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchBidController;
import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.Main_LogIn.MainActivity;
import com.example.heesoo.myapplication.Profile.ViewProfileActivity;
import com.example.heesoo.myapplication.Provider.ProviderFindNewTaskActivity;
import com.example.heesoo.myapplication.Provider.ProviderMainActivity;
import com.example.heesoo.myapplication.Provider.ProviderViewBiddedTaskList;
import com.example.heesoo.myapplication.Requester.RequesterAddTaskActivity;
import com.example.heesoo.myapplication.Requester.RequesterAssignedTaskListActivity;
import com.example.heesoo.myapplication.Requester.RequesterBiddedTasksListActivity;
import com.example.heesoo.myapplication.Requester.RequesterShowTaskDetailActivity;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        myTasks = findViewById(R.id.tasksListView);
        addTask = findViewById(R.id.addTaskButton);
        addTask.setVisibility(View.VISIBLE);

        Thread thread= new Thread()
        {
            public void run()
            {

                Calendar timeStamp = Calendar.getInstance();


                while (true) {
                    Log.d("ERROR", "IN THREAD");

                    ElasticSearchBidController.GetAllBids getAllBids = new ElasticSearchBidController.GetAllBids();
                    getAllBids.execute("");
                    ArrayList<Bid> allBids = new ArrayList<Bid>();

                    try {
                        allBids = getAllBids.get();
                    } catch (Exception e) {
                        Log.d("ERROR", "Oh no! Something went wrong while accessing the database");
                    }

                    ArrayList<Bid> myBids = new ArrayList<Bid>();
                    for (Bid bid: allBids) {
                        if (bid.getTaskRequester().equals(SetCurrentUser.getCurrentUser().getUsername())) {
                            myBids.add(bid);
                            if (bid.getTimeStamp().after(timeStamp)) {
                                Log.d("ERROR", "BID HAS BEEN PLACED");
                                sendNotification("You have received a bid on the following task: " + bid.getTaskName() + "!");
                            }
                        }
                    }

                    timeStamp = Calendar.getInstance();
                    try {
                        TimeUnit.MINUTES.sleep(1);
                    }
                    catch (Exception e) {}
                }
            }
        };

        thread.start();

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

        if (checkNetwork(this)) {
            MainActivity.user.sync();
        }

        taskList = new ArrayList<Task>();
        allTasks = new ArrayList<Task>();

        taskList = getUserTasksFromDatabase();

        taskAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, android.R.id.text1, taskList);
        //taskAdapter.notifyDataSetChanged();

        myTasks.setAdapter(taskAdapter);

    }

    protected ArrayList<Task> getUserTasksFromDatabase() {

        allTasks = MainActivity.user.getRequesterTasks();

        ArrayList<String> requesterPostTasksNames = new ArrayList<String>();

        for (Task task : allTasks){
            if (SetCurrentUser.getCurrentUser().getUsername().equals(task.getUserName())) {
                Log.d("REQUESTCODE2", task.getTaskName());
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

    public void sendNotification(String msg) {

        NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder
                (getApplicationContext()).setContentTitle("TaskTaskeriffy Notification").setContentText(msg).
                setContentTitle("TaskTaskeriffy Notification").setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);


//        //SEND NOTIFICATION
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainTaskActivity.this);
//
//        final EditText editText = new EditText(MainTaskActivity.this);
//        editText.setText(msg);
//
//        alertDialogBuilder.setView(editText);
//
//        // set dialog message
//        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
//            }
//        });
//
//        // create alert dialog
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        // show it
//        alertDialog.show();
    }

}

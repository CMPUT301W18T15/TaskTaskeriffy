package com.example.heesoo.myapplication.Provider;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
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
import android.widget.ListView;


import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.Requester.RequesterMainActivity;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;
import com.example.heesoo.myapplication.R;

import java.util.ArrayList;
import java.util.Objects;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_new_tasks);

        listView = findViewById(R.id.avaliableTasksList);
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
                adapter = new ArrayAdapter<Task>(ProviderViewBiddedTaskList.this, android.R.layout.simple_list_item_1, android.R.id.text1, templist);
                //taskAdapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    // Objects.equals() is only accepted in new Api's
    @SuppressLint("NewApi")
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
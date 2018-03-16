package com.example.heesoo.myapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.heesoo.myapplication.Entities.Task;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by manuelakm on 2018-03-15.
 */

public class ViewHistoryActivity extends AppCompatActivity {

    private ArrayList<Task> tempTaskList;
    private ArrayList<Task> taskList;
    private ListView listView;
    private Task selectedTask;
    private ArrayAdapter<Task> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_new_tasks);

        listView = findViewById(R.id.historyView);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Object t = listView.getItemAtPosition(i);
                selectedTask = (Task) t;

                AlertDialog.Builder popUp = new AlertDialog.Builder(ViewHistoryActivity.this);
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

    // Objects.equals() is only accepted in new API's
    @SuppressLint("NewApi")
    @Override
    protected void onStart() {
        super.onStart();
        tempTaskList = new ArrayList<Task>();
        taskList = new ArrayList<Task>();

        // UNCOMMENT OUT WHEN ELASTICSEARCH CONTROLLER IS IMPLEMENTED

        /* ElasticSearchController.GetAllTasks getAllTasks = new ElasticSearchController().GetAllTasks();
        getAllTasks.execute();

        try{
            tempTaskList = getAllTasks.get();
        } catch(Exception e){
            Log.i("ERROR", "Failed to pull tasks from Database");
        } */

        for(int i = 0; i < tempTaskList.size(); i++){
            if ( Objects.equals(tempTaskList.get(i).getUserName(), MyApplication.getCurrentUser().getUsername())
                    && Objects.equals(tempTaskList.get(i).getStatus(), "Completed") ) {
                taskList.add(tempTaskList.get(i));
            }
        }

        Log.d("IN TASKLIST", taskList.get(0).getTaskName());
        adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, android.R.id.text1, taskList);
        listView.setAdapter(adapter);

    }

}
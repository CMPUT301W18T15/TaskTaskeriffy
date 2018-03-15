package com.example.heesoo.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manuelakm on 2018-03-14.
 */

public class FindNewTaskActivity extends AppCompatActivity {
    ArrayList<String> tasksToPrint;
    private TaskList taskList;
    private ListView listView;
    private Task selectedTask;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.avaliableTasksList);
        listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Object t = listView.getItemAtPosition(i);
                selectedTask = (Task) t;

                AlertDialog.Builder popUp = new AlertDialog.Builder(FindNewTaskActivity.this);
                popUp.setMessage("Would you like to see details about '"  + selectedTask.getTaskName() + "' ?");
                popUp.setCancelable(true);


                popUp.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                setResult(RESULT_OK);
                                //Intent intent = new Intent(getApplicationContext(), ViewTaskActivity.class);
                                //intent.putExtra("TaskToBidOn", selectedTask);
                                //startActivityForResult(intent, 1);
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
    protected void onStart() {
        super.onStart();
        taskList = new TaskList();

        // UNCOMMENT OUT WHEN ELASTICSEARCH CONTROLLER IS IMPLEMENTED

        /* ElasticSearchController.GetAllTasks getAllTasks = new ElasticSearchController().GetAllTasks();
        getAllTasks.execute();

        try{
            taskList = getAllTasks.get();
        } catch(Exception e){
            Log.i("ERROR", "Failed to pull tasks from Database");
        } */

        //String task = "";

        for(int i = 0; i < taskList.getSize(); i++){
            tasksToPrint.add("Task Name: " + taskList.getTask(i).getTaskName() + " Status: " + taskList.getTask(i).getStatus());
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasksToPrint);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {

        if (i == null) {
        }

        else if (requestCode == 1) {

            Bid bidPlaced = (Bid) i.getSerializableExtra("bidPlaced");

            //ElasticSearchController.AddBid addBid = new ElasticSearchController.AddBid();
            //addBid.execute(bidPlaced);

        }

    }
}

package com.example.heesoo.myapplication;

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

import com.example.heesoo.modelclasses.Bid;
import com.example.heesoo.modelclasses.Task;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by manuelakm on 2018-03-14.
 */

public class FindNewTaskActivity extends AppCompatActivity {
    private ArrayList<Task> tempTaskList;
    private ArrayList<Task> taskList;
    private ListView listView;
    private Task selectedTask;
    private ArrayAdapter<Task> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_new_tasks);
        //taskList = new TaskList();

        listView = findViewById(R.id.avaliableTasksList);
        listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Object t = listView.getItemAtPosition(i);
                selectedTask = (Task) t;

                Log.d("PRINTING", selectedTask.getTaskName());


                AlertDialog.Builder popUp = new AlertDialog.Builder(FindNewTaskActivity.this);
                popUp.setMessage("Would you like to see details about '"  + selectedTask.getTaskName() + "' ?");
                popUp.setCancelable(true);


                popUp.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                setResult(RESULT_OK);
                                Intent intent = new Intent(getApplicationContext(), PlaceBidActivity.class);
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        tempTaskList = new ArrayList<Task>();
        taskList = new ArrayList<Task>();

        // dummy tasks:
        Task dTask1 = new Task("Requestname1","dTask" ,"dTask1Description","Assigned");
        Task dTask12 = new Task("Requestname2","dTaskName12" ,"dTask12Description","Assigned");
        Task dTask13 = new Task("Requestname2","dTaskName13" ,"dTask13Description","Requested");
        Task dTask123 = new Task("Requestname2","dTaskName123Nameshouldnotappear" ,"dTask123Description","Assigned");

        dTask1.setTaskProvider("Requestname3");
        dTask12.setTaskProvider("Requestname3");
        dTask13.setTaskProvider("Requestname3");

        tempTaskList.add(dTask1);
        tempTaskList.add(dTask12);
        tempTaskList.add(dTask13);
        tempTaskList.add(dTask123);

        Bid newBid = new Bid("dTask", "dTask1Description", "Assigned", 100f,MyApplication.getCurrentUser().getUsername());
        Bid newBid2 = new Bid("dTask", "dTask1Description", "Assigned", 120f, MyApplication.getCurrentUser().getUsername());
        Bid newBid3 = new Bid("dTask", "dTask1Description", "Assigned", 80f, MyApplication.getCurrentUser().getUsername());
        dTask13.addBid(newBid);
        dTask13.addBid(newBid2);
        dTask13.addBid(newBid3);



        // UNCOMMENT OUT WHEN ELASTICSEARCH CONTROLLER IS IMPLEMENTED

        /* ElasticSearchController.GetAllTasks getAllTasks = new ElasticSearchController().GetAllTasks();
        getAllTasks.execute();

        try{
            taskList = getAllTasks.get();
        } catch(Exception e){
            Log.i("ERROR", "Failed to pull tasks from Database");
        } */

        //String task = "";

        for(int i = 0; i < tempTaskList.size(); i++){
            if (!Objects.equals(tempTaskList.get(i).getUserName(), MyApplication.getCurrentUser().getUsername())
                    && !Objects.equals(tempTaskList.get(i).getStatus(), "Assigned")) {
                taskList.add(tempTaskList.get(i));
            }
        }
        Log.d("IN TASKLIST", taskList.get(0).getTaskName());
        adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, android.R.id.text1, taskList);
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

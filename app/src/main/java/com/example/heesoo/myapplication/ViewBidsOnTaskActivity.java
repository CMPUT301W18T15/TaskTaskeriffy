package com.example.heesoo.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.heesoo.modelclasses.Bid;
import com.example.heesoo.modelclasses.Task;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by manuelakm on 2018-03-15.
 */

public class ViewBidsOnTaskActivity extends AppCompatActivity {
    private ListView bidsView;
    private ArrayList<Bid> tempBidList;
    private ArrayList<Bid> bidList;
    private Task task;
    private ArrayAdapter<Bid> adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bids_on_task);

        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");
    }

    @SuppressLint("NewApi")
    @Override
    protected void onStart() {
        super.onStart();
        tempBidList = new ArrayList<Bid>();
        bidList = new ArrayList<Bid>();

        // UNCOMMENT OUT WHEN ELASTICSEARCH CONTROLLER IS IMPLEMENTED

        /* ElasticSearchController.GetAllBids getAllBids = new ElasticSearchController().GetAllBids();
        getAllBids.execute();

        try{
            tempBidList = getAllBids.get();
        } catch(Exception e){
            Log.i("ERROR", "Failed to pull tasks from Database");
        } */

        for(int i = 0; i < tempBidList.size(); i++){
            if (!Objects.equals(tempBidList.get(i).getTaskName(), task.getTaskName())) {
                bidList.add(tempBidList.get(i));
            }
        }
        Log.d("IN TASKLIST", bidList.get(0).getTaskName());
        adapter = new ArrayAdapter<Bid>(this, android.R.layout.simple_list_item_1, android.R.id.text1, bidList);
        bidsView.setAdapter(adapter);

    }
}

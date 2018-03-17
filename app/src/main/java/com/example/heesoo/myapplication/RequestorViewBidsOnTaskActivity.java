package com.example.heesoo.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;


import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.Entities.Task;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by manuelakm on 2018-03-15.
 */

public class RequestorViewBidsOnTaskActivity extends AppCompatActivity {
    private ListView bidsView;
    private ArrayList<Bid> tempBidList;
    private ArrayList<Bid> bidList;
    private Task task;

    //private ArrayAdapter<String> adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bids_on_task);

        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");

        bidsView = findViewById(R.id.provider_assigned_task_list);
        bidsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent bidinfo = new Intent(RequestorViewBidsOnTaskActivity.this, RequestorBidDetailActivity.class);
                Bid bid = bidList.get(index);
                bidinfo.putExtra("bid", bid);
                startActivity(bidinfo);
            }
        });
    }

    @SuppressLint("NewApi")
    @Override
    protected void onStart() {
        super.onStart();
        bidList = new ArrayList<Bid>();

        // TODO: UNCOMMENT OUT WHEN ELASTICSEARCH CONTROLLER IS IMPLEMENTED

        bidList = task.getBids();

        ArrayList<String> bidNames = new ArrayList<String>();


        for(int i = 0; i < bidList.size(); i++){
                bidNames.add("Provider Name: "+ bidList.get(i).getTaskProvider()+" Price: " + bidList.get(i).getBidPrice() + " Status: " + bidList.get(i).getStatus());
            }
        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bidNames);
//        bidsView.setAdapter(adapter);

    }
}

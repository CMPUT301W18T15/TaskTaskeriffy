package com.example.heesoo.myapplication.Requester;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;


import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.R;

import java.util.ArrayList;

/**
 * Created by manuelakm on 2018-03-15.
 */

/*
This activity is navigated to when the requester clicks on the button to view bids through the
RequesterShowTaskDetailActivity when the status of the task is "bidded". This activity provides shows a
list view of all the bids on a particular task to the requester so they may click on a bid to see it's details
and perform an action.
 */

public class RequesterViewBidsOnTaskActivity extends AppCompatActivity {
    private ListView bidsView;
    private ArrayList<Bid> bidList;
    private Task task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bids_on_task);

        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");

        bidsView = findViewById(R.id.bidsView);
        bidsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent bidinfo = new Intent(RequesterViewBidsOnTaskActivity.this, RequesterBidDetailActivity.class);
                Bid bid = bidList.get(index);

                bidinfo.putExtra("bid", bid);
                bidinfo.putExtra("task", task);
                startActivity(bidinfo);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        bidList = new ArrayList<Bid>();
        bidList = task.getBids();

        ArrayList<String> bidNames = new ArrayList<String>();


        for (Bid bid:bidList){
                bidNames.add("Provider Name: "+bid.getTaskProvider()+"Bid Price: "+bid.getBidPrice()+" Status: " + bid.getStatus());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, bidNames);
        bidsView.setAdapter(adapter);

    }
}

package com.example.heesoo.myapplication.task_requester_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;


import com.example.heesoo.myapplication.entities.Bid;
import com.example.heesoo.myapplication.entities.BidList;
import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.R;

import java.util.ArrayList;

import static com.example.heesoo.myapplication.task_requester_activities.ViewRequestedTasksActivity.checkNetwork;

/**
 * Created by manuelakm on 2018-03-15.
 */

/*
This activity is navigated to when the requester clicks on the button to view bids through the
ShowTaskDetailActivity when the status of the task is "bidded". This activity provides shows a
list view of all the bids on a particular task to the requester so they may click on a bid to see it's details
and perform an action.
 */

public class ViewBidsOnTaskActivity extends AppCompatActivity {
    private ListView bidsView;
    private BidList bidList;
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
                Intent bidinfo = new Intent(ViewBidsOnTaskActivity.this, ViewBidDetailActivity.class);
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
        checkNetwork(this);
        bidList = new BidList();
        bidList = task.getBids();

        ArrayList<String> bidNames = new ArrayList<String>();

        for (int i = 0; i < bidList.size(); i++) {
            Bid bid = bidList.get(i);

            bidNames.add("Provider Name: "+bid.getTaskProvider()+"\n Bid Price: "+bid.getBidPrice()+"\n  Status: " + bid.getStatus());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, bidNames);
        bidsView.setAdapter(adapter);

    }
}

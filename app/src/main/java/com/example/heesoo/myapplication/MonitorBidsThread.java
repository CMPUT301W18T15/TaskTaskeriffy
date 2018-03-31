package com.example.heesoo.myapplication;

import android.util.Log;

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchBidController;
import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by manuelakm on 2018-03-31.
 */

public class MonitorBidsThread extends Thread {
    long minInterval;
    String timeStamp;

    MonitorBidsThread(long minInterval) {
        this.minInterval = minInterval;
    }

    public void run() {

        while (true) {

            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

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
                    //if (bid.getTimeStamp() > timeStamp) {
                    //}
                }
            }
        }


    }
}

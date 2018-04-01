package com.example.heesoo.myapplication;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.EditText;

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchBidController;
import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by manuelakm on 2018-03-31.
 */

public class MonitorBidsThread extends Thread {

    long minInterval;
    Calendar timeStamp;
    Notification myNotification;


    MonitorBidsThread(long minInterval) {
        this.minInterval = minInterval;
    }

    public void run() {

        timeStamp = Calendar.getInstance();


        while (true) {

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
}

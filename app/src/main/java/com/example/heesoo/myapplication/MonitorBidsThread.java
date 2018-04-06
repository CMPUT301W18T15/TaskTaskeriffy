package com.example.heesoo.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Process;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchBidController;
import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.Requester.RequesterBiddedTasksListActivity;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.lang.Thread;

public class MonitorBidsThread extends Thread {

    private NotificationManager myNotificationManager;
    private ArrayList<Bid> allBids;
    private Calendar timeStamp;
    private Context context;
    private boolean done;

    public MonitorBidsThread(Context context) {
        this.context = context;
    }

    public void run() {

        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        myNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        timeStamp = Calendar.getInstance();
        done = false;

        while (!done) {

            Log.d("ERROR","In while loop");

            ElasticSearchBidController.GetAllBids getAllBids = new ElasticSearchBidController.GetAllBids();
            getAllBids.execute("");

            allBids = new ArrayList<Bid>();;
            allBids.clear();

            try {
                allBids = getAllBids.get();
            } catch (Exception e) {
                Log.d("ERROR", "Oh no! Something went wrong while accessing the database");
            }

            Log.d("ERROR", "Number of bids: " + Integer.toString(allBids.size()));

            for (Bid bid: allBids) {
                if (bid.getTaskRequester().equals(SetCurrentUser.getCurrentUser().getUsername())) {
                    Log.d("ERROR", "Name of bidder: " + bid.getTaskProvider());

                    if (bid.getTimeStamp().after(timeStamp)) {
                        Log.d("ERROR", "BID HAS BEEN PLACED");

                        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_FOREGROUND);

                        String channelID = "myChannelID";
                        String channelName = "myChannelName";
                        String channelDescription = "myChannelDescription";

                        Intent intent = new Intent(context, RequesterBiddedTasksListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelID)
                                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_focused)
                                .setContentTitle("TaskTaskeriffy")
                                .setContentText("Someone bid on your '" + bid.getTaskName().toUpperCase() + "' task!")
                                .setContentIntent(pendingIntent)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setAutoCancel(true);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                            CharSequence name = channelName;
                            String description = channelDescription;
                            int importance = NotificationManager.IMPORTANCE_DEFAULT;
                            NotificationChannel channel = new NotificationChannel(channelID, name, importance);
                            channel.setDescription(description);

                            myNotificationManager.createNotificationChannel(channel);
                        }

                        myNotificationManager.notify(01, mBuilder.build());

                        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

                    }
                }
            }

            timeStamp = Calendar.getInstance();

            try {
                Thread.currentThread().sleep(60000);
            }
            catch (Exception e) {

            }
        }

    }
}

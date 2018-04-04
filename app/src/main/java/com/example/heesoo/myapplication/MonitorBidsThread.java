package com.example.heesoo.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchBidController;
import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchTaskController;
import com.example.heesoo.myapplication.Entities.Bid;
import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.Provider.ProviderViewBiddedTaskList;
import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

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

        myNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        timeStamp = Calendar.getInstance();
        done = false;

        while (!done) {

            Log.d("ERROR","In while loop");

            ElasticSearchBidController.GetAllBids getAllBids = new ElasticSearchBidController.GetAllBids();
            getAllBids.execute("");

            allBids = new ArrayList<Bid>();;

            try {
                allBids = getAllBids.get();
            } catch (Exception e) {
                Log.d("ERROR", "Oh no! Something went wrong while accessing the database");
            }

            for (Bid bid: allBids) {
                if (bid.getTaskRequester().equals(SetCurrentUser.getCurrentUser().getUsername())) {
                    if (bid.getTimeStamp().after(timeStamp)) {
                        Log.d("ERROR", "BID HAS BEEN PLACED");

                        String channelID = "myChannelID";
                        String channelName = "myChannelName";
                        String channelDescription = "myChannelDescription";

                        Intent intent = new Intent(context, ProviderViewBiddedTaskList.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelID)
                                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_focused)
                                .setContentTitle("TaskTaskeriffy")
                                .setContentText("You have received a bid on the following task: " + bid.getTaskName() + "!")
                                .setContentIntent(pendingIntent)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setAutoCancel(true);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                            CharSequence name = channelName;
                            String description = channelDescription;
                            int importance = NotificationManager.IMPORTANCE_DEFAULT;
                            NotificationChannel channel = new NotificationChannel(channelID, name, importance);
                            channel.setDescription(description);

                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                            myNotificationManager.createNotificationChannel(channel);
                        }

                        myNotificationManager.notify(01, mBuilder.build());
                    }
                }
            }

            SleepTask sleepTask = new SleepTask();
            sleepTask.execute();

            //SystemClock.sleep(7000);

//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Log.d("ERROR","End of delay");
//                }
//            }, 6000);

//            try {
//                //Thread.sleep(60000);
//                Log.d("Error", "BEFORE pLEASE");
//                Thread.currentThread().sleep(6000);
//                //Thread.currentThread().wait(6000);
//                Log.d("Error", "AFTER pLEASE");
//            }
//            catch (Exception e) {
//                Log.d("ERROR", "nO WAIT");
//            }
        }

    }
}

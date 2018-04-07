package com.example.heesoo.myapplication.general_activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Process;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.heesoo.myapplication.elastic_search_controllers.ElasticSearchBidController;
import com.example.heesoo.myapplication.entities.Bid;
import com.example.heesoo.myapplication.R;
import com.example.heesoo.myapplication.entities.BidList;
import com.example.heesoo.myapplication.task_requester_activities.TaskRequesterViewBiddedTasksActivity;
import com.example.heesoo.myapplication.shared_preferences.SetPublicCurrentUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.lang.Thread;

public class NotificationThread extends Thread {

    private NotificationManager myNotificationManager;
    private BidList allBids;
    private Calendar timeStamp;
    private Context context;
    private boolean done;

    public NotificationThread(Context context) {
        this.context = context;
    }

    public void run() {

        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        myNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        timeStamp = Calendar.getInstance();
        done = false;

        while (!done) {

            ElasticSearchBidController.GetAllBids getAllBids = new ElasticSearchBidController.GetAllBids();
            getAllBids.execute("");

            allBids = new BidList();;
            allBids.clear();

            try {
                allBids = getAllBids.get();
            } catch (Exception e) {
                Log.d("ERROR", "Oh no! Something went wrong while accessing the database");
            }

            for (int i = 0; i < allBids.size(); i++) {
                Bid bid = allBids.get(i);

                if (bid.getTaskRequester().equals(SetPublicCurrentUser.getCurrentUser().getUsername())) {

                    if (bid.getTimeStamp().after(timeStamp)) {

                        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_FOREGROUND);

                        String channelID = "myChannelID";
                        String channelName = "myChannelName";
                        String channelDescription = "myChannelDescription";

                        Intent intent = new Intent(context, TaskRequesterViewBiddedTasksActivity.class);
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

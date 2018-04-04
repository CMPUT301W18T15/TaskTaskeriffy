package com.example.heesoo.myapplication;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

/**
 * Created by manuelakm on 2018-04-04.
 */

public class SleepTask extends AsyncTask<Void,Void,Void> {

    @Override
    protected Void doInBackground(Void... voids) {


        try {
            Thread.currentThread().sleep(3000);
        }
        catch (Exception e) {}

//        Handler handler=new Handler();
//        Runnable r=new Runnable() {
//            public void run() {
//                //what ever you do here will be done after 3 seconds delay.
//                Log.d("ERROR", "AFTER DELAY");
//            }
//        };
//        handler.postDelayed(r, 3000);

        return null;
    }
}

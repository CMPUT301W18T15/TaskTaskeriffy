package com.example.heesoo.myapplication.ElasticSearchControllers;

import android.os.AsyncTask;
import android.util.Log;

<<<<<<< HEAD:app/src/main/java/com/example/heesoo/myapplication/ElasticSearchControllers/ElasticSearchBidController.java
import com.example.heesoo.myapplication.Entities.Bid;
=======
import com.example.heesoo.modelclasses.Bid;
>>>>>>> 44a22492f696b3ec835da725f9daf182c819c159:app/src/main/java/com/example/heesoo/myapplication/ElasticSearchBidController.java
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Created by riyariya on 2018-03-15.
 */

public class ElasticSearchBidController {
    private static JestDroidClient client;

    public static class AddBidsTask extends AsyncTask<Bid, Void, Void> {

        @Override
        protected Void doInBackground(Bid... bids) {
            verifySettings();

            for (Bid bid : bids) {
                Index index = new Index.Builder(bid).index("cmput301w18t15")
                        .type("bid")
                        .build();
                try {
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded()) {
                        bid.setId(result.getId());
                    }
                    else {
                        Log.i("Error","Some error =(");
                    }

                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the bids");
                }
            }
            return null;
        }
    }


    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}

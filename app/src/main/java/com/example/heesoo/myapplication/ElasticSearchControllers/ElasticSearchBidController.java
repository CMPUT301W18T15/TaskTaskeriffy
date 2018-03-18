package com.example.heesoo.myapplication.ElasticSearchControllers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.heesoo.myapplication.Entities.Bid;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/*
This class is how the program accesses and updates the bid type of the our index in the database.
It is used in the following : ProviderFindNewTaskActivity, ProviderPlaceBidActivity,
RequesterShowTaskDetailActivity.
 */

public class ElasticSearchBidController {
    private static JestDroidClient client;

    private final static String index_team = "cmput301w18t15";
    private final static String type_bid = "bid";
    private final static String database_team = "http://cmput301.softwareprocess.es:8080";

    public static class AddBidsTask extends AsyncTask<Bid, Void, Void> {

        @Override
        protected Void doInBackground(Bid... bids) {
            verifySettings();

            for (Bid bid : bids) {
                Index index = new Index.Builder(bid).index(index_team)
                        .type(type_bid)
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

    public static class GetBid extends AsyncTask<String, Void, Bid> {
        @Override
        protected Bid doInBackground(String... id) {
            verifySettings();
            Bid bid = null;
            Get get = new Get.Builder(index_team, id[0])
                    .type(type_bid)
                    .build();
            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    bid = result.getSourceAsObject(Bid.class);
                }
                else{
                    Log.i("error", "Search query failed to find any thing =/");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return bid;
        }

    }

    public static class EditBidTask extends AsyncTask<Bid, Void, Void> {
        @Override
        protected Void doInBackground(Bid... bids) {
            verifySettings();

            for (Bid bid : bids) {

                Index index = new Index.Builder(bid)
                        .index(index_team)
                        .type(type_bid)
                        .id(bid.getId())
                        .build();

                try {
                    DocumentResult execute = client.execute(index);
                    if (execute.isSucceeded()) {
                        Log.i("ESC.EditTask", "Bid has been updated.");
                    }
                }
                catch (Exception e) {
                    Log.e("ESC.EditTask", "Something went wrong when we tried to communicate with the elasticsearch server!");
                }

            }
            return null;
        }
    }

    public static class DeleteBidTask extends AsyncTask<Bid, Void, Void> {
        @Override
        protected Void doInBackground(Bid... bids) {
            verifySettings();

            for (Bid bid : bids) {
                Delete delete = new Delete.Builder(bid.getId())
                        .index(index_team)
                        .type(type_bid)
                        .build();
                try {
                    DocumentResult result = client.execute(delete);
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to delete the bid");

                }
            }
            return null;
        }
    }

    public static class GetAllBids extends AsyncTask<String, Void, ArrayList<Bid>> {

        @Override
        protected ArrayList<Bid> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Bid> bids = new ArrayList<Bid>();

            //TODO: Try this commented stuff if older implementation does not work
            //String query = ("{ \"query\": { \" match_all \" : {} } }");
            //Search search = new Search.Builder(query).addIndex(index_team).addType(type_bid).build();

            Search search = new Search.Builder(search_parameters[0])
                    .addIndex(index_team)
                    .addType(type_bid)
                    .build();


            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<Bid> foundBids;
                    foundBids = result.getSourceAsObjectList(Bid.class);
                    bids.addAll(foundBids);
                }
                else{
                    Log.i("ERROR","search query failed to find anything");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return bids;
        }
    }

    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(database_team);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}

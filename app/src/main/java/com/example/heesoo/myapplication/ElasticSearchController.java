package com.example.heesoo;


import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by heesoopark on 2018-03-12.
 */

public class ElasticSearchController {

    private static JestDroidClient client;

    public static class addUserTask extends AsyncTask<String, Void, User> {

        @Override
        protected User doInBackground(String... parameters) {
            verifySettings();

            User user = null;

            String query = "{\n" +
                    "      \"query\" : {\n" +
                    "           \"constant_score\" : {\n" +
                    "               \"filter\" : {\n" +
                    "                    \"term\" : {\"username\": \"" + parameters[1] + "\"}\n" +
                    "               }\n" +
                    "           }\n" +
                    "       }\n" +
                    "}";

            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t15")
                    .addType("User")
                    .build();

            String source = "{\"name\": \"" + parameters[0] + "\"," +
                    "\"usernam\": \"" + parameters[1];
            Index index = new Index.Builder(source).index("cmput301w18t15").type("User").build();

            try {
                SearchResult result = client.execute(search);
                JsonObject hits = result.getJsonObject().getAsJsonObject("hits");
                if (result.isSucceeded()) {
                    if (hits.get("total").getAsInt() == 0) {
                        DocumentResult execute = client.execute(index);
                        if (execute.isSucceeded()) {
                            user = new User(parameters[0], parameters[1], execute.getId());
                            Log.i("ESC.AddUserTask", "User has been created.");
                        } else {
                            Log.i("ESC.AddUserTask", "Application failed to create new user.");
                        }
                    } else {
                        Log.i("ESC.AddUserTask", "The user already exists.");
                    }
                } else {
                    Log.e("ESC.AddUserTask", "The search query failed.");
                }

            }
            catch (Exception e) {
                Log.e("ESC.AddUserTask", "Something went wrong with connecting with elasticsearch server");
            }

            return user;
        }
    }

    public static class GetUserTask extends AsyncTask<String, Void, User> {

        @Override
        protected User doInBackground(String... parameters) {
            verifySettings();

            User user = null;

            String query = "{\n" +
                    "           \"query\" : {\n" +
                    "               \"constant_score\" : {\n" +
                    "                   \"filter\" : {\n" +
                    "                       \"terms\" : {\"username\": \"" + parameters[0] + "\"}\n" +
                    "                   }\n" +
                    "               }\n" +
                    "           }\n" +
                    "}";

            Log.d("ESC.GetUserTask", query);

            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t15")
                    .addType("User")
                    .build();

            try {
                SearchResult result = client.execute(search);
                JsonObject hits = result.getJsonObject().getAsJsonObject("hits");
                if (result.isSucceeded()) {
                    if (hits.get("total").getAsInt() == 1) {
                        JsonObject userInfo = hits.getAsJsonArray("hits").get(0).getAsJsonObject();
                        JsonObject userInfoSouce = userInfo.get("_source").getAsJsonObject();

                        String id = userInfo.get("_id").getAsString();

                        user = new Gson().fromJson(userInfoSouce, User.class);
                        user.setId(id);

                        Log.i("ESC.GetUserTask", "Unique user was found.");
                    } else {
                        Log.i("ESC.GetUserTask", "User does not exist or is not unique.");
                    }
                } else {
                    Log.i("ESC.getUserTask", "The search query failed.");
                }

            }
            catch (Exception e) {
                Log.e("ESC.GetUserTask", "Something went wrong");
            }
            return user;
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

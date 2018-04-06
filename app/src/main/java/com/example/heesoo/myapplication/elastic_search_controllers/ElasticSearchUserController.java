package com.example.heesoo.myapplication.elastic_search_controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.heesoo.myapplication.entities.User;
import com.google.gson.Gson;


import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.client.JestResult;

import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;

/*
This class is how the program accesses and updates the user type of the our index in the database.
It is used in the following : RegisterActivity, MainActivity, EditProfileActivity.
 */

public class ElasticSearchUserController {


    private static JestDroidClient client;

    private final static String index_team = "cmput301w18t15";
    private final static String type_user = "user";
    private final static String database_team = "http://cmput301.softwareprocess.es:8080";


    // TODO we need a function which adds tweets to elastic search
    public static class AddUserTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();

            for (User user : users) {

                String source = "{ \"username\" : \"" + user.getUsername() + "\"," +
                        " \"password\" : \"" + user.getPassword() + "\"," +
                        " \"email\" : \"" + user.getEmailAddress() + "\"," +
                        " \"phonenumber\" : \"" + user.getPhoneNumber() + "\"}";

                Log.d("DATABASE SAVING", source);
                Gson gson = new Gson();
                String serializedUser = gson.toJson(user);
                Index index = new Index.Builder(user)
                        .index(index_team)
                        .type(type_user)
                        .id(user.getUsername())
                        .build();
                try {
                    Log.d("DATABASE SAVING" ,"Before execution");
                    DocumentResult result = client.execute(index);
                    Log.d("YOUME10",""+result);
                    Log.d("EXECUTE_RESULT", ""+result);
                    Log.d("DATABASE SAVING" ,"After execution");
                    if (result.isSucceeded()) {
                        user.setId(result.getId());
                        Log.d("DATABASE SAVING" ,"Successfully saved");

                    }else{
                        Log.d("DATABASE SAVING" ,"User not entered");
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Log.d("DATABASE SAVING" ,"The program failed to save to database");
                }

            }
            return null;
        }
    }

    public static class GetUserTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... id) {
            verifySettings();
            User user = null;
            Get get = new Get.Builder(index_team, id[0])
                    .type(type_user)
                    .build();
            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    user = result.getSourceAsObject(User.class);
                }
                else{
                    Log.i("error", "Search query failed to find any thing =/");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return user;
        }

    }


    public Boolean profileExists(String username) {
        boolean result = true;
        GetUserTask getUserTask = new GetUserTask();
        getUserTask.execute(username);
        try {
            User profile = getUserTask.get();
            Log.d("XXX",""+profile);
            // return false if no profile found
            if (profile == null || username.isEmpty()) {
                result = false;
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }


    public static class EditUserTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();

            for (User user : users) {

                Index index = new Index.Builder(user)
                        .index(index_team)
                        .type(type_user)
                        .id(user.getUsername())
                        .build();

                try {
                    DocumentResult execute = client.execute(index);
                    if (execute.isSucceeded()) {
                        Log.i("ESC.UpdateUserTask", "User has been updated.");
                    }
                }
                catch (Exception e) {
                    Log.e("ESC.UpdateUserTask", "Something went wrong when we tried to communicate with the elasticsearch server!");
                }

            }
            return null;
        }
    }

    public static class DeleteProfile extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();

            for (User user : users) {
                Delete delete = new Delete.Builder(user.getUsername())
                        .index(index_team)
                        .type(type_user)
                        .build();
                try {
                    DocumentResult result = client.execute(delete);
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and delete the profile");

                }
            }
            return null;
        }
    }

    public static void verifySettings() {
        if (client == null) {
            Log.d("DATABASE SAVING", "Setting droid client");
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(database_team);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}

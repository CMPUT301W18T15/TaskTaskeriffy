package com.example.heesoo.myapplication.ElasticSearchControllers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.heesoo.myapplication.Entities.Task;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/*
This class is how the program accesses and updates the task type of the our index in the database.
It is used in the following : Task, ProviderFindNewTaskActivity, ProviderMainActivity,
ProviderViewBiddedTaskActivity and all Activities in the Requester package
(minus RequesterViewBidsOnTaskActivity).
 */

public class ElasticSearchTaskController {

    private final static String index_team = "cmput301w18t15";
    private final static String type_task = "task";
    private final static String database_team = "http://cmput301.softwareprocess.es:8080";


    private static JestDroidClient client;

    public static class AddTask extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {
            verifySettings();

            for (Task task : tasks) {
                Index index = new Index.Builder(task).index(index_team)
                        .type(type_task)
                        .build();
                try {
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded()) {
                        task.setId(result.getId());
                    }
                    else {
                        Log.i("Error","Some error =(");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the tasks");
                }
            }
            return null;
        }
    }


    public static class GetTask extends AsyncTask<String, Void, Task> {
        @Override
        protected Task doInBackground(String... id) {
            verifySettings();
            Task task = null;
            Get get = new Get.Builder(index_team, id[0])
                    .type(type_task)
                    .build();
            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    task = result.getSourceAsObject(Task.class);
                }
                else{
                    Log.i("error", "Search query failed to find any thing =/");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return task;
        }

    }

    public static class EditTask extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {
            verifySettings();

            for (Task task : tasks) {

                Index index = new Index.Builder(task)
                        .index(index_team)
                        .type(type_task)
                        .id(task.getId())
                        .build();

                try {
                    DocumentResult execute = client.execute(index);
                    if (execute.isSucceeded()) {
                        Log.i("ESC.EditTask", "Task has been updated.");
                    }
                }
                catch (Exception e) {
                    Log.e("ESC.EditTask", "Something went wrong when we tried to communicate with the elasticsearch server!");
                }

            }
            return null;
        }
    }

    public static class DeleteTask extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {
            verifySettings();

            for (Task task : tasks) {
                Delete delete = new Delete.Builder(task.getId())
                        .index(index_team)
                        .type(type_task)
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

    public static class GetAllTasks extends AsyncTask<String, Void, ArrayList<Task>> {

        @Override
        protected ArrayList<Task> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Task> tasks = new ArrayList<Task>();

            //TODO: Try this commented stuff if older implementation does not work
            //String query = ("{ \"query\": { \" match_all \" : {} } }");
            //Search search = new Search.Builder(query).addIndex(index_team).addType(type_task).build();

            Search search = new Search.Builder(search_parameters[0])
                    .addIndex(index_team)
                    .addType(type_task)
                    .build();


            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<Task> foundTasks;
                    foundTasks = result.getSourceAsObjectList(Task.class);
                    tasks.addAll(foundTasks);
                }
                else{
                    Log.i("ERROR","search query failed to find anything");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return tasks;
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

package com.example.heesoo.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.DocumentResult;

/**
 * Created by riyariya on 2018-03-12.
 */

public class ElasticSearchTaskController {

    private static JestDroidClient client;

    public static class AddTasksTask extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {
            verifySettings();

            for (Task task : tasks) {
                Index index = new Index.Builder(task).index("cmput301w18t15")
                        .type("task")
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

    // TODO we need a function which gets tasks from elastic search
    public static class GetTasksTask extends AsyncTask<String, Void, TaskList> {
        @Override
        protected TaskList doInBackground(String... search_parameters) {
            verifySettings();

            TaskList tasks = new TaskList();

            // TODO Build the query
            Search search = new Search.Builder(search_parameters[0]).addIndex("cmput301w18t15").addType("task").build();
            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if(result.isSucceeded())
                {
                    TaskList foundTasks = result.getSourceAsObjectList(Task.class);
                    tasks.addAll(foundTasks);
                }
                else
                {
                    Log.i("Error", "Search query failed to find any thing =/");
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
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}

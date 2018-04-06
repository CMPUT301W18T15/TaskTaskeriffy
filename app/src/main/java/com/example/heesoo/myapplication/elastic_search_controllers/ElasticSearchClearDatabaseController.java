package com.example.heesoo.myapplication.elastic_search_controllers;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;

import io.searchbox.client.JestResult;
import io.searchbox.indices.DeleteIndex;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by heesoopark on 2018-04-05.
 */

public class ElasticSearchClearDatabaseController {
    private static JestDroidClient client;
    private final static String database_team = "http://cmput301.softwareprocess.es:8080";

    public void deleteIndex() throws IOException {

        String indexName = "cmput301w18t15";
        //createIndex(indexName);

        verifySettings();

        DeleteIndex indicesExists = new DeleteIndex.Builder(indexName).build();
        JestResult result = client.execute(indicesExists);
        assertTrue(result.getErrorMessage(), result.isSucceeded());
    }

    public void deleteTask() throws IOException {
        String indexName = "cmput301w18t15";
        //createIndex(indexName);

        verifySettings();

        DeleteIndex indicesExists = new DeleteIndex.Builder(indexName)
                .type("task")
                .build();
        JestResult result = client.execute(indicesExists);
        assertTrue(result.getErrorMessage(), result.isSucceeded());

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

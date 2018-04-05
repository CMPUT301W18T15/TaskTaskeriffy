package com.example.heesoo.myapplication.ElasticSearchControllers;

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

    public void deleteIndex() throws IOException {
        String indexName = "cmput301w18t15";
        //createIndex(indexName);

        DeleteIndex indicesExists = new DeleteIndex.Builder(indexName).build();
        JestResult result = client.execute(indicesExists);
        assertTrue(result.getErrorMessage(), result.isSucceeded());
    }


    public void deleteNonExistingIndex() throws IOException {
        DeleteIndex deleteIndex = new DeleteIndex.Builder("newindex2").build();
        JestResult result = client.execute(deleteIndex);
        assertFalse("Delete request should fail for an index that does not exist", result.isSucceeded());
    }

}

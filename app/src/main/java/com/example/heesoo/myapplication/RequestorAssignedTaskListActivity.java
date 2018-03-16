package com.example.heesoo.myapplication;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;

        import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchTaskController;
        import com.example.heesoo.myapplication.Entities.Task;


        import java.util.ArrayList;
        import java.util.Objects;

/**
 * Created by riyariya on 2018-03-14.
 */

public class RequestorAssignedTaskListActivity extends AppCompatActivity {

    private ArrayList<Task> taskList; // the list of tasks that requester posted
    private ListView clickableList;
    private ArrayList<Task> allTasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_assigned_task_list);


        // when click on list
        clickableList = findViewById(R.id.requester_assigned_task_list);
        clickableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent taskinfo = new Intent(com.example.heesoo.myapplication.RequestorAssignedTaskListActivity.this, RequestorShowTaskDetailActivity.class);
                Task task = taskList.get(index);
                taskinfo.putExtra("task", task);
                startActivity(taskinfo);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        //@todo need to pull all the tasks posted by this requester
        // will return an arraylist of tasks,
        // @todo get user's name
        //String thisRequesterName = getCurrentUser
        taskList = new ArrayList<Task>();
        allTasks = new ArrayList<Task>();


//        // dummy tasks:
//        Task dTask1 = new Task("Requestname1","dTaskNameshouldnotappear" ,"dTask1Description","Assigned");
//        Task dTask12 = new Task("Requestname2","dTaskName12" ,"dTask12Description","Assigned");
//        Task dTask13 = new Task("Requestname2","dTaskName13" ,"dTask13Description","Requested");
//        Task dTask123 = new Task("Requestname2","dTaskName123" ,"dTask123Description","Bidded");
//        // dummy user's name
//
//        String thisRequesterName = "Requestname2";
//        String thisStatus = "Assigned";
//
//
//        allTasks.add(dTask1);
//        allTasks.add(dTask12);
//        allTasks.add(dTask13);
//        allTasks.add(dTask123);

        ArrayList<String> requesterAssignedTasksNames = new ArrayList<String>();

        ElasticSearchTaskController.GetAllTasks getAllTasks = new ElasticSearchTaskController.GetAllTasks();
        getAllTasks.execute("");

        try {
            allTasks = getAllTasks.get();
        }
        catch (Exception e) {
            Log.i("Error", "The request for tweets failed in onStart");
        }

        for (Task task:allTasks){
            if (MyApplication.getCurrentUser().getUsername().equals(task.getUserName()) && task.getStatus().equals("Assigned")){
                taskList.add(task);
                requesterAssignedTasksNames.add("Name: "+task.getTaskName()+" Status: " + task.getStatus());

            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, requesterAssignedTasksNames);
        clickableList.setAdapter(adapter);

    }

}

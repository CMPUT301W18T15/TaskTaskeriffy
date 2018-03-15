package com.example.heesoo.myapplication;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListView;

        import java.io.Serializable;
        import java.util.ArrayList;

/**
 * Created by riyariya on 2018-03-14.
 */

public class RequestorAssignedTaskListActivity extends AppCompatActivity {

    private ArrayList<Task> taskList; // the list of tasks that requester posted
    private ListView clickableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_assigned_task_list);


        // when click on list
        clickableList = (ListView) findViewById(R.id.requester_assigned_task_list);
        clickableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent taskinfo = new Intent(com.example.heesoo.myapplication.RequestorAssignedTaskListActivity.this, RequestorShowTaskDetailActivity.class);
                Task task = taskList.get(index);
                taskinfo.putExtra("task", (Serializable)task);
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

        // dummy tasks:
        Task dTask1 = new Task("Requestname1","dTaskNameshouldnotappear" ,"dTask1Description","Assigned");
        Task dTask12 = new Task("Requestname2","dTaskName12" ,"dTask12Description","Assigned");
        Task dTask13 = new Task("Requestname2","dTaskName13" ,"dTask13Description","Requested");
        Task dTask123 = new Task("Requestname2","dTaskName123" ,"dTask123Description","Bidded");
        // dummy user's name

        String thisRequesterName = "Requestname2";
        String thisStatus = "Assigned";


        taskList.add(dTask1);
        taskList.add(dTask12);
        taskList.add(dTask13);
        taskList.add(dTask123);



        ArrayList<String> requesterAssignedTasksNames = new ArrayList<String>();

        for(int i = 0; i < taskList.size(); i++){
            if(thisRequesterName == taskList.get(i).getUserName() && thisStatus == taskList.get(i).getStatus()){
                requesterAssignedTasksNames.add("Name: "+taskList.get(i).getTaskName()+" Status: " + taskList.get(i).getStatus());
            }
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, requesterAssignedTasksNames);
        clickableList.setAdapter(adapter);

    }

}

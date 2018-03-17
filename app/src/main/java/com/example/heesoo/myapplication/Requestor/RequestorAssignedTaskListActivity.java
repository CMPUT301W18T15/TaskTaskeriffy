package com.example.heesoo.myapplication.Requestor;

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
        import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;
        import com.example.heesoo.myapplication.R;


        import java.util.ArrayList;

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
        setContentView(R.layout.activity_requester_assigned_task_list);


        // when click on list
        clickableList = findViewById(R.id.requester_assigned_task_list);
        clickableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent taskinfo = new Intent(RequestorAssignedTaskListActivity.this, RequestorShowTaskDetailActivity.class);
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
            if (SetCurrentUser.getCurrentUser().getUsername().equals(task.getUserName()) && task.getStatus().equals("Assigned")){
                taskList.add(task);
                requesterAssignedTasksNames.add("Name: "+task.getTaskName()+" Status: " + task.getStatus());

            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, requesterAssignedTasksNames);
        clickableList.setAdapter(adapter);

    }

}
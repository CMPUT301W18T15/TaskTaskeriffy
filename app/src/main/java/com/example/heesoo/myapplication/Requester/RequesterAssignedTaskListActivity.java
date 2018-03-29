package com.example.heesoo.myapplication.Requester;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v4.view.MenuItemCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.SearchView;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;

        import com.example.heesoo.myapplication.ElasticSearchControllers.ElasticSearchTaskController;
        import com.example.heesoo.myapplication.Entities.Task;
        import com.example.heesoo.myapplication.SetCurrentUser.SetCurrentUser;
        import com.example.heesoo.myapplication.R;


        import java.util.ArrayList;

        import static com.example.heesoo.myapplication.Requester.RequesterMainActivity.checkNetwork;

/**
 * Created by riyariya on 2018-03-14.
 */

/*
This activity serves to show the task requester a list of the tasks requested by them that are assigned to a provider.
This activity is reached through the show assigned task list button on the dashboard (RequesterMainActivity).
 */

public class RequesterAssignedTaskListActivity extends AppCompatActivity {

    private ArrayList<Task> taskList; // the list of tasks that requester posted
    private ListView clickableList;
    private ArrayList<Task> allTasks;
    private ArrayAdapter<Task> taskAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_assigned_task_list);


        // when click on list
        clickableList = findViewById(R.id.requester_assigned_task_list);
        clickableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent taskinfo = new Intent(RequesterAssignedTaskListActivity.this, RequesterShowTaskDetailActivity.class);
                Task task = taskList.get(index);
                taskinfo.putExtra("task", task);
                startActivity(taskinfo);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<Task> templist = new ArrayList<Task>();

                for(Task temp : taskList){
                    if (temp.getTaskName().toLowerCase().contains(newText.toLowerCase())) {
                        templist.add(temp);
                    }
                }
                taskAdapter = new ArrayAdapter<Task>(RequesterAssignedTaskListActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, templist);
                //taskAdapter.notifyDataSetChanged();
                clickableList.setAdapter(taskAdapter);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        taskList = new ArrayList<Task>();
        allTasks = new ArrayList<Task>();
        checkNetwork(this);
        ArrayList<String> requesterAssignedTasksNames = new ArrayList<String>();

        ElasticSearchTaskController.GetAllTasks getAllTasks = new ElasticSearchTaskController.GetAllTasks();
        getAllTasks.execute("");

        try {
            allTasks = getAllTasks.get();
        }
        catch (Exception e) {
            Log.i("Error", "The request for tasks failed in onStart");
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

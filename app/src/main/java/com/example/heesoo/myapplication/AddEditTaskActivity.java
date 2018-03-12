package com.example.heesoo.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by riyariya on 2018-03-12.
 */


public class AddEditTaskActivity extends Activity {
    private EditText taskName;
    private EditText taskDescription;
    private Button saveButton;
    private TaskList taskList;
    private ArrayAdapter<Task> adapter;

    @Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.i("LifeCycle ---->", "onCreate is called");
    setContentView(R.layout.addedittask);

    taskName = (EditText) findViewById(R.id.taskName);
    taskDescription = (EditText) findViewById(R.id.taskDescription);
//    attachPictureButton = (Button) findViewById(R.id.attachPictureButton);
//    attachMapButton = (Button) findViewById(R.id.attachMapButton);
    saveButton = (Button) findViewById(R.id.save);



    saveButton.setOnClickListener(new View.OnClickListener() {

        public void onClick(View v) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            setResult(RESULT_OK);
            String name = taskName.getText().toString();
            String description = taskDescription.getText().toString();
            // Check if Required fields are entered
            if (name.isEmpty()){
                CharSequence text = "Missing Required Fields";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            // Save all the fields
            else{
                Task task = new Task(getusername(), name, description, status); //TODO: how to find status?
                Boolean isEditing = false ;
                int i;
                //Finding if the action was edit or add
                for (i = 0; i < taskList.size(); i ++) {
                    System.out.println(i);
                    if (taskList.get(i).getName().equals(task.getTaskName())){
                        System.out.println("found the element");
                        isEditing = true;
                        break;
                    }
                }
                //On edit, modify the task
                if (isEditing){
                    taskList.set(i,task);
                }
                //On add, append to the taskList
                else{
                    taskList.add(task);
                }
                adapter.notifyDataSetChanged();
                ElasticsearchTaskController.AddTasksTask addTasksTask = new ElasticsearchTaskController.AddTasksTask();
                addTasksTask.execute(newTweet);
                CharSequence text = "Saving Task";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //Clear all the views
                taskName.getText().clear();
                taskDescription.getText().clear();
            }
        }
    });
}

    /**
     * Attaches the adapter
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LifeCycle --->", "onStart is called");
        ElasticsearchTaskController.GetTasksTask getTasksTask = new ElasticsearchTaskController.GetTasksTask();
        getTasksTask.execute("");
        try{
            taskList = getTasksTask.get();
        }
        catch (Exception e)
        {
            Log.i("Error", "Failed to get the tasks from the async object");
        }
        //adapter = new ArrayAdapter<Task>(this,
                R.layout.list_item, tweetList);
        //oldTweetsList.setAdapter(adapter); TODO: what
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onDestroy is called");
    }



}

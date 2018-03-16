<<<<<<< HEAD:app/src/main/java/com/example/heesoo/myapplication/TaskProvider.java
package com.example.heesoo.myapplication;

import com.example.heesoo.myapplication.Entities.Task;
import com.example.heesoo.myapplication.Entities.TaskList;
import com.example.heesoo.myapplication.Entities.User;
=======
package com.example.heesoo.modelclasses;
>>>>>>> 44a22492f696b3ec835da725f9daf182c819c159:app/src/main/java/com/example/heesoo/modelclasses/TaskProvider.java

/**
 * Created by chengze on 2018/2/26.
 */

public class TaskProvider extends User {
    private TaskList myTasks;

    public TaskProvider(String Username, String Password, String emailAddress, String phoneNumber) {
        super(Username, Password, emailAddress, phoneNumber);
        myTasks = new TaskList();
    }

    public void addTask(Task t) {

        myTasks.addTask(t);
    }

    public void removeTask(Task t) {

        myTasks.removeTask(t);
    }

    public Task getTask(Task t) {

        return myTasks.getTask(t);
    }

    public TaskList getTasks() {

        return myTasks;
    }

    public TaskList getAssignedTasks() {

        return myTasks.getAssignedTasks();
    }

    public TaskList getBiddedTasks() {

        return myTasks.getBiddedTasks();
    }

    public TaskList getCompletedTasks() {

        return myTasks.getCompletedTasks();
    }
}

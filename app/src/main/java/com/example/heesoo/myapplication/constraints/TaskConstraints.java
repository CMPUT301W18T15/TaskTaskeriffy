package com.example.heesoo.myapplication.constraints;

/*
This class contains methods that allow the program to verify task constraints, such as : the task
 title satisfies a maximum length constraint, the description satisfies a maximum length constraint,
 and that neither field has been left empty. An instance of this class is utilized by the AddTaskActivity.
 */

public class TaskConstraints {

    public Boolean titleLength(String title) {
        if (title.length() <= 30) {
            return true;
        }else{
            return false;
        }
    }
    public Boolean descriptionLength(String description) {
        if (description.length() <= 300) {
            return true;
        }else{
            return false;
        }
    }

    public boolean checkEmpty(String name, String description) {
        if(!(name.equals("") || description.equals(""))){
            return true;
        }else{
            return false;
        }
    }

}

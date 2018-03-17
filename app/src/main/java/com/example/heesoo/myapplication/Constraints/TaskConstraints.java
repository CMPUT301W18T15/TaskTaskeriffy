package com.example.heesoo.myapplication.Constraints;

/**
 * Created by heesoopark on 2018-03-17.
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

}

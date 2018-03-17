package com.example.heesoo.myapplication.Constraints;

import android.support.v7.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by heesoopark on 2018-03-17.
 */

public class UserConstraints{

    public Boolean PasswordMatch(String password, String repeat_password) {
        if (password.equals(repeat_password)) {
            return true;
        }else{
            return false;
        }
    }

    public boolean checkEmpty(String username, String password, String repeat_password, String email, String phone) {
        if(!(username.equals("") || password.equals("") || repeat_password.equals("") || email.equals("") || phone.equals(""))){
            return true;
        }else{
            return false;
        }
    }

    public boolean usernameLength(String username){
        if (username.length() <= 8) {
            return true;
        }else{
            return false;
        }
    }

    public boolean emailFormat(String email){
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        if (mat.matches()){
            return true;
        }else{
            return false;
        }
    }

}

package com.example.heesoo.myapplication.constraints;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
This class contains methods that allow the program to verify user constraints, such as : the user
has not left any fields blank, the username satisfies a maximum length, the entered email address
follows the correct format, and the entered passwords match. An instance of this class is utilized
by the RegisterActivity.
 */

public class UserConstraints {

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

package com.example.heesoo.myapplication.ModeClassTests;

import android.media.Image;
import android.test.ActivityInstrumentationTestCase2;

import com.example.heesoo.myapplication.entities.User;
//import com.example.heesoo.myapplication.user_task_bid.User;


/**
 * Created by chengze on 2018/2/25.
 */

public class UserTest extends ActivityInstrumentationTestCase2 {
    public UserTest(){
        super(User.class);
    }

    private String username = "Chengze";
    private String password = "chengze2018";
    private String emailAddress = "chengze@example.com";
    private String phoneNumber = "11111111111";
    private Image profilePicture;
    private Image newPicture;

    public void testGetUsername(){
        User user = new User(username, password, emailAddress, phoneNumber);
        assertEquals(username, user.getUsername());
    }

    public void testSetUsername(){
        User user = new User(username, password, emailAddress, phoneNumber);
        user.setUsername("newUserName");
        assertEquals("newUserName", user.getUsername());
    }

   public void testGetPassword(){
        User user = new User(username, password, emailAddress, phoneNumber);
        assertEquals(password, user.getPassword());
    }

    public void testSetPassword(){
        User user = new User(username, password, emailAddress, phoneNumber);
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    public void testGetEmail(){
        User user = new User(username, password, emailAddress, phoneNumber);
        assertEquals(emailAddress, user.getEmailAddress());
    }

    public void testSetEmail(){
        User user = new User(username, password, emailAddress, phoneNumber);
        user.setEmailAddress("newemail@example.com");
        assertEquals("newemail@example.com", user.getEmailAddress());
    }

    public void testGetPhoneNumber(){
        User user = new User(username, password, emailAddress, phoneNumber);
        assertEquals(phoneNumber, user.getPhoneNumber());
    }

    public void testSetPhoneNumber(){
        User user = new User(username, password, emailAddress, phoneNumber);
        user.setPhoneNumber("newAddress");
        assertEquals("newAddress", user.getPhoneNumber());
    }

    public void testGetPicture(){
        User user = new User(username, password, emailAddress, phoneNumber);
        assertEquals(profilePicture, user.getPicture());
    }

    public void testSetPicture(){
        User user = new User(username, password, emailAddress, phoneNumber);
        user.setPicture(newPicture);
        assertEquals(newPicture, user.getPicture());
    }
}

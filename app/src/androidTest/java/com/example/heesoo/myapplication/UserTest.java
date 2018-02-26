package com.example.heesoo.myapplication;

import android.media.Image;
import android.test.ActivityInstrumentationTestCase2;

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
    private String address = "11111 11Ave, NW, Edmonton, AB";
    private Image profilePicture;

    public void testGetUsername(){
        User user = new User(username, password, emailAddress, address, profilePicture);
        assertEquals(username, user.getUsername());
    }

    public void testSetUsername(){
        User user = new User(username, password, emailAddress, address, profilePicture);
        user.setUsername("newUserName");
        assertEquals("newUserName", user.getUsername());
    }

    public void testGetPassword(){
        User user = new User(username, password, emailAddress, address, profilePicture);
        assertEquals(password, user.getPassword());
    }

    public void testSetPassword(){
        User user = new User(username, password, emailAddress, address, profilePicture);
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    public void testGetEmail(){
        User user = new User(username, password, emailAddress, address, profilePicture);
        assertEquals(emailAddress, user.getEmail());
    }

    public void testSetEmail(){
        User user = new User(username, password, emailAddress, address, profilePicture);
        user.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", user.getEmail());
    }

    public void testGetAddress(){
        User user = new User(username, password, emailAddress, address, profilePicture);
        assertEquals(address, user.getAddress());
    }

    public void testSetAddress(){
        User user = new User(username, password, emailAddress, address, profilePicture);
        user.setAddress("newAddress");
        assertEquals("newAddress", user.getAddress());
    }
}

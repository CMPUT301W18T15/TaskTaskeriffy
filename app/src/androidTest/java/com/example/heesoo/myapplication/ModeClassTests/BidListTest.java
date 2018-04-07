package com.example.heesoo.myapplication.ModeClassTests;

import android.test.ActivityInstrumentationTestCase2;

import com.example.heesoo.myapplication.entities.Bid;
import com.example.heesoo.myapplication.entities.BidList;
import com.example.heesoo.myapplication.entities.Task;
import com.example.heesoo.myapplication.entities.TaskList;
import com.example.heesoo.myapplication.entities.User;

/**
 * Created by chengze on 2018/4/7.
 */

public class BidListTest extends ActivityInstrumentationTestCase2 {
    public BidListTest() {
        super(Bid.class);
    }

    private String username = "Username1";
    private String password = "Password";
    private String email = "email@email.com";
    private String phoneNumber = "0000000000";

    private String TaskName = "Task1";
    private String Description = "TaskDescription";
    private Float BidPrice = 1f;
    private String TaskProvider = "TaskProvider";
    private String TaskRequester = "TaskRequester";

    public void testAdd() {
        User taskRequester = new User(username, password, email, phoneNumber);
        Bid bid = new Bid(TaskName, Description, BidPrice, TaskProvider, TaskRequester);
        BidList bidList = new BidList();
        bidList.add(bid);
        assertEquals(bidList.get(0), bid);

    }

    public void testRemove() {
        User taskRequester = new User(username, password, email, phoneNumber);
        Bid bid = new Bid(TaskName, Description, BidPrice, TaskProvider, TaskRequester);
        BidList bidList = new BidList();
        bidList.add(bid);
        assertEquals(bidList.get(0), bid);
        bidList.remove(0);
        assertTrue(bidList.size() == 0);
    }

}

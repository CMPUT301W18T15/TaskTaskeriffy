package com.example.heesoo.myapplication.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by manuelakm on 2018-04-06.
 */

public class BidList implements Serializable {

    private ArrayList<Bid> bids;

    public BidList() {

        bids = new ArrayList<Bid>();
    }

    public void add(Bid b) {

        bids.add(b);
    }

    public void remove(int index) {

        bids.remove(index);
    }

    public Bid get(int i) {

        return bids.get(i);

    }

    public void addAll(Collection<Bid> b) {

        bids.addAll(b);
    }

    public int size() {

        return bids.size();
    }

    public boolean contains(Bid b) {
        return bids.contains(b);
    }

    public int indexOf(Bid b) {
        return bids.indexOf(b);
    }

    public void clear() {
        bids.clear();
    }

    public boolean isEmpty() {
        return bids.isEmpty();
    }

}

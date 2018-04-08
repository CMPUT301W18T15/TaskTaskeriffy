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

    public Bid bidByUsername(String username){
        for(int i =0; i<this.size(); i++){
            Bid bid = this.get(i);
            String bidProvider = bid.getTaskProvider();
            if(username.equals(bidProvider)) return bid;
        }
        return null;
    }

    public int indexOf(Bid b) {
        int index =bids.indexOf(b);
        return index;
    }

    public void clear() {
        bids.clear();
    }

    public boolean isEmpty() {
        return bids.isEmpty();
    }

}

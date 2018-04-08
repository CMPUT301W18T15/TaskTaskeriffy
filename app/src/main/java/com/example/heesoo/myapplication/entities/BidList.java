package com.example.heesoo.myapplication.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by manuelakm on 2018-04-06.
 */

public class BidList implements Serializable {

    private ArrayList<Bid> bids;

    /**
     * <p>
     *     A constructor that creates a BidList; this BidList contains an internal ArrayList<Bid>.
     * </p>
     *
     * @since       1.0
     */
    public BidList() {

        bids = new ArrayList<Bid>();
    }

    /**
     * <p>
     *     A method that adds a bid to the BidList.
     * </p>
     * @param b the bid object that is to be added to the BidList
     */
    public void add(Bid b) {

        bids.add(b);
    }

    /**
     * <p>
     *     A method that removes a bid to the BidList.
     * </p>
     * @param index the int that represents the index of the BidList that is to be removed
     */
    public void remove(int index) {

        bids.remove(index);
    }

    /**
     * <p>
     *     A method that returns the bid at the given index of the BidList.
     * </p>
     * @param i the index of the BidList that is to be returned
     * @return the bid at position i of the BidList
     */
    public Bid get(int i) {

        return bids.get(i);

    }

    /**
     * <p>
     *     A method that adds all the bids in the given collection to the BidList.
     * </p>
     * @param b a collection of bids that is to be added to the BidList
     */
    public void addAll(Collection<Bid> b) {

        bids.addAll(b);
    }

    /**
     * <p>
     *     A method that returns the number of Bids in the BidList
     * </p>
     *
     * @return an int that represents the number of bids in the BidList
     */
    public int size() {

        return bids.size();
    }

    /**
     * <p>
     *     A method that returns if the given Bid is contained in the BidList.
     * </p>
     * @param b the bid that is to be checked
     * @return a boolean that represents whether a bid is inside the BidList or not
     */
    public boolean contains(Bid b) {

        return bids.contains(b);
    }

    /**
     * <p>
     *     A method that returns the bid associated with a given user
     * </p>
     * @param username a string that represents the user to be checked for
     * @return the bid placed by this user (null if no bid is found)
     */
    public Bid bidByUsername(String username){
        for(int i =0; i<this.size(); i++){
            Bid bid = this.get(i);
            String bidProvider = bid.getTaskProvider();
            if(username.equals(bidProvider)) return bid;
        }
        return null;
    }

    /**
     * <p>
     *     A method that returns the index of a bid in the BidList
     * </p>
     * @param b the bid to be searched for
     * @return the int that represents the index of the given bid
     */
    public int indexOf(Bid b) {
        int index =bids.indexOf(b);
        return index;
    }

    /**
     * <p>
     *     A method that clears the entire BidList
     * </p>
     */
    public void clear() {

        bids.clear();
    }

    /**
     * <p>
     *     A method that checks if the BidList contains any bids
     * </p>
     * @return boolean value that represents if a BidList is empty or not
     */
    public boolean isEmpty() {

        return bids.isEmpty();
    }

}

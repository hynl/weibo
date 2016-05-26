package com.hynl.weibo.hynlentity.response;

import java.util.ArrayList;

/**
 * Created by tt6000 on 2016/4/28.
 */
public class Favorite2Response {

    private ArrayList<Favorite> favorites;
    private int total_number;

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public void setFavorites(ArrayList<Favorite> favorites) {

        this.favorites = favorites;
    }

    public int getTotal_number() {

        return total_number;
    }

    public ArrayList<Favorite> getFavorites() {

        return favorites;
    }
}

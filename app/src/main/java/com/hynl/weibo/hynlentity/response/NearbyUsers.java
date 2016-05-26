package com.hynl.weibo.hynlentity.response;

import com.hynl.weibo.hynlentity.User;

import java.util.ArrayList;

/**
 * Created by tt6000 on 2016/5/8.
 */
public class NearbyUsers {
    ArrayList<User> users;
    int total_number;

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public int getTotal_number() {

        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }
}

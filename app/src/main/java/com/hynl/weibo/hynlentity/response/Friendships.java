package com.hynl.weibo.hynlentity.response;

import com.hynl.weibo.hynlentity.User;

import java.util.ArrayList;

/**
 * Created by tt6000 on 2016/5/5.
 */
public class Friendships {
    public ArrayList<User> users;
    public int total_number;
    public int next_cursor;

    public void setNext_cursor(int next_cursor) {
        this.next_cursor = next_cursor;
    }

    public int getNext_cursor() {

        return next_cursor;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void setTotal_number(int total_number) {

        this.total_number = total_number;
    }

    public ArrayList<User> getUsers() {

        return users;
    }

    public int getTotal_number() {

        return total_number;
    }
}

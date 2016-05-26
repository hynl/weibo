package com.hynl.weibo.hynlentity.response;

import com.hynl.weibo.hynlentity.Comment;

import java.util.ArrayList;

/**
 * Created by tt6000 on 2016/5/1.
 */
public class MentionsResponse {
    ArrayList<Comment> comments;
    int total_number;

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public void setComments(ArrayList<Comment> comments) {

        this.comments = comments;
    }

    public int getTotal_number() {

        return total_number;
    }

    public ArrayList<Comment> getComments() {

        return comments;
    }
}

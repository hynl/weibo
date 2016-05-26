package com.hynl.weibo.hynlentity.response;

import com.hynl.weibo.hynlentity.Comment;

import java.util.List;
/**
 * Created by tt6000 on 2016/3/11.
 */
public class CommentsResponse {
	private List<Comment> comments;
	private int total_number;

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public int getTotal_number() {
		return total_number;
	}

	public void setTotal_number(int total_number) {
		this.total_number = total_number;
	}

}
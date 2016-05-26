package com.hynl.weibo.hynlentity.response;

import com.hynl.weibo.hynlentity.Status;

import java.util.ArrayList;

/**
 * Created by tt6000 on 2016/3/11.
 */
public class StatusTimeLineResponse {
//	所有关注人微博信息

	private ArrayList<Status> statuses;
	private int total_number;

	public ArrayList<Status> getStatuses() {
		return statuses;
	}

	public void setStatuses(ArrayList<Status> statuses) {
		this.statuses = statuses;
	}

	public int getTotal_number() {
		return total_number;
	}

	public void setTotal_number(int total_number) {
		this.total_number = total_number;
	}

}

package com.hynl.weibo.hynlfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hynl.weibo.BaseFragment;
import com.hynl.weibo.R;
import com.hynl.weibo.hynlactivity.CommentsActivity;
import com.hynl.weibo.hynlactivity.MyReceiveCommentActivity;

/**
 * Created by tt6000 on 2016/3/15.
 */
public class MessageFragment extends BaseFragment implements View.OnClickListener{

	private View view;
	private LinearLayout comments;
	private LinearLayout mentions;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(activity, R.layout.frag_message, null);
		comments= (LinearLayout) view.findViewById(R.id.comments_me);
		mentions= (LinearLayout) view.findViewById(R.id.mentions_comm);
		mentions.setOnClickListener(this);
		comments.setOnClickListener(this);
		


		
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.comments_me:
				startActivity(new Intent(getActivity(), MyReceiveCommentActivity.class));
				break;
			case R.id.mentions_comm:
				startActivity(new Intent(getActivity(), CommentsActivity.class));
			default:break;
		}
	}
}

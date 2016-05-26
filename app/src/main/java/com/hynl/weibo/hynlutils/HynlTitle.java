package com.hynl.weibo.hynlutils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.hynl.weibo.R;
/**
 * Created by tt6000 on 2016/3/15.
 */
public class HynlTitle {

	private View viewTitle;
	private TextView tvTitle;
	private ImageView ivLeft;
	private ImageView ivRight;
	private TextView tvLeft;
	private TextView tvRight;

	public HynlTitle(Activity context) {
		viewTitle = context.findViewById(R.id.rl_titlebar);
		tvTitle = (TextView) viewTitle.findViewById(R.id.titlebar_tv);
		ivLeft = (ImageView) viewTitle.findViewById(R.id.titlebar_iv_left);
		ivRight = (ImageView) viewTitle.findViewById(R.id.titlebar_iv_right);
		tvLeft = (TextView) viewTitle.findViewById(R.id.titlebar_tv_left);
		tvRight = (TextView) viewTitle.findViewById(R.id.titlebar_tv_right);
	}
	
	public HynlTitle(View context) {
		viewTitle = context.findViewById(R.id.rl_titlebar);
		tvTitle = (TextView) viewTitle.findViewById(R.id.titlebar_tv);
		ivLeft = (ImageView) viewTitle.findViewById(R.id.titlebar_iv_left);
		ivRight = (ImageView) viewTitle.findViewById(R.id.titlebar_iv_right);
		tvLeft = (TextView) viewTitle.findViewById(R.id.titlebar_tv_left);
		tvRight = (TextView) viewTitle.findViewById(R.id.titlebar_tv_right);
	}

	// title

	public HynlTitle setTitleBgRes(int resid) {
		viewTitle.setBackgroundResource(resid);
		return this;
	}

	public HynlTitle setTitleText(String text) {
		tvTitle.setVisibility(TextUtils.isEmpty(text) ? View.GONE
				: View.VISIBLE);
		tvTitle.setText(text);
		return this;
	}

	// left

	public HynlTitle setLeftImage(int resId) {
		ivLeft.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
		ivLeft.setImageResource(resId);
		return this;
	}

	public HynlTitle setLeftText(String text) {
		tvLeft.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
		tvLeft.setText(text);
		return this;
	}

	public HynlTitle setLeftOnClickListener(OnClickListener listener) {
		if (ivLeft.getVisibility() == View.VISIBLE) {
			ivLeft.setOnClickListener(listener);
		} else if (tvLeft.getVisibility() == View.VISIBLE) {
			tvLeft.setOnClickListener(listener);
		}
		return this;
	}

	// right

	public HynlTitle setRightImage(int resId) {
		ivRight.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
		ivRight.setImageResource(resId);
		return this;
	}

	public HynlTitle setRightText(String text) {
		tvRight.setVisibility(TextUtils.isEmpty(text) ? View.GONE
				: View.VISIBLE);
		tvRight.setText(text);
		return this;
	}

	public HynlTitle setRightOnClickListener(OnClickListener listener) {
		if (ivRight.getVisibility() == View.VISIBLE) {
			ivRight.setOnClickListener(listener);
		} else if (tvRight.getVisibility() == View.VISIBLE) {
			tvRight.setOnClickListener(listener);
		}
		return this;
	}

	public View build() {
		return viewTitle;
	}

}

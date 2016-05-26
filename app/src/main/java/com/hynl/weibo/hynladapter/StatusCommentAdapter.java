package com.hynl.weibo.hynladapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hynl.weibo.R;
import com.hynl.weibo.hynlactivity.UserInfoActivity;
import com.hynl.weibo.hynlconstants.AccessTokenKeeper;
import com.hynl.weibo.hynlentity.Comment;
import com.hynl.weibo.hynlentity.User;
import com.hynl.weibo.hynlutils.DateUtils;
import com.hynl.weibo.hynlutils.StringUtils;
import com.hynl.weibo.hynlutils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.openapi.legacy.ActivityInvokeAPI;

import java.util.List;
/**
 * Created by tt6000 on 2016/4/4.
 */
public class StatusCommentAdapter extends BaseAdapter {
	Oauth2AccessToken mAccess2Token;
	
	private Context context;
	private List<Comment> comments;
	private ImageLoader imageLoader;

	public StatusCommentAdapter(Context context, List<Comment> comments) {
		this.context = context;
		this.comments = comments;
		this.imageLoader = ImageLoader.getInstance();
		mAccess2Token = AccessTokenKeeper.readAccessToken(context);
	}
	
	@Override
	public int getCount() {
		return comments.size();
	}

	@Override
	public Comment getItem(int position) {
		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderList holder;
		if (convertView == null) {
			holder = new ViewHolderList();
			convertView = View.inflate(context, R.layout.item_comment, null);
			holder.ll_comments = (LinearLayout) convertView
					.findViewById(R.id.ll_comments);
			holder.iv_avatar = (ImageView) convertView
					.findViewById(R.id.iv_avatar);
			holder.tv_subhead = (TextView) convertView
					.findViewById(R.id.tv_subhead);
			holder.tv_caption = (TextView) convertView
					.findViewById(R.id.tv_caption);
			holder.tv_comment = (TextView) convertView
					.findViewById(R.id.tv_comment);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolderList) convertView.getTag();
		}

		Comment comment = getItem(position);
		final User user = comment.getUser();
		
		imageLoader.displayImage(user.getProfile_image_url(), holder.iv_avatar);
		holder.tv_subhead.setText(user.getName());
		holder.tv_caption.setText(DateUtils.getShortTime(comment.getCreated_at()));
		SpannableString weiboContent = StringUtils.getWeiboContent(
				context, holder.tv_comment, comment.getText());
		holder.tv_comment.setText(weiboContent);
		
		holder.iv_avatar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(context, UserInfoActivity.class);
//				intent.putExtra("userName", user.getName());
//				context.startActivity(intent);
				if(user.getIdstr().equals(mAccess2Token.getUid())){
					Intent intent = new Intent(context, UserInfoActivity.class);
					intent.putExtra("userName", user.getScreen_name());
					context.startActivity(intent);
				}else{
					ActivityInvokeAPI.openUserInfoByUid((Activity) context, user.getIdstr());
				}
				ToastUtils.showToast(context,user.getName(),Toast.LENGTH_SHORT);
			}

		});
		
		holder.tv_subhead.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(context, UserInfoActivity.class);
//				intent.putExtra("userName", user.getName());
//				context.startActivity(intent);
				if(user.getIdstr().equals(mAccess2Token.getUid())){
					Intent intent = new Intent(context, UserInfoActivity.class);
					intent.putExtra("userName", user.getScreen_name());
					context.startActivity(intent);
				}else{
					ActivityInvokeAPI.openUserInfoByUid((Activity) context, user.getIdstr());
				}
				ToastUtils.showToast(context,user.getName(),Toast.LENGTH_SHORT);
			}
		});
		
		holder.ll_comments.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ToastUtils.showToast(context, "回复评论", Toast.LENGTH_SHORT);
			}
		});
		
		return convertView;
	}
	
	public static class ViewHolderList {
		public LinearLayout ll_comments;
		public ImageView iv_avatar;
		public TextView tv_subhead;
		public TextView tv_caption;
		public TextView tv_comment;
	}

}

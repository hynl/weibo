package com.hynl.weibo.hynlfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hynl.weibo.BaseApplication;
import com.hynl.weibo.BaseFragment;
import com.hynl.weibo.R;
import com.hynl.weibo.hynlactivity.UserInfoActivity;
import com.hynl.weibo.hynladapter.UserItemAdapter;
import com.hynl.weibo.hynlapi.HynlWeiboAPI;
import com.hynl.weibo.hynlapi.SimpleRequestListener;
import com.hynl.weibo.hynlconstants.AccessTokenKeeper;
import com.hynl.weibo.hynlentity.User;
import com.hynl.weibo.hynlentity.UserItem;
import com.hynl.weibo.hynlwidget.WrapHeightListView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by tt6000 on 2016/3/15.
 */
public class UserFragment extends BaseFragment {

	private LinearLayout ll_userinfo;
	
	private ImageView iv_avatar;
	private TextView tv_subhead;
	private TextView tv_caption;

	private TextView tv_status_count;
	private TextView tv_follow_count;
	private TextView tv_fans_count;
	
	private WrapHeightListView lv_user_items;

	private User user;
	private View view;

	private UserItemAdapter adapter;
	private List<UserItem> userItems;
	
	private HynlWeiboAPI weiboApi;
	private Oauth2AccessToken mAccessToken;
	private ImageLoader imageLoader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(getActivity(), R.layout.frag_user, null);
		
		weiboApi = new HynlWeiboAPI(activity);
		mAccessToken = AccessTokenKeeper.readAccessToken(activity);
		imageLoader = ImageLoader.getInstance();
		
		initView();
		
		setItem();

		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		// show/hide方法不会走生命周期
		System.out.println("user frag onStart()");
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		
		if(!hidden) {
			weiboApi.usersShow(mAccessToken.getUid(), "", 
					new SimpleRequestListener(activity, null) {

						@Override
						public void onComplete(String response) {
							super.onComplete(response);
							
							BaseApplication application = (BaseApplication) activity.getApplication();
							application.currentUser = user = new Gson().fromJson(response, User.class);
							
							setUserInfo();
						}
				
			});
		}
	}
	
	private void initView() {
		// 标题栏
		//new HynlTitle(view).setTitleText("我的页面").build();
		// 用户信息
		ll_userinfo = (LinearLayout) view.findViewById(R.id.ll_userinfo);
		ll_userinfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity, UserInfoActivity.class);
				startActivity(intent);
			}
		});
		iv_avatar = (ImageView) view.findViewById(R.id.iv_avatar);
		tv_subhead = (TextView) view.findViewById(R.id.tv_subhead);
		tv_caption = (TextView) view.findViewById(R.id.tv_caption);
		// 互动信息栏: 微博数、关粉丝注数、数
		tv_status_count = (TextView) view.findViewById(R.id.tv_status_count);
		tv_follow_count = (TextView) view.findViewById(R.id.tv_follow_count);
		tv_fans_count = (TextView) view.findViewById(R.id.tv_fans_count);
		// 设置栏列表
		lv_user_items = (WrapHeightListView) view.findViewById(R.id.lv_user_items);
		userItems = new ArrayList<UserItem>();
		adapter = new UserItemAdapter(activity, userItems);
		lv_user_items.setAdapter(adapter);
	}

	// 设置用户信息
	private void setUserInfo() {
		tv_subhead.setText(user.getName());
		tv_caption.setText("简介:" + user.getDescription());
		imageLoader.displayImage(user.getAvatar_large(), iv_avatar);
		tv_status_count.setText("" + user.getStatuses_count());
		tv_follow_count.setText("" + user.getFriends_count());
		tv_fans_count.setText("" + user.getFollowers_count());
	}
	
	// 设置栏列表
	private void setItem() {
		userItems.add(new UserItem(false, R.drawable.userfrg_new_friends, "我的关心好友亲", "75"));
		userItems.add(new UserItem(false, R.drawable.userfrg_contacts, "我的粉丝后援军", "55"));
		userItems.add(new UserItem(false, R.drawable.userfrg_favourate_songs, "我的亲朋通讯录", "175"));
		userItems.add(new UserItem(true, R.drawable.userfrag_my_message, "我的绕梁音乐汇", "陈奕迅"));
		userItems.add(new UserItem(false, R.drawable.userfrg_diary, "我的微博收藏夹", ""));
		userItems.add(new UserItem(false, R.drawable.userfrg_tools, "我的未读消息数", ""));
		userItems.add(new UserItem(true, R.drawable.user_frag_collection, "我的收藏微博", ""));
		userItems.add(new UserItem(false, R.drawable.userfrg_new_friends, "更多朋友", ""));
		userItems.add(new UserItem(true, R.drawable.userfrg_tools, "还有其他", "继续探索"));
		adapter.notifyDataSetChanged();
	}

}

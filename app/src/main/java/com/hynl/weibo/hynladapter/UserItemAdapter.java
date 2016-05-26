package com.hynl.weibo.hynladapter;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hynl.weibo.R;
import com.hynl.weibo.hynlactivity.CollectionActivity;
import com.hynl.weibo.hynlactivity.FollowersActivity;
import com.hynl.weibo.hynlactivity.MyNewFriendsActivity;
import com.hynl.weibo.hynlactivity.MyplayActivity;
import com.hynl.weibo.hynlactivity.RemindsActivity;
import com.hynl.weibo.hynlconstants.AccessTokenKeeper;
import com.hynl.weibo.hynlentity.UserItem;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.List;
/**
 * Created by tt6000 on 2016/4/6.
 */
public class UserItemAdapter extends BaseAdapter {
	Oauth2AccessToken mAccess2Token;

	private Context context;
	private List<UserItem> datas;

	public UserItemAdapter(Context context, List<UserItem> datas) {
		this.context = context;
		this.datas = datas;
		mAccess2Token = AccessTokenKeeper.readAccessToken(context);
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public UserItem getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_user, null);
			holder.v_divider = convertView.findViewById(R.id.v_divider);
			holder.ll_content = convertView.findViewById(R.id.ll_content);
			holder.iv_left = (ImageView) convertView.findViewById(R.id.iv_left);
			holder.tv_subhead = (TextView) convertView.findViewById(R.id.tv_subhead);
			holder.tv_caption = (TextView) convertView.findViewById(R.id.tv_caption);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// set data
		UserItem item = getItem(position);
		holder.iv_left.setImageResource(item.getLeftImg());
		holder.tv_subhead.setText(item.getSubhead());
		holder.tv_caption.setText(item.getCaption());

		holder.v_divider.setVisibility(item.isShowTopDivider() ?
				View.VISIBLE : View.GONE);
		holder.ll_content.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch(position){
					case 0:
                    context.startActivity(new Intent(context, MyNewFriendsActivity.class));
						break;
					case 1:
						context.startActivity(new Intent(context,FollowersActivity.class));

						break;
					case 2:
				Intent intent = new Intent();

						intent.setAction(Intent.ACTION_PICK);

						intent.setData(ContactsContract.Contacts.CONTENT_URI);
						context.startActivity(intent);
						break;

					case 3:

						break;
					case 4:
//						ActivityInvokeAPI.openContact((Activity)context);
						context.startActivity(new Intent(context, CollectionActivity.class));
						break;
					case 5:
						context.startActivity(new Intent(context, RemindsActivity.class));
						break;
					case 6:

						break;
					case 7:
						context.startActivity(new Intent(context, MyplayActivity.class));
						break;
					default:break;
				}
//				ToastUtils.showToast(context, "item click position = " + position, Toast.LENGTH_SHORT);
			}
		});

		
		return convertView;
	}


	public static class ViewHolder{
		public View v_divider;
		public View ll_content;
		public ImageView iv_left;
		public TextView tv_subhead;
		public TextView tv_caption;
	}


}

package com.hynl.weibo.hynladapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.hynl.weibo.R;
import com.hynl.weibo.hynlentity.User;
import com.hynl.weibo.hynlutils.DateUtils;
import com.hynl.weibo.hynlutils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by tt6000 on 2016/5/7.
 */
public class SurroundAdapter extends BaseAdapter {
    ArrayList<User> users;
    Context context;
    ImageLoader imageLoader;
    public SurroundAdapter(Context context, ArrayList<User> users){
       this.users=users;
        this.context=context;
        imageLoader=ImageLoader.getInstance();

    }
    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public  User getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderList viewHolder = new ViewHolderList();
        if(convertView==null){
            convertView=View.inflate(context,R.layout.item_people,null);
            viewHolder.iv_avatar= (ImageView) convertView.findViewById(R.id.iv_avatar);
            viewHolder.tv_avatar= (TextView) convertView.findViewById(R.id.tv_avatar);
            viewHolder.tv_sub= (TextView) convertView.findViewById(R.id.tv_sub);
            viewHolder.tv_cap= (TextView) convertView.findViewById(R.id.tv_cap);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolderList) convertView.getTag();
        }
        User user = users.get(position);
        imageLoader.displayImage(user.getProfile_image_url(),viewHolder.iv_avatar);
        StringUtils.getWeiboContent(context, viewHolder.tv_cap, user.getDistance() + "");
        viewHolder.tv_avatar.setText(StringUtils.getWeiboContent(context, viewHolder.tv_avatar, user.getName()));

        viewHolder.tv_sub.setText(StringUtils.getWeiboContent(context, viewHolder.tv_sub, user.getDescription()));
        viewHolder.tv_cap.setText(user.getLast_at()+"  "+user.getDistance()+"m以内");

        return convertView;
    }
    public static class ViewHolderList{
        public ImageView iv_avatar;
        public TextView tv_avatar;
        public TextView tv_sub;
        public TextView tv_cap;
    }
}

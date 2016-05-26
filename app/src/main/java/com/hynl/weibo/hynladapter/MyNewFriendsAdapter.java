package com.hynl.weibo.hynladapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.hynl.weibo.R;

import com.hynl.weibo.hynlentity.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


/**
 * Created by tt6000 on 2016/5/5.
 */
public class MyNewFriendsAdapter extends BaseAdapter{
    Context context;
    ArrayList<User> users;
    ImageLoader imageLoader;

    public MyNewFriendsAdapter(Context context,ArrayList<User> users){

        this.context=context;
        this.users=users;
        imageLoader=ImageLoader.getInstance();
    }
    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
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
            convertView=View.inflate(context,R.layout.item_mynewfriends,null);
            viewHolder.iv_avatar= (ImageView) convertView.findViewById(R.id.iv_avatar);
            viewHolder.tv_subhead= (TextView) convertView.findViewById(R.id.tv_subhead);
            viewHolder.tv_caption= (TextView) convertView.findViewById(R.id.tv_caption);
            convertView.setTag(viewHolder);
        }else{
           viewHolder= (ViewHolderList) convertView.getTag();
        }
        User user = users.get(position);
        imageLoader.displayImage(user.getProfile_image_url(),viewHolder.iv_avatar);
        viewHolder.tv_subhead.setText(user.getName());
        viewHolder.tv_caption.setText(user.getDescription());

        return convertView;
    }
    public static class ViewHolderList{
        public ImageView iv_avatar;
        public TextView tv_subhead;
        public TextView tv_caption;
    }
}

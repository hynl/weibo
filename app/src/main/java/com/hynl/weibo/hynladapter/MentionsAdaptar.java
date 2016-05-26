package com.hynl.weibo.hynladapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hynl.weibo.R;
import com.hynl.weibo.hynlactivity.StatusDetailActivity;
import com.hynl.weibo.hynlactivity.WriteCommentActivity;
import com.hynl.weibo.hynlactivity.WriteStatusActivity;
import com.hynl.weibo.hynlentity.Comment;

import com.hynl.weibo.hynlentity.Status;
import com.hynl.weibo.hynlentity.User;
import com.hynl.weibo.hynlutils.DateUtils;
import com.hynl.weibo.hynlutils.StringUtils;
import com.hynl.weibo.hynlutils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


/**
 * Created by tt6000 on 2016/5/1.
 */
public class MentionsAdaptar extends BaseAdapter {
    private Context context;
    private ArrayList<Comment> comments;
    private ImageLoader imageLoader;

    public MentionsAdaptar(Context context, ArrayList<Comment> datas) {
        this.context = context;
        this.comments = datas;
        imageLoader = ImageLoader.getInstance();
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
        ViewHolderList holder = new ViewHolderList();
        if(convertView==null){

            convertView=View.inflate(context, R.layout.item_mentions,null);
            holder.ll_mentions= (LinearLayout) convertView.findViewById(R.id.ll_comments);
            holder.ll_avatar_mentions= (LinearLayout) convertView.findViewById(R.id.ll_avatar_mentions);
            holder.ll_re_card= (LinearLayout) convertView.findViewById(R.id.ll_re_card);
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            holder.tv_caption= (TextView) convertView.findViewById(R.id.tv_caption);
            holder.tv_subhead= (TextView) convertView.findViewById(R.id.tv_subhead);
            holder.tv_content= (TextView) convertView.findViewById(R.id.tv_content);
            holder.iv_rstatus_img= (ImageView) convertView.findViewById(R.id.iv_rstatus_img);
            holder.tv_rstatus_username= (TextView) convertView.findViewById(R.id.tv_rstatus_username);
            holder.tv_rstatus_content= (TextView) convertView.findViewById(R.id.tv_rstatus_content);
            holder.ll_share_bottom= (LinearLayout) convertView.findViewById(R.id.ll_share_bottom);
            holder.ll_comment_bottom= (LinearLayout) convertView.findViewById(R.id.ll_comment_bottom);
            holder.ll_like_bottom= (LinearLayout) convertView.findViewById(R.id.ll_like_bottom);
            holder.tv_share_bottom= (TextView) convertView.findViewById(R.id.tv_share_bottom);
            holder.tv_like_bottom= (TextView) convertView.findViewById(R.id.tv_like_bottom);
            holder.tv_comment_bottom= (TextView) convertView.findViewById(R.id.tv_comment_bottom);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolderList) convertView.getTag();
        }
        Comment comment = getItem(position);
        final User user = comment.getUser();
        final Status status = comment.getStatus();
        holder.ll_avatar_mentions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        Intent intent = new Intent(context, StatusDetailActivity.class);
                        intent.putExtra("status", status);
                        context.startActivity(intent);
                    }
                });
       holder.ll_re_card.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context, StatusDetailActivity.class);
               intent.putExtra("status", status.getRetweeted_status());
               context.startActivity(intent);
           }
       });

        imageLoader.displayImage(user.getProfile_image_url(), holder.iv_avatar);
        holder.tv_subhead.setText(user.getName());
        holder.tv_caption.setText(DateUtils.getShortTime(status.getCreated_at())
                + " 来自 " + Html.fromHtml(status.getSource()));
        SpannableString weiboContent = StringUtils.getWeiboContent(
                context, holder.tv_content, comment.getText());
        holder.tv_content.setText(weiboContent);
        String imgUrl = status.getThumbnail_pic();
        if(TextUtils.isEmpty(imgUrl)) {
            holder.iv_rstatus_img.setVisibility(View.GONE);
        } else {
            holder.iv_rstatus_img.setVisibility(View.VISIBLE);
            imageLoader.displayImage(imgUrl, holder.iv_rstatus_img);
        }

        SpannableString re_avatar = StringUtils.getWeiboContent(context,holder.tv_rstatus_username,"@" + status.getUser().getName());
        holder.tv_rstatus_username.setText(re_avatar);
        SpannableString re_content = StringUtils.getWeiboContent(context,holder.tv_rstatus_content,status.getText());
        holder.tv_rstatus_content.setText(re_content);
        holder.tv_share_bottom.setText(status.getReposts_count() == 0 ?
                "转发" : status.getReposts_count() + "");

        holder.tv_comment_bottom.setText(status.getComments_count() == 0 ?
                "评论" : status.getComments_count() + "");

        holder.tv_like_bottom.setText(status.getAttitudes_count() == 0 ?
                "赞" : status.getAttitudes_count() + "");
        holder.ll_share_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WriteStatusActivity.class);
                intent.putExtra("status", status);
                context.startActivity(intent);
            }
        });
        holder.ll_comment_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.getComments_count() > 0) {
                    Intent intent = new Intent(context, StatusDetailActivity.class);
                    intent.putExtra("status", status);
                    intent.putExtra("scroll2Comment", true);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, WriteCommentActivity.class);
                    intent.putExtra("status", status);
                    context.startActivity(intent);
                }
                ToastUtils.showToast(context, "评个论~", Toast.LENGTH_SHORT);
            }
        });

        holder.ll_like_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(context, "点个赞~", Toast.LENGTH_SHORT);

            }
        });

        return convertView;
    }

    public static class ViewHolderList{
        public LinearLayout ll_mentions;
        public LinearLayout ll_avatar_mentions;
        //user
        public ImageView iv_avatar;
        public TextView tv_subhead;
        public TextView tv_caption;
        public TextView tv_content;
        //
        public LinearLayout ll_re_card;
        public ImageView iv_rstatus_img;
        public TextView tv_rstatus_username;
        public TextView tv_rstatus_content;
        //
        public LinearLayout ll_share_bottom;
        public LinearLayout ll_comment_bottom;
        public LinearLayout ll_like_bottom;
        public TextView tv_share_bottom;
        public TextView tv_comment_bottom;
        public TextView tv_like_bottom;
    }
}

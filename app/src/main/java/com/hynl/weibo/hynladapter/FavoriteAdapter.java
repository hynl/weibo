package com.hynl.weibo.hynladapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hynl.weibo.R;
import com.hynl.weibo.hynlactivity.ImageBrowserActivity;
import com.hynl.weibo.hynlactivity.StatusDetailActivity;
import com.hynl.weibo.hynlactivity.UserInfoActivity;
import com.hynl.weibo.hynlactivity.WriteCommentActivity;
import com.hynl.weibo.hynlactivity.WriteStatusActivity;
import com.hynl.weibo.hynlapi.HynlWeiboAPI;
import com.hynl.weibo.hynlapi.SimpleRequestListener;
import com.hynl.weibo.hynlconstants.AccessTokenKeeper;
import com.hynl.weibo.hynlentity.PicUrls;
import com.hynl.weibo.hynlentity.Status;
import com.hynl.weibo.hynlentity.User;
import com.hynl.weibo.hynlentity.response.Favorite;
import com.hynl.weibo.hynlutils.DateUtils;
import com.hynl.weibo.hynlutils.StringUtils;
import com.hynl.weibo.hynlutils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.openapi.legacy.ActivityInvokeAPI;

import java.util.ArrayList;


/**
 * Created by tt6000 on 2016/4/28.
 */
public class FavoriteAdapter extends BaseAdapter {
    Oauth2AccessToken mAccess2Token;
    private Context context;
    private ArrayList<Favorite> favorites;
    private ImageLoader imageLoader;
    public FavoriteAdapter(Context context, ArrayList<Favorite> datas) {
        this.context = context;
        this.favorites = datas;
        imageLoader = ImageLoader.getInstance();
        mAccess2Token= AccessTokenKeeper.readAccessToken(context);
    }


    @Override
    public int getCount() {
        return favorites.size();
    }

    @Override
    public Object getItem(int position) {
        return favorites.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_status, null);
            holder.ll_card_content = (LinearLayout) convertView
                    .findViewById(R.id.ll_card_content);
            holder.iv_avatar = (ImageView) convertView
                    .findViewById(R.id.iv_avatar);
            holder.rl_content = (RelativeLayout) convertView
                    .findViewById(R.id.rl_content);
            holder.iv_collection = (ImageView) convertView.findViewById(R.id.collection_item);
            holder.tv_subhead = (TextView) convertView
                    .findViewById(R.id.tv_subhead);
            holder.tv_caption = (TextView) convertView
                    .findViewById(R.id.tv_caption);

            holder.tv_content = (TextView) convertView
                    .findViewById(R.id.tv_content);
            holder.include_status_image = (FrameLayout) convertView
                    .findViewById(R.id.include_status_image);
            holder.gv_images = (GridView) holder.include_status_image
                    .findViewById(R.id.gv_images);
            holder.iv_image = (ImageView) holder.include_status_image
                    .findViewById(R.id.iv_image);

            holder.include_retweeted_status = (LinearLayout) convertView
                    .findViewById(R.id.include_retweeted_status);
            holder.tv_retweeted_content = (TextView) holder.include_retweeted_status
                    .findViewById(R.id.tv_retweeted_content);
            holder.include_retweeted_status_image = (FrameLayout) holder.include_retweeted_status
                    .findViewById(R.id.include_status_image);
            holder.gv_retweeted_images = (GridView) holder.include_retweeted_status_image
                    .findViewById(R.id.gv_images);
            holder.iv_retweeted_image = (ImageView) holder.include_retweeted_status_image
                    .findViewById(R.id.iv_image);

            holder.ll_share_bottom = (LinearLayout) convertView
                    .findViewById(R.id.ll_share_bottom);
            holder.iv_share_bottom = (ImageView) convertView
                    .findViewById(R.id.iv_share_bottom);
            holder.tv_share_bottom = (TextView) convertView
                    .findViewById(R.id.tv_share_bottom);
            holder.ll_comment_bottom = (LinearLayout) convertView
                    .findViewById(R.id.ll_comment_bottom);
            holder.iv_comment_bottom = (ImageView) convertView
                    .findViewById(R.id.iv_comment_bottom);
            holder.tv_comment_bottom = (TextView) convertView
                    .findViewById(R.id.tv_comment_bottom);
            holder.ll_like_bottom = (LinearLayout) convertView
                    .findViewById(R.id.ll_like_bottom);
            holder.iv_like_bottom = (ImageView) convertView
                    .findViewById(R.id.iv_like_bottom);
            holder.tv_like_bottom = (TextView) convertView
                    .findViewById(R.id.tv_like_bottom);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // bind data
        final Status status =((Favorite)getItem(position)).getStatus();
        final User user = status.getUser();
        if(user==null){
            HynlWeiboAPI weiboapi = new HynlWeiboAPI(context);
            weiboapi.destroy(status.getId(),new SimpleRequestListener(context,null){
                @Override
                public void onComplete(String response) {
                    super.onComplete(response);
                    ToastUtils.showToast(context,"取消收藏成功",Toast.LENGTH_LONG);
                }
            });

        }

        imageLoader.displayImage(user.getProfile_image_url(), holder.iv_avatar);
        holder.tv_subhead.setText(user.getName());
        holder.iv_collection.setImageResource(R.drawable.icon_collection_press);
        holder.iv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HynlWeiboAPI weiboapi = new HynlWeiboAPI(context);
                weiboapi.destroy(status.getId(), new SimpleRequestListener(context, null) {
                    @Override
                    public void onComplete(String response) {
                        super.onComplete(response);
                        ToastUtils.showToast(context, "取消收藏成功", Toast.LENGTH_LONG);
                    }
                });
            }
        });
        holder.tv_caption.setText(DateUtils.getShortTime(status.getCreated_at())
                + " 来自 " + Html.fromHtml(status.getSource()));
        holder.tv_content.setText(StringUtils.getWeiboContent(
                context, holder.tv_content, status.getText()));

        holder.iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, UserInfoActivity.class);
//                intent.putExtra("userName", user.getName());
//                context.startActivity(intent);
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

        holder.tv_subhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, UserInfoActivity.class);
//                intent.putExtra("userName", user.getName());
//                context.startActivity(intent);
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

        setImages(status, holder.include_status_image, holder.gv_images, holder.iv_image);

        final Status retweeted_status = status.getRetweeted_status();
        if(retweeted_status != null) {
            User retUser = retweeted_status.getUser();

            holder.include_retweeted_status.setVisibility(View.VISIBLE);
            String retweetedContent = "@" + retUser.getName() + ":"
                    + retweeted_status.getText();
            holder.tv_retweeted_content.setText(StringUtils.getWeiboContent(
                    context, holder.tv_retweeted_content, retweetedContent));

            setImages(retweeted_status,
                    holder.include_retweeted_status_image,
                    holder.gv_retweeted_images, holder.iv_retweeted_image);
        } else {
            holder.include_retweeted_status.setVisibility(View.GONE);
        }

        holder.tv_share_bottom.setText(status.getReposts_count() == 0 ?
                "转发" : status.getReposts_count() + "");

        holder.tv_comment_bottom.setText(status.getComments_count() == 0 ?
                "评论" : status.getComments_count() + "");

        holder.tv_like_bottom.setText(status.getAttitudes_count() == 0 ?
                "赞" : status.getAttitudes_count() + "");

        holder.ll_card_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StatusDetailActivity.class);
                intent.putExtra("status", status);
                context.startActivity(intent);
            }
        });

        holder.include_retweeted_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StatusDetailActivity.class);
                intent.putExtra("status", retweeted_status);
                context.startActivity(intent);
            }
        });

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
                if(status.getComments_count() > 0) {
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

    private void setImages(final Status status, FrameLayout imgContainer,
                           GridView gv_images, ImageView iv_image) {
        ArrayList<PicUrls> pic_urls = status.getPic_urls();
        String thumbnail_pic = status.getThumbnail_pic();

        if(pic_urls != null && pic_urls.size() > 1) {
            imgContainer.setVisibility(View.VISIBLE);
            gv_images.setVisibility(View.VISIBLE);
            iv_image.setVisibility(View.GONE);

            StatusGridImgsAdapter gvAdapter = new StatusGridImgsAdapter(context, pic_urls);
            gv_images.setAdapter(gvAdapter);
            gv_images.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(context, ImageBrowserActivity.class);
                    intent.putExtra("status", status);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        } else if(thumbnail_pic != null) {
            imgContainer.setVisibility(View.VISIBLE);
            gv_images.setVisibility(View.GONE);
            iv_image.setVisibility(View.VISIBLE);

            imageLoader.displayImage(thumbnail_pic, iv_image);
            iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ImageBrowserActivity.class);
                    intent.putExtra("status", status);
                    context.startActivity(intent);
                }
            });
        } else {
            imgContainer.setVisibility(View.GONE);
        }
    }

    public static class ViewHolder {
        public LinearLayout ll_card_content;
        public ImageView iv_avatar;
        public RelativeLayout rl_content;
        public TextView tv_subhead;
        public TextView tv_caption;

        public TextView tv_content;
        public FrameLayout include_status_image;
        public GridView gv_images;
        public ImageView iv_image;

        public LinearLayout include_retweeted_status;
        public TextView tv_retweeted_content;
        public FrameLayout include_retweeted_status_image;
        public GridView gv_retweeted_images;
        public ImageView iv_retweeted_image;

        public LinearLayout ll_share_bottom;
        public ImageView iv_share_bottom;
        public TextView tv_share_bottom;
        public LinearLayout ll_comment_bottom;
        public ImageView iv_comment_bottom;
        public TextView tv_comment_bottom;
        public LinearLayout ll_like_bottom;
        public ImageView iv_like_bottom;
        public TextView tv_like_bottom;
        public ImageView iv_collection;
    }

}

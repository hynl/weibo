package com.hynl.weibo.hynlactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hynl.weibo.BaseActivity;
import com.hynl.weibo.R;
import com.hynl.weibo.hynladapter.MyNewFriendsAdapter;
import com.hynl.weibo.hynlapi.HynlWeiboAPI;
import com.hynl.weibo.hynlapi.SimpleRequestListener;
import com.hynl.weibo.hynlconstants.AccessTokenKeeper;
import com.hynl.weibo.hynlentity.User;
import com.hynl.weibo.hynlentity.response.Friendships;
import com.hynl.weibo.hynlutils.HynlTitle;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;

/**
 * Created by tt6000 on 2016/5/7.
 */
public class FollowersActivity extends BaseActivity implements View.OnClickListener{
    int next_cursor;
    MyNewFriendsAdapter adapter;
    PullToRefreshListView plv_friends;
    private Oauth2AccessToken mAccessToken;
    private View footView;
    private ArrayList<User> users = new ArrayList<User>();
    private int curPage = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfriends);
        View title = new HynlTitle(this)
                .setTitleText("我的关心好友亲")
                .setTitleBgRes(R.drawable.userinfo_navigationbar_background)
                .setLeftImage(R.drawable.navigationbar_back_sel)
                .setLeftOnClickListener(this)
                .build();
        mAccessToken= AccessTokenKeeper.readAccessToken(FollowersActivity.this);
        plv_friends = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_friends);
        adapter = new MyNewFriendsAdapter(FollowersActivity.this,users);
        plv_friends.setAdapter(adapter);
        plv_friends.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData(0);
            }
        });
        plv_friends.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {

                loadData(next_cursor);
            }
        });

        footView = View.inflate(FollowersActivity.this, R.layout.footview_loading, null);

        loadData(0);
    }
    private void loadData(final int page){
        HynlWeiboAPI weiboAPI = new HynlWeiboAPI(FollowersActivity.this);
        weiboAPI.followers(mAccessToken.getUid(), 100, page, new SimpleRequestListener(FollowersActivity.this, null) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                if (page == 0) {
                    users.clear();
                }
                curPage = page;
                addDate(new Gson().fromJson(response, Friendships.class));
            }

            @Override
            public void onAllDone() {
                super.onAllDone();
                plv_friends.onRefreshComplete();
            }
        });
    }
    public void addDate(Friendships friends){

        for(User user : friends.getUsers()) {
            if(!users.contains(user)) {
                users.add(user);
            }
        }
        next_cursor=friends.getNext_cursor();
        adapter.notifyDataSetChanged();

//        if(curPage < friends.getTotal_number()) {
//            addFootView(plv_friends, footView);
//        } else {
//            removeFootView(plv_friends, footView);
//        }
    }

    private void addFootView(PullToRefreshListView plv, View footView) {
        ListView lv = plv.getRefreshableView();
        if(lv.getFooterViewsCount() == 1) {
            lv.addFooterView(footView);
        }
    }

    private void removeFootView(PullToRefreshListView plv, View footView) {
        ListView lv = plv.getRefreshableView();
        if(lv.getFooterViewsCount() > 1) {
            lv.removeFooterView(footView);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            default:
                break;
        }
    }
}

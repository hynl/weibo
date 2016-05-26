package com.hynl.weibo.hynlactivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hynl.weibo.BaseApplication;
import com.hynl.weibo.R;
import com.hynl.weibo.BaseActivity;
import com.hynl.weibo.hynladapter.StatusAdapter;
import com.hynl.weibo.hynlapi.HynlWeiboAPI;
import com.hynl.weibo.hynlapi.SimpleRequestListener;
import com.hynl.weibo.hynlconstants.AccessTokenKeeper;
import com.hynl.weibo.hynlentity.Status;
import com.hynl.weibo.hynlentity.User;
import com.hynl.weibo.hynlentity.response.StatusTimeLineResponse;
import com.hynl.weibo.hynlutils.HynlTitle;
import com.hynl.weibo.hynlwidget.Pull2RefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tt6000 on 2016/4/28.
 */
public class MyReceiveCommentActivity extends BaseActivity implements View.OnClickListener{

    private Pull2RefreshListView plv_user_info;
    private View footView;
    private User user;
    private View title;
    private String userName;
    private boolean isCurrentUser;
    private Oauth2AccessToken mAccessToken;

    private List<Status> statuses = new ArrayList<Status>();
    private StatusAdapter statusAdapter;
    private long curPage = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_comments);
        weiboApi = new HynlWeiboAPI(this);
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        weiboApi.usersShow(mAccessToken.getUid(), "",
                new SimpleRequestListener(MyReceiveCommentActivity.this, null) {

                    @Override
                    public void onComplete(String response) {
                        super.onComplete(response);

                        BaseApplication application = (BaseApplication) MyReceiveCommentActivity.this.getApplication();
                        application.currentUser = user = new Gson().fromJson(response, User.class);

                    }

                });
        imageLoader = ImageLoader.getInstance();
        userName = getIntent().getStringExtra("userName");
        if(TextUtils.isEmpty(userName)) {
            isCurrentUser = true;
            user = application.currentUser;
        }
        initView();
        loadData();

    }
    private void initView() {
        title = new HynlTitle(this)
                .setTitleText("我的微博评论")
                .setTitleBgRes(R.drawable.userinfo_navigationbar_background)
                .setLeftImage(R.drawable.navigationbar_back_sel)
                .setLeftOnClickListener(this)
                .build();
        // 获取标题栏信息,需要时进行修改



        initListView();
    }
    private void initListView() {
        plv_user_info = (Pull2RefreshListView) findViewById(R.id.plv_user_info);
        footView = View.inflate(this, R.layout.footview_loading, null);

        statusAdapter = new StatusAdapter(this, statuses);
        plv_user_info.setAdapter(statusAdapter);

        plv_user_info.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadStatuses(1);
            }
        });
        plv_user_info.setOnLastItemVisibleListener(
                new PullToRefreshBase.OnLastItemVisibleListener() {

                    @Override
                    public void onLastItemVisible() {
                        loadStatuses(curPage + 1);
                    }
                });


    }
    private void loadData() {


        loadStatuses(1);
    }
    private void loadStatuses(final long requestPage) {
        weiboApi.statusesUser_timeline(
                user == null ? -1 : user.getId(),null, requestPage,
                new SimpleRequestListener(this, null) {

                    @Override
                    public void onComplete(String response) {
                        super.onComplete(response);

                        showLog("status comments = " + response);

                        if (requestPage == 1) {
                            statuses.clear();
                        }

                        addStatus(gson.fromJson(response, StatusTimeLineResponse.class));
                    }

                    @Override
                    public void onAllDone() {
                        super.onAllDone();

                        plv_user_info.onRefreshComplete();
                    }

                });
    }
    private void addStatus(StatusTimeLineResponse response) {
        for(Status status : response.getStatuses()) {
            if(!statuses.contains(status)) {
                statuses.add(status);
            }
        }
        statusAdapter.notifyDataSetChanged();

        if(curPage < response.getTotal_number()) {
            addFootView(plv_user_info, footView);
        } else {
            removeFootView(plv_user_info, footView);
        }
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

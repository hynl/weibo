package com.hynl.weibo.hynlactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hynl.weibo.BaseActivity;
import com.hynl.weibo.R;
import com.hynl.weibo.hynladapter.FavoriteAdapter;
import com.hynl.weibo.hynlapi.HynlWeiboAPI;
import com.hynl.weibo.hynlapi.SimpleRequestListener;
import com.hynl.weibo.hynlentity.response.Favorite;
import com.hynl.weibo.hynlentity.response.Favorite2Response;
import com.hynl.weibo.hynlutils.HynlTitle;

import java.util.ArrayList;

/**
 * Created by tt6000 on 2016/4/28.
 */
public class CollectionActivity extends BaseActivity implements View.OnClickListener{


    ArrayList<Favorite> favorites = new ArrayList<>();
    private PullToRefreshListView plv_home;
    private View footView;
    private FavoriteAdapter adapter;
    private int curPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_home);
        View title = new HynlTitle(this)
                .setTitleText("我的微博收藏夹")
                .setTitleBgRes(R.drawable.userinfo_navigationbar_background)
                .setLeftImage(R.drawable.navigationbar_back_sel)
                .setLeftOnClickListener(this)
                .build();
        initView();
        loadData(1);

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        initView();
//        loadData(1);
//        return view;
//    }

    private void initView() {

        plv_home = (PullToRefreshListView) findViewById(R.id.lv_home);

        plv_home.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData(1);
            }
        });
        plv_home.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                loadData(curPage + 1);
            }
        });

        footView = View.inflate(CollectionActivity.this, R.layout.footview_loading, null);

    }

    public void loadData(final int page) {
        HynlWeiboAPI api = new HynlWeiboAPI(this);

        api.favorites(50, page,
                new SimpleRequestListener(this, null) {

                    @Override
                    public void onComplete(String response) {
                        super.onComplete(response);

                        if (page == 1) {
//                            favorites.clear();//集合清空，加载第一页。
                        }

                        curPage = page;
                        addData(new Gson().fromJson(response, Favorite2Response.class));

                    }

                    @Override
                    public void onAllDone() {
                        super.onAllDone();

                        plv_home.onRefreshComplete();
                    }

                });
    }

    private void addData(Favorite2Response responseBean) {
        favorites = responseBean.getFavorites();

        for (Favorite fr : favorites) {
            if (!favorites.contains(fr)) {
                favorites.add(fr);
            }
        }
        adapter = new FavoriteAdapter(CollectionActivity.this, favorites);
        plv_home.setAdapter(adapter);
//        if (curPage < responseBean.getTotal_number()) {
//            addFootView(plv_home, footView);
//        } else {
//            removeFootView(plv_home, footView);
//        }
    }

    private void addFootView(PullToRefreshListView plv, View footView) {
        ListView lv = plv.getRefreshableView();
        if (lv.getFooterViewsCount() == 1) {
            lv.addFooterView(footView);
        }
    }

    private void removeFootView(PullToRefreshListView plv, View footView) {
        ListView lv = plv.getRefreshableView();
        if (lv.getFooterViewsCount() > 1) {
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

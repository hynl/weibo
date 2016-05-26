package com.hynl.weibo.hynlfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hynl.weibo.BaseFragment;
import com.hynl.weibo.R;
import com.hynl.weibo.hynladapter.StatusAdapter;
import com.hynl.weibo.hynlapi.HynlWeiboAPI;
import com.hynl.weibo.hynlapi.SimpleRequestListener;
import com.hynl.weibo.hynlentity.Status;
import com.hynl.weibo.hynlentity.response.StatusTimeLineResponse;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by tt6000 on 2016/3/15.
 */
public class HomeFragment extends BaseFragment {

	private View view;

	private PullToRefreshListView plv_home;
	private View footView;
	
	private StatusAdapter adapter;
	private List<Status> statuses = new ArrayList<Status>();
	private int curPage = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initView();
		loadData(1);
		return view;
	}

	private void initView() {
		view = View.inflate(activity, R.layout.frag_home, null);

		View title=view.findViewById(R.id.rl_titlebar);
		title.setVisibility(View.GONE);
		
		plv_home = (PullToRefreshListView) view.findViewById(R.id.lv_home);
		adapter = new StatusAdapter(activity, statuses);
		plv_home.setAdapter(adapter);
		plv_home.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				loadData(1);
			}
		});
		plv_home.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						loadData(curPage + 1);
					}
				});
		
		footView = View.inflate(activity, R.layout.footview_loading, null);
	}

	public void loadData(final int page) {
		HynlWeiboAPI api = new HynlWeiboAPI(activity);

		api.statusesHome_timeline(page,
				new SimpleRequestListener(activity, null) {

					@Override
					public void onComplete(String response) {
						super.onComplete(response);

						if(page == 1) {
							statuses.clear();//集合清空，加载第一页。
						}
						
						curPage = page;
						
						addData(new Gson().fromJson(response, StatusTimeLineResponse.class));
					}

					@Override
					public void onAllDone() {
						super.onAllDone();
						
						plv_home.onRefreshComplete();
					}

				});
	}
	
	private void addData(StatusTimeLineResponse responseBean) {
		for(Status status : responseBean.getStatuses()) {
			if(!statuses.contains(status)) {
				statuses.add(status);
			}
		}
		adapter.notifyDataSetChanged();
		
		if(curPage < responseBean.getTotal_number()) {
			addFootView(plv_home, footView);
		} else {
			removeFootView(plv_home, footView);
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
}

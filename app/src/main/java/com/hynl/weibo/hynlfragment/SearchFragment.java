package com.hynl.weibo.hynlfragment;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hynl.weibo.BaseFragment;
import com.hynl.weibo.R;
import com.hynl.weibo.hynladapter.LocStatusAdapter;
import com.hynl.weibo.hynladapter.SurroundAdapter;
import com.hynl.weibo.hynlapi.HynlWeiboAPI;
import com.hynl.weibo.hynlapi.SimpleRequestListener;
import com.hynl.weibo.hynlentity.Status;
import com.hynl.weibo.hynlentity.User;
import com.hynl.weibo.hynlentity.response.GeoList;
import com.hynl.weibo.hynlentity.response.NearbyUsers;
import com.hynl.weibo.hynlentity.response.StatusTimeLineResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tt6000 on 2016/3/15.
 */
public class SearchFragment extends BaseFragment {

	float longitude;
	float latitude;
	String adress="中国陕西省西安市长安区西安电子科技大学南校区竹园公寓";
	View view;
	LocStatusAdapter locStatusAdapter;
	SurroundAdapter userAdapter;
	private ViewPager viewPager;//页卡内容
	private ImageView imageView;// 动画图片
	private TextView textView1,textView2,textView3;
	private List<View> views;// Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private ListView view1,view2;
	View view3;//各个页卡
	ArrayList<User> users = new ArrayList<>();
	ArrayList<Status> statuses = new ArrayList<>();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(activity, R.layout.frag_new, null);
		initImageView();
		initTextView();
		initViewPager();
		getLocation();
		loadDate1(1);
		loadDate2(1);
     return view;
	}
	public void getLocation()  {
		HynlWeiboAPI api = new HynlWeiboAPI(activity);

		api.addressGeo(adress,
				new SimpleRequestListener(activity, null) {

					@Override
					public void onComplete(String response) {
						super.onComplete(response);

						GeoList locaGeo=new Gson().fromJson(response, GeoList.class);

						longitude=locaGeo.getGeos().get(0).getLongitude();
						latitude=locaGeo.getGeos().get(0).getLatitude();


					}

					@Override
					public void onAllDone() {
						super.onAllDone();

					}

				});
	}
	//附近微博列表
	private void loadDate2(int page) {
		HynlWeiboAPI api = new HynlWeiboAPI(activity);

		api.nearbyTimeline(String.valueOf(latitude), String.valueOf(longitude), 11132, 50, page, false,false,
				new SimpleRequestListener(activity, null) {

					@Override
					public void onComplete(String response) {
						super.onComplete(response);


						addData2(new Gson().fromJson(response,StatusTimeLineResponse.class));
					}

					@Override
					public void onAllDone() {
						super.onAllDone();

					}

				});
	}
       void	addData2(StatusTimeLineResponse response){
		   for(Status status : response.getStatuses()) {
			   if(!statuses.contains(status)) {
				   statuses.add(status);
			   }
		   }
		   view2.setAdapter(locStatusAdapter);
	   }
//附近的人列表
	private void loadDate1(int page) {
		HynlWeiboAPI api = new HynlWeiboAPI(activity);

		api.nearbyUsers(String.valueOf(latitude),String.valueOf(longitude),11132,50,page,false,
				new SimpleRequestListener(activity, null) {

					@Override
					public void onComplete(String response) {
						super.onComplete(response);


						addData1(new Gson().fromJson(response, NearbyUsers.class));
					}

					@Override
					public void onAllDone() {
						super.onAllDone();

					}

				});
	}
	void addData1(NearbyUsers nearbyUsers){
		for(User user : nearbyUsers.getUsers()) {
			if(!users.contains(user)) {
				users.add(user);
			}
		}
		view1.setAdapter(userAdapter);
	}

	void initImageView(){
		imageView= (ImageView)view.findViewById(R.id.iv_line);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.iv_line).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
	}
	void initTextView(){
		textView1 = (TextView) view.findViewById(R.id.text1);
		textView2 = (TextView) view.findViewById(R.id.text2);
		textView3 = (TextView) view.findViewById(R.id.text3);

		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
		textView3.setOnClickListener(new MyOnClickListener(2));
	}
	void initViewPager(){
		viewPager=(ViewPager) view.findViewById(R.id.vp_surround);
		views=new ArrayList<View>();
//        LayoutInflater inflater=getLayoutInflater();
//        view1=inflater.inflate(R.layout.lay1, null);
//        view2=inflater.inflate(R.layout.lay2, null);
//        view3=inflater.inflate(R.layout.lay3, null);
		initView();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void initView() {


		LayoutInflater inflater=getActivity().getLayoutInflater();
		view1 = (ListView)inflater.inflate(R.layout.item_frag_lay1, null);

		userAdapter=new SurroundAdapter(getActivity(),users);


		view2= (ListView) inflater.inflate(R.layout.item_frag_lay2, null);
		locStatusAdapter=new LocStatusAdapter(getActivity(),statuses);



		view3=inflater.inflate(R.layout.item_frag_lay3, null);
	}

	private class MyOnClickListener implements View.OnClickListener {
		private int index=0;
		public MyOnClickListener(int i){
			index=i;
		}
		public void onClick(View v) {
			viewPager.setCurrentItem(index);
		}

	}
	public class MyViewPagerAdapter extends PagerAdapter {
		private List<View> mListViews;

		public MyViewPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)   {
			container.removeView(mListViews.get(position));
		}


		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mListViews.get(position), 0);
			return mListViews.get(position);
		}

		@Override
		public int getCount() {
			return  mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
	}

	public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
		public void onPageScrollStateChanged(int arg0) {


		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {


		}

		public void onPageSelected(int arg0) {
            /*两种方法，这个是一种，下面还有一种，显然这个比较麻烦
            Animation animation = null;
            switch (arg0) {
            case 0:
                if (currIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, 0, 0, 0);
                }
                break;
            case 1:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                }
                break;
            case 2:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, two, 0, 0);
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(one, two, 0, 0);
                }
                break;

            }
            */
			Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);//显然这个比较简洁，只有一行代码。
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);
//			Toast.makeText(getActivity(), "您选择了" + viewPager.getCurrentItem() + "页卡", Toast.LENGTH_SHORT).show();
		}

	}
}

package com.hynl.weibo.hynladapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import com.hynl.weibo.R;
import com.hynl.weibo.hynlentity.PicUrls;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
/**
 * Created by tt6000 on 2016/4/1.
 */
public class StatusGridImgsAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<PicUrls> datas;
	private ImageLoader imageLoader;

	public StatusGridImgsAdapter(Context context, ArrayList<PicUrls> datas) {
		this.context = context;
		this.datas = datas;
		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public PicUrls getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_grid_image, null);
			holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		GridView gv = (GridView) parent;
		int horizontalSpacing = gv.getHorizontalSpacing();//image水平间隔
		int numColumns = gv.getNumColumns();//列数
		int itemWidth = (gv.getWidth() - (numColumns-1) * horizontalSpacing
				- gv.getPaddingLeft() - gv.getPaddingRight()) / numColumns;

		LayoutParams params = new LayoutParams(itemWidth, itemWidth);
		holder.iv_image.setLayoutParams(params);
		
		PicUrls urls = getItem(position);
		imageLoader.displayImage(urls.getThumbnail_pic(), holder.iv_image);
		
		return convertView;
	}

	public static class ViewHolder {
		public ImageView iv_image;
	}

}
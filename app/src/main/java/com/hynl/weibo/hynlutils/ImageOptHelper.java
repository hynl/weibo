package com.hynl.weibo.hynlutils;

import android.graphics.Bitmap;

import com.hynl.weibo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
/**
 * Created by tt6000 on 2016/3/29.
 */
public class ImageOptHelper {
	
	public static DisplayImageOptions getImgOptions() {
		DisplayImageOptions imgOptions = new DisplayImageOptions.Builder()
			.cacheOnDisk(true)
			.cacheInMemory(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.showImageOnLoading(R.drawable.timeline_image_loading)
			.showImageForEmptyUri(R.drawable.timeline_image_loading)
			.showImageOnFail(R.drawable.timeline_image_failure)
			.build();
		return imgOptions;
	}
	
	public static DisplayImageOptions getAvatarOptions() {
		DisplayImageOptions avatarOptions = new DisplayImageOptions.Builder()
			.cacheOnDisk(true)
			.cacheInMemory(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.showImageOnLoading(R.drawable.avatar_default)
			.showImageForEmptyUri(R.drawable.avatar_default)
			.showImageOnFail(R.drawable.avatar_default)
			.displayer(new RoundedBitmapDisplayer(999))//圆角边
			.build();
		return avatarOptions;
	}
	
	public static DisplayImageOptions getCornerOptions(int cornerRadiusPixels) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
			.cacheOnDisk(true)
			.cacheInMemory(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.showImageOnLoading(R.drawable.timeline_image_loading)
			.showImageForEmptyUri(R.drawable.timeline_image_loading)
			.showImageOnFail(R.drawable.timeline_image_loading)
			.displayer(new RoundedBitmapDisplayer(cornerRadiusPixels)).build();
		return options;
	}
}

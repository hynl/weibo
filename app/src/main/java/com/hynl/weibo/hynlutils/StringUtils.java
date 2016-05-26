package com.hynl.weibo.hynlutils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hynl.weibo.R;
import com.sina.weibo.sdk.openapi.legacy.ActivityInvokeAPI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by tt6000 on 2016/3/29.
 */
public class StringUtils {
	//文本样式拓展

	public static SpannableString getWeiboContent(final Context context, final TextView tv, String source) {
		String regexAt = "@[\u4e00-\u9fa5\\w]+";
		String regexTopic = "#[\u4e00-\u9fa5\\w]+#";
		String regexEmoji = "\\[[\u4e00-\u9fa5\\w]+\\]";
		String regexUrls="http://t.cn/\\w+";
		
		String regex = "(" + regexAt + ")|(" + regexTopic + ")|(" + regexEmoji + ")|("+regexUrls+")";
		
		SpannableString spannableString = new SpannableString(source);
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(spannableString);
		
		if(matcher.find()) {
			//文本超链接
			tv.setMovementMethod(LinkMovementMethod.getInstance());
			matcher.reset();
		}
		
		while(matcher.find()) {
			final String atStr = matcher.group(1);
			final String topicStr = matcher.group(2);
			String emojiStr = matcher.group(3);
			final String URLstr = matcher.group(4);
			
			if(atStr != null) {
				int start = matcher.start(1);
				
				MyClickableSpan clickableSpan = new MyClickableSpan(context) {
					
					@Override
					public void onClick(View widget) {
//						Intent intent = new Intent(context, UserInfoActivity.class);
//						intent.putExtra("userName", atStr.substring(1));
//						context.startActivity(intent);
						ActivityInvokeAPI.openUserInfoByNickName((Activity)context, atStr.substring(1));
					}
				};
				spannableString.setSpan(clickableSpan, start, start + atStr.length(), 
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			
			if(topicStr != null) {
				int start = matcher.start(2);
				
				MyClickableSpan clickableSpan = new MyClickableSpan(context) {
					
					@Override
					public void onClick(View widget) {
						ToastUtils.showToast(context, "话题: " + topicStr, Toast.LENGTH_SHORT);
					}
				};
				spannableString.setSpan(clickableSpan, start, start + topicStr.length(), 
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			
			if(emojiStr != null) {
				int start = matcher.start(3);
				
				int imgRes = EmotionUtils.getImgByName(emojiStr);
				Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imgRes);
				
				if(bitmap != null) {
					int size = (int) tv.getTextSize();
					bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
					
					ImageSpan imageSpan = new ImageSpan(context, bitmap);//编辑框中加图片
					spannableString.setSpan(imageSpan, start, start + emojiStr.length(), 
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			}
			if(URLstr!=null){
				int start =matcher.start(0);
				MyClickableSpan  clickableSpan = new MyClickableSpan(context){
					@Override
					public void onClick(View widget) {
						ToastUtils.showToast(context,"网址",Toast.LENGTH_SHORT);
						//跳转网页；
						ActivityInvokeAPI.openWeiboBrowser((Activity)context,URLstr);
					}
				};
				spannableString.setSpan(clickableSpan, start, start + URLstr.length(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			
			
			
		}
		

		return spannableString;
	}

	static class MyClickableSpan extends ClickableSpan {

		private Context context;
		
		public MyClickableSpan(Context context) {
			this.context = context;
		}

		@Override
		public void onClick(View widget) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setColor(context.getResources().getColor(R.color.txt_at_blue));
			ds.setUnderlineText(false);
		}
		
		
	}
}

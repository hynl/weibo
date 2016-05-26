package com.hynl.weibo.hynlactivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hynl.weibo.BaseActivity;
import com.hynl.weibo.R;
import com.hynl.weibo.hynlconstants.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
/**
 * Created by tt6000 on 2016/3/11.
 */
public class StartActivity extends BaseActivity {
	
	private static final int WHAT_INTENT2LOGIN = 1;
	private static final int WHAT_INTENT2MAIN = 2;
	private static final long SPLASH_DUR_TIME = 5000;


	private Oauth2AccessToken accessToken;
	
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch (msg.what) {
			case WHAT_INTENT2LOGIN:
				intent2Activity(LoginActivity.class);
				finish();
				break;
			case WHAT_INTENT2MAIN:
				intent2Activity(com.hynl.weibo.MainActivity.class);
				finish();
				break;
			default:
				break;
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);
		
		accessToken = AccessTokenKeeper.readAccessToken(this);
		if(accessToken.isSessionValid()) {
			handler.sendEmptyMessageDelayed(WHAT_INTENT2MAIN, SPLASH_DUR_TIME);
		} else {
			handler.sendEmptyMessageDelayed(WHAT_INTENT2LOGIN, SPLASH_DUR_TIME);
		}
	}
}

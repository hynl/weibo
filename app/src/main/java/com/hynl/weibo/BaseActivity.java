package com.hynl.weibo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hynl.weibo.hynlconstants.CommonConstants;
import com.hynl.weibo.hynlutils.ToastUtils;
import com.hynl.weibo.hynlutils.Logger;

import com.hynl.weibo.hynlapi.HynlWeiboAPI;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by tt6000 on 2016/3/11.
 */
public abstract class BaseActivity extends Activity {

    protected String TAG;

    protected BaseApplication application;
    protected SharedPreferences sp;

    protected HynlWeiboAPI weiboApi;
    protected ImageLoader imageLoader;
    protected Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = this.getClass().getSimpleName();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//横屏

        application = (BaseApplication) getApplication();
        sp = getSharedPreferences(CommonConstants.SP_NAME, MODE_PRIVATE);//存储名称，文件打开方式
        weiboApi = new HynlWeiboAPI(this);
        imageLoader = ImageLoader.getInstance();


        gson = new Gson();
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    protected void showToast(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    protected void showLog(String msg) {
        Logger.show(TAG, msg);
    }

}


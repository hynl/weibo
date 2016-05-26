package com.hynl.weibo.hynlactivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hynl.weibo.BaseActivity;
import com.hynl.weibo.R;
import com.hynl.weibo.hynlapi.HynlWeiboAPI;
import com.hynl.weibo.hynlapi.SimpleRequestListener;
import com.hynl.weibo.hynlconstants.AccessTokenKeeper;
import com.hynl.weibo.hynlentity.Reminds;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by tt6000 on 2016/5/6.
 */
public class RemindsActivity extends BaseActivity {
    Reminds reminds ;
    int status;
    int follower;
    TextView tv_status;
    TextView tv_follower;
    Oauth2AccessToken mAccess ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminds);
        mAccess= AccessTokenKeeper.readAccessToken(RemindsActivity.this);
        tv_status = (TextView) findViewById(R.id.status_count);
        tv_follower= (TextView) findViewById(R.id.follower_count);
        HynlWeiboAPI weiboAPI =  new HynlWeiboAPI(RemindsActivity.this);
        weiboAPI.reminds(mAccess.getUid(), new SimpleRequestListener(RemindsActivity.this,null){
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
               reminds= new Gson().fromJson(response, Reminds.class);
                status=reminds.getStatus();
                follower=reminds.getFollower();
                Log.e("status",status+""+reminds.toString());
                tv_status.setText("count"+status);
                tv_follower.setText("follower"+follower);
            }
        });

    }
}

package com.hynl.weibo.hynlactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hynl.weibo.BaseActivity;
import com.hynl.weibo.R;
import com.hynl.weibo.hynlfragment.FragmentController;
import com.hynl.weibo.hynlwidget.MySurfaceView;
import com.sina.weibo.sdk.openapi.legacy.ActivityInvokeAPI;
import com.xys.libzxing.zxing.activity.CaptureActivity;


/**
 * Created by tt6000 on 2016/5/8.
 */
public class MyplayActivity extends BaseActivity {
    private View view;
    MySurfaceView gameSurface;
    ImageView startBtn;
    Button btn;
    String mStrUrl;
    FragmentController fragmentController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_search);
        gameSurface= (MySurfaceView) findViewById(R.id.sur_pan);
        startBtn= (ImageView) findViewById(R.id.start_btn);
        btn= (Button) findViewById(R.id.go_fraoract);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (gameSurface.getGoPage()){
                    case 0:startActivityForResult(new Intent(MyplayActivity.this, CaptureActivity.class), 0);
                        break;
                    case 1:startActivity(new Intent(MyplayActivity.this,MyNewFriendsActivity.class));
                        break;
                    case 2:startActivity(new Intent(MyplayActivity.this,CollectionActivity.class));
                        break;
                    case 3:startActivity(new Intent(MyplayActivity.this,FollowersActivity.class));
                        break;
                    case 4:startActivity(new Intent(MyplayActivity.this, WriteStatusActivity.class));
                        break;
                    case 5:
                        break;
                    default:break;
                }
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gameSurface.isRun()) {
                    gameSurface.startPan();
                    startBtn.setImageResource(R.drawable.hand_up);
                } else {
                    if (!gameSurface.isShouldEnd()) {
                        gameSurface.endPan();
                        startBtn.setImageResource(R.drawable.hand_up_press);
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Bundle bundle = data.getExtras();
            mStrUrl = bundle.getString("result");
            ActivityInvokeAPI.openWeiboBrowser(MyplayActivity.this, mStrUrl);
        }
    }

}

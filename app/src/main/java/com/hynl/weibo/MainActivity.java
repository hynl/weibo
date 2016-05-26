package com.hynl.weibo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hynl.weibo.hynlactivity.MyplayActivity;
import com.hynl.weibo.hynlactivity.WriteStatusActivity;
import com.hynl.weibo.hynlapi.HynlWeiboAPI;
import com.hynl.weibo.hynlapi.SimpleRequestListener;
import com.hynl.weibo.hynlconstants.AccessTokenKeeper;
import com.hynl.weibo.hynlfragment.FragmentController;
import com.hynl.weibo.hynlutils.ToastUtils;
import com.sina.weibo.sdk.openapi.legacy.ActivityInvokeAPI;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
            implements NavigationView.OnNavigationItemSelectedListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    String  mStrUrl;
    private RadioGroup rg_tab;
    private RadioButton rb_home;
    private ImageView iv_add;
    private FragmentController controller;
    private Toolbar toolbar;
    private HynlWeiboAPI weiboAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weiboAPI = new HynlWeiboAPI(MainActivity.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        controller = FragmentController.getInstance(this, R.id.fl_content);
        controller.showFragment(0);
        rg_tab = (RadioGroup) findViewById(R.id.rg_tab);
        iv_add = (ImageView) findViewById(R.id.iv_add);

        rg_tab.setOnCheckedChangeListener(this);
        iv_add.setOnClickListener(this);

        rb_home = (RadioButton) findViewById(R.id.rb_home);
//        try {
//            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
//            InputStream is = getResources().getAssets().open("test_selector.xml");
//            parser.setInput(is, "utf-8");
//            rb_home.setTextColor(ColorStateList.createFromXml(getResources(), parser));
//            Log.i("TEST", "SUCCESS");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.i("TEST", "TEST");
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
        Bundle bundle = data.getExtras();
        mStrUrl = bundle.getString("result");
        ActivityInvokeAPI.openWeiboBrowser(MainActivity.this,mStrUrl);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                toolbar.setTitle("首页");
                controller.showFragment(0);
                break;
            case R.id.rb_meassage:
                toolbar.setTitle("消息");
                controller.showFragment(1);
                break;
            case R.id.rb_search:
                toolbar.setTitle("周边");
                controller.showFragment(2);
                break;
            case R.id.rb_user:
                toolbar.setTitle("个人中心");
                controller.showFragment(3);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentController.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                Intent intent = new Intent(MainActivity.this, WriteStatusActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Theme) {
            // Handle the camera action
        } else if (id == R.id.Shake) {
            ActivityInvokeAPI.openShake(MainActivity.this);

        } else if (id == R.id.Scan) {
            startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);

        } else if (id == R.id.Roulette) {

           startActivity(new Intent(MainActivity.this, MyplayActivity.class));
        } else if (id == R.id.exit) {

            MainActivity.this.finish();

        } else if (id == R.id.byebye) {

            weiboAPI.LogoutUser(new SimpleRequestListener(MainActivity.this, null) {

                public void onComplete(String response) {
                    if (!TextUtils.isEmpty(response)) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String value = obj.getString("result");

                            if ("true".equalsIgnoreCase(value)) {
                                AccessTokenKeeper.clear(MainActivity.this);
                                ToastUtils.showToast(MainActivity.this, "登出成功", Toast.LENGTH_SHORT);
                                MainActivity.this.finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}



package com.hynl.weibo.hynlactivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

import android.widget.ImageView;

import android.widget.TextView;

import com.hynl.weibo.BaseActivity;
import com.hynl.weibo.R;

import com.hynl.weibo.hynladapter.WriteStatusGridImgsAdapter;
import com.hynl.weibo.hynlapi.SimpleRequestListener;
import com.hynl.weibo.hynlentity.Status;

import com.hynl.weibo.hynlutils.ImageUtils;
import com.hynl.weibo.hynlutils.StringUtils;
import com.hynl.weibo.hynlutils.HynlTitle;
import com.hynl.weibo.hynlwidget.WrapHeightGridView;

import java.util.ArrayList;

/**
 * Created by tt6000 on 2016/4/7.
 */
public class WriteStatusActivity extends BaseActivity implements OnClickListener, OnItemClickListener {

	// 输入框
	private EditText et_write_status;
	// 添加的九宫格图片
	private WrapHeightGridView gv_write_status;
	// 转发微博内容
	private View include_retweeted_status_card;
	private ImageView iv_rstatus_img;;
	private TextView tv_rstatus_username;;
	private TextView tv_rstatus_content;;
	// 底部添加栏
	private ImageView iv_image;
	private ImageView iv_at;
	private ImageView iv_topic;
	private ImageView iv_emoji;
	private ImageView iv_add;

	// 进度框
	private ProgressDialog progressDialog;

	private WriteStatusGridImgsAdapter statusImgsAdapter;
	private ArrayList<Uri> imgUris = new ArrayList<Uri>();
//	private EmotionPagerAdapter emotionPagerGvAdapter;
	
	private Status retweeted_status;
	private Status cardStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_write_status);

		retweeted_status = (Status) getIntent().getSerializableExtra("status");
		
		initView();
	}

	private void initView() {
		// 标题栏
		new HynlTitle(this)
				.setTitleText("发微博")
				.setLeftText("取消")
				.setLeftOnClickListener(this)
				.setRightText("发送")
				.setRightOnClickListener(this)
				.build();
		// 输入框
		et_write_status = (EditText) findViewById(R.id.et_write_status);
		// 添加的九宫格图片
		gv_write_status = (WrapHeightGridView) findViewById(R.id.gv_write_status);
		// 转发微博内容
		include_retweeted_status_card = findViewById(R.id.include_retweeted_status_card);
		iv_rstatus_img = (ImageView) findViewById(R.id.iv_rstatus_img);
		tv_rstatus_username = (TextView) findViewById(R.id.tv_rstatus_username);
		tv_rstatus_content = (TextView) findViewById(R.id.tv_rstatus_content);
		// 底部添加栏
		iv_image = (ImageView) findViewById(R.id.iv_image);
		iv_at = (ImageView) findViewById(R.id.iv_at);
		iv_topic = (ImageView) findViewById(R.id.iv_topic);
		iv_emoji = (ImageView) findViewById(R.id.iv_emoji);
		iv_add = (ImageView) findViewById(R.id.iv_add);

		// 进度框
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("微博发布中...");

		statusImgsAdapter = new WriteStatusGridImgsAdapter(this, imgUris, gv_write_status);
		gv_write_status.setAdapter(statusImgsAdapter);
		gv_write_status.setOnItemClickListener(this);

		iv_image.setOnClickListener(this);
		iv_at.setOnClickListener(this);
		iv_topic.setOnClickListener(this);
		iv_emoji.setOnClickListener(this);
		iv_add.setOnClickListener(this);

		initRetweetedStatus();
//		initEmotion();
	}
	
	/**
	 * 发送微博
	 */
	private void sendStatus() {
		String statusContent = et_write_status.getText().toString();
		if(TextUtils.isEmpty(statusContent)) {
			showToast("微博内容不能为空");
			return;
		}
		
		String imgFilePath = null;
		if(imgUris.size() > 0) {
			Uri uri = imgUris.get(0);
			imgFilePath = ImageUtils.getImageAbsolutePath19(this, uri);
		}
		
		long retweetedStatusId = cardStatus == null ? -1 : cardStatus.getId();
		
		weiboApi.statusesSend(statusContent, imgFilePath, retweetedStatusId, 
				new SimpleRequestListener(this, null){

					@Override
					public void onComplete(String response) {
						super.onComplete(response);
						
						showToast("微博发送成功");
						WriteStatusActivity.this.finish();
					}
			
		});
		
	}

	/**
	 * 初始化引用微博内容
	 */
	private void initRetweetedStatus() {
		if(retweeted_status != null) {
			Status rrStatus = retweeted_status.getRetweeted_status();//转发微博的转发微博
			if(rrStatus != null) {
				String content = "//@" + retweeted_status.getUser().getName()
						+ ":" + retweeted_status.getText();
				et_write_status.setText(
						StringUtils.getWeiboContent(this, et_write_status, content));
				cardStatus = rrStatus;
			} else {
				et_write_status.setText("转发微博");
				cardStatus = retweeted_status;
			}
			
			String imgUrl = cardStatus.getThumbnail_pic();
			if(TextUtils.isEmpty(imgUrl)) {
				iv_rstatus_img.setVisibility(View.GONE);
			} else {
				iv_rstatus_img.setVisibility(View.VISIBLE);
				imageLoader.displayImage(imgUrl, iv_rstatus_img);
			}
			
			tv_rstatus_username.setText("@" + cardStatus.getUser().getName());
			tv_rstatus_content.setText(cardStatus.getText());
			
			iv_image.setVisibility(View.GONE);
			
			include_retweeted_status_card.setVisibility(View.VISIBLE);
		} else {
			include_retweeted_status_card.setVisibility(View.GONE);
		}
	}



	/**
	 * 更新图片显示
	 */
	private void updateImgs() {
		if(imgUris.size() > 0) {
			gv_write_status.setVisibility(View.VISIBLE);
			statusImgsAdapter.notifyDataSetChanged();
		} else {
			gv_write_status.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titlebar_tv_left:
			finish();
			break;
		case R.id.titlebar_tv_right:
			sendStatus();
			break;
		case R.id.iv_image:
			ImageUtils.showImagePickDialog(this);
			break;
		case R.id.iv_at:
			break;
		case R.id.iv_topic:
			break;
		case R.id.iv_emoji:

			break;
		case R.id.iv_add:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Object itemAdapter = parent.getAdapter();
		if(itemAdapter instanceof WriteStatusGridImgsAdapter) {
			if(position == statusImgsAdapter.getCount() - 1) {
				ImageUtils.showImagePickDialog(this);
			}

		}
	}

	//图片返回结果的不同处理方式
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case ImageUtils.REQUEST_CODE_FROM_ALBUM:
			if(resultCode == RESULT_CANCELED) {
				return;
			}
			Uri imageUri = data.getData();
			
			imgUris.add(imageUri);
			updateImgs();
			break;
		case ImageUtils.REQUEST_CODE_FROM_CAMERA:
			if(resultCode == RESULT_CANCELED) {
				ImageUtils.deleteImageUri(this, ImageUtils.imageUriFromCamera);
			} else {
				Uri imageUriCamera = ImageUtils.imageUriFromCamera;
				
				imgUris.add(imageUriCamera);
				updateImgs();
			}
			break;

		default:
			break;
		}
	}

}

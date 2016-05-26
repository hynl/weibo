package com.hynl.weibo.hynlwidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.hynl.weibo.R;

/**
 * Created by tt6000 on 2016/4/25.
 */

    public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable{
        private SurfaceHolder mSurfaceHolder;
        private Canvas mCanvas;
        private Thread mThread;
        private boolean isRunning;
        private String[] mText = new String[]{"写写小心情","我的粉丝群","我的小密室","我很关注你","我要扫一扫","留在本页"};
        private int[] mImages = new int[]{R.drawable.mysurface_pay,R.drawable.mysurface_friend,R.drawable.mysurface_bbb,R.drawable.mysurface_ccc,R.drawable.mysurface_aaa,R.drawable.f015};
        private int[] mColor = new int[]{0xffffc300,0xfff17E01,0xffffc300,0xfff17E01,0xffffc300,0xfff17E01};
        private int mCount=6;
        private Bitmap[] mBitmaps;
        private RectF mRange =new RectF();
        private int mRadius;
        private Paint mArcPaint;
        private Paint mTextPaint;
        private double mSpeed ;
        private volatile float mStartAngle=0;
        private volatile float mEndAngle;
        private boolean isShouldEnd;
        private int mCenter;
        private int mPadding;
        private Bitmap mBgBitmap= BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
        private float mTextSize= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());

        public MySurfaceView(Context context, AttributeSet attrs) {
            super(context, attrs);
            mSurfaceHolder=getHolder();
            mSurfaceHolder.addCallback(this);
            setFocusable(true);
            setFocusableInTouchMode(true);
            setKeepScreenOn(true);
        }

        public MySurfaceView(Context context) {
            super(context, null);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int width=Math.min(getMeasuredWidth(),getMeasuredHeight());
            mPadding =getPaddingLeft();
            mRadius=width-mPadding*2;
            mCenter=width/2;
            setMeasuredDimension(width, width);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mArcPaint=new Paint();
            mArcPaint.setAntiAlias(true);
            mArcPaint.setDither(true);
            mTextPaint=new Paint();
            mTextPaint.setColor(0xffffffff);
            mTextPaint.setTextSize(mTextSize);
            mRange=new RectF(mPadding,mPadding,mPadding+mRadius,mPadding+mRadius);
            mBitmaps=new Bitmap[mCount];
            for(int i =0;i<mCount;i++){
                mBitmaps[i]=BitmapFactory.decodeResource(getResources(),mImages[i]);
            }

            isRunning= true;
            mThread=new Thread(this);
            mThread.start();

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

            isRunning=false;
        }

        @Override
        public void run() {

            while(isRunning){
                long start= System.currentTimeMillis();
                draw();
                long end = System.currentTimeMillis();
                if(end-start<50)
                {
                    try {
                        Thread.sleep(50-(end-start));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        private void draw() {
            try {
                mCanvas=mSurfaceHolder.lockCanvas();
                if(mCanvas!=null){

                    drawBg();
                    float temAngle=mEndAngle=mStartAngle;
                    float sweepAngle =360/mCount;
                    for(int i=0;i<mCount;i++){
                        mArcPaint.setColor(mColor[i]);
                        mCanvas.drawArc(mRange, temAngle, sweepAngle, true, mArcPaint);
                        drawText(temAngle, sweepAngle, mText[i]);
                        drawIcon(temAngle, mBitmaps[i]);
                        temAngle=temAngle+sweepAngle;
                        mEndAngle+=sweepAngle;
                    }

                    mStartAngle= (float) (mStartAngle+mSpeed);
                    if(isShouldEnd){
                        mSpeed-=1;
                    }
                    if(mSpeed<=0){
                        mSpeed=0;
                        isShouldEnd=false;
                    }
                }
            }catch (Exception e)
            {

            }
            finally {
                if(mCanvas!=null)
                {
                    mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                }

            }

        }
        public void startPan(){
            mSpeed=30;
        }
        public void endPan(){
            isShouldEnd=true;
        }
        public boolean isRun(){
            return mSpeed!=0;
        }
        public boolean isShouldEnd(){
            return  isShouldEnd;
        }
    public float getEndAngle(){

        return mEndAngle;
    }
    public int getGoPage(){
        int ite = 0;
        float littleAngle=0;
        float endAngle=getEndAngle();
        littleAngle= endAngle%360;

        if(littleAngle>330||littleAngle<=30){
            ite=0;
        }else if(littleAngle>30&&littleAngle<=90){
            ite=1;
        }else if(littleAngle>90&&littleAngle<=150){
            ite=2;
        }else if(littleAngle>150&&littleAngle<=210){
            ite=3;
        }else if(littleAngle>210&&littleAngle<=270){
            ite = 4;
        }else{ite=5;}
        return ite;

    }

        private void drawIcon(float temAngle, Bitmap mImage) {

            int ImageWidth = mRadius/8;
            float angle = (float) ((temAngle+360/mCount/2)*Math.PI/180);
            int x= (int) (mCenter+mRadius/2/2*Math.cos(angle));
            int y= (int) (mCenter+mRadius/2/2*Math.sin(angle));
            Rect rect = new Rect(x-ImageWidth/2,y-ImageWidth/2,x+ImageWidth/2,y+ImageWidth/2);
            mCanvas.drawBitmap(mImage, null, rect, null);

        }

        private void drawText(float temAngle, float sweepAngle, String mText) {
            Path path=new Path();
            path.addArc(mRange, temAngle, sweepAngle);
            float textWidth= mTextPaint.measureText(mText);
            int hOff = (int) (mRadius*Math.PI/mCount/2-textWidth/2);
            int vOff = mRadius/2/6;
            mCanvas.drawTextOnPath(mText,path,hOff,vOff,mTextPaint);
        }

        private void drawBg() {
            mCanvas.drawColor(0XFFFFFFFF);
            mCanvas.drawBitmap(mBgBitmap,null,new Rect(mPadding/2,mPadding/2,getMeasuredWidth()-mPadding/2,getMeasuredWidth()-mPadding/2),null);

        }

    }


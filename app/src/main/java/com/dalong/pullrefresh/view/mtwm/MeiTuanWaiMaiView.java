package com.dalong.pullrefresh.view.mtwm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.dalong.pullrefresh.R;

public class MeiTuanWaiMaiView extends View{

	private Bitmap mMeiTuanIcon;
	private Bitmap mZoomMeiTuanIcon;
	private int measuredWidth;
	private int measuredHeight;
	private float mProgress;
	private int mAlpha;
	private Paint mPaint;

	public MeiTuanWaiMaiView(Context context) {
		this(context,null);
	}

	public MeiTuanWaiMaiView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MeiTuanWaiMaiView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init(){
		mMeiTuanIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_mtwm_header1);
		mPaint = new Paint();
		mPaint.setAlpha(0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}


	private int measureWidth(int widthMeasureSpec){
		int result;
		int size = MeasureSpec.getSize(widthMeasureSpec);
		int mode = MeasureSpec.getMode(widthMeasureSpec);
		if (MeasureSpec.EXACTLY == mode) {
			result = size;
		}else {
			result = mMeiTuanIcon.getWidth();
			if (MeasureSpec.AT_MOST == mode) {
				result = Math.min(result, size);
			}
		}
		return result;
	}

	private int measureHeight(int heightMeasureSpec){
		int result;
		int size = MeasureSpec.getSize(heightMeasureSpec);
		int mode = MeasureSpec.getMode(heightMeasureSpec);
		if (MeasureSpec.EXACTLY == mode) {
			result = size;
		}else {
			result = mMeiTuanIcon.getHeight();
			if (MeasureSpec.AT_MOST == mode) {
				result = Math.min(result, size);
			}
		}
		return result;
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		measuredWidth = w;
		measuredHeight = h;
		mZoomMeiTuanIcon = Bitmap.createScaledBitmap(mMeiTuanIcon,measuredWidth,measuredHeight,true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.scale(mProgress, mProgress, measuredWidth/2, measuredHeight);
		mPaint.setAlpha(mAlpha);
		canvas.drawBitmap(mZoomMeiTuanIcon, 0, 0, mPaint);
	}

	public void setCurrentProgress(float currentProgress){
		this.mProgress = currentProgress;
		this.mAlpha = (int) (currentProgress*255);
		invalidate();
	}
}

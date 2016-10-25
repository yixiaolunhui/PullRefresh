package com.dalong.pullrefresh.view.jd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;

import com.dalong.pullrefresh.R;

public class SecondStepView extends View{

	private Bitmap endBitmap;

	public SecondStepView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SecondStepView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SecondStepView(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		//拿到帧动画第三章图片，我们的FirstStepView的宽高也是根据这张图片来测量的，所以我们就能
		//保证两个View的宽高一致了
		endBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.app_refresh_people_3);
	}

	/**
	 * 只需要测量方法
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}
	
	private int measureWidth(int widthMeasureSpec){
		int result = 0;
		int size = MeasureSpec.getSize(widthMeasureSpec);
		int mode = MeasureSpec.getMode(widthMeasureSpec);
		if (MeasureSpec.EXACTLY == mode) {
			result = size;
		}else {
			result = endBitmap.getWidth();
			if (MeasureSpec.AT_MOST == mode) {
				result = Math.min(size, result);
			}
		}
		return result;
	}
	private int measureHeight(int heightMeasureSpec){
		int result = 0;
		int size = MeasureSpec.getSize(heightMeasureSpec);
		int mode = MeasureSpec.getMode(heightMeasureSpec);
		if (MeasureSpec.EXACTLY == mode) {
			result = size;
		}else {
			result = endBitmap.getHeight();
			if (MeasureSpec.AT_MOST == mode) {
				result = Math.min(size, result);
			}
		}
		return result;
	}
	
}

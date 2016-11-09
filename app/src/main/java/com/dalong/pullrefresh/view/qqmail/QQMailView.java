package com.dalong.pullrefresh.view.qqmail;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.dalong.pullrefresh.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouweilong on 2016/11/9.
 */

public class QQMailView extends View {
    private static final int STATE_ANIMATION_STOP = 0;
    private static final int STATE_ANIMATION_START = 1;
    //左面圆圈颜色
    private int leftColor;
    //中间圆圈颜色
    private int centerColor;
    //右面圆圈颜色
    private int rightColor;
    //最大半径
    private float maxRadius;
    //最小半径
    private float minRadius;
    //旋转动画时间
    private long mDuration;
    //画笔
    private Paint mPaint;
    //控件的宽高
    private int mWidth,mHeight;
    //圆圈之间的间距
    private int mDistance;
    //默认的最大的间距
    private int mMaxDistance;
    //最大透明度
    private final int maxAlpha= 255;
    //最小透明度
    private final int minAlpha = 150;
    //偏移量
    private float offset = dp2px(30);
    //动画集合
    private List<Animator> animatorList = new ArrayList<>();
    //改变的width
    private float mChangeWidth;
    //状态
    private int state=STATE_ANIMATION_STOP;
    //透明度
    private int mAlpha=maxAlpha;

    public QQMailView(Context context) {
        this(context,null);
    }

    public QQMailView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QQMailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.QQMailView);
        leftColor=typedArray.getColor(R.styleable.QQMailView_leftColor,0xffffe464);
        centerColor=typedArray.getColor(R.styleable.QQMailView_centerColor,0xffef4a4a);
        rightColor=typedArray.getColor(R.styleable.QQMailView_rightColor,0xffceee88);
        maxRadius=dp2px(typedArray.getDimension(R.styleable.QQMailView_maxRadius,6));
        minRadius=dp2px(typedArray.getDimension(R.styleable.QQMailView_minRadius,4));
        mDistance=dp2px(typedArray.getDimension(R.styleable.QQMailView_distance,20));
        mDuration=typedArray.getInt(R.styleable.QQMailView_durationTime,900);
        typedArray.recycle();
        this.mMaxDistance=mDistance;
        this.offset=mMaxDistance/3;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
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
            result = 200;
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
            result = 200;
            if (MeasureSpec.AT_MOST == mode) {
                result = Math.min(result, size);
            }
        }
        return result;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircles(canvas);
    }

    /**
     * 🌹画圆
     * @param canvas
     */
    private void drawCircles(Canvas canvas) {
        drawLeftCircles(canvas);
        drawCenterCircles(canvas);
        drawRightCircles(canvas);
    }

    /**
     * 画左面的圆圈
     * @param canvas
     */
    private void drawLeftCircles(Canvas canvas) {
        mPaint.setColor(leftColor);
        drawCircle(canvas,mChangeWidth-mDistance,mPaint);
    }

    /**
     * 画中间的圆圈
     * @param canvas
     */
    private void drawCenterCircles(Canvas canvas) {
        mPaint.setColor(centerColor);
        drawCircle(canvas,mChangeWidth,mPaint);
    }

    /**
     *  画右面的圆圈
     * @param canvas
     */
    private void drawRightCircles(Canvas canvas) {
        mPaint.setColor(rightColor);
        drawCircle(canvas,mChangeWidth+mDistance,mPaint);
    }

    /**
     * 画圆
     * @param canvas
     * @param canvasTranslateX
     * @param paint
     */
    private void drawCircle(Canvas canvas, float canvasTranslateX, @NonNull Paint paint) {
        float radius;
        float imitationTranslateX;
        if (canvasTranslateX >= mDistance && canvasTranslateX <= mDistance + offset) {
            radius = getFuncRadius(canvasTranslateX);
            canvasTranslateX = mDistance;
            imitationTranslateX = getImitationTranslateX(canvasTranslateX);
        } else if (canvasTranslateX > mDistance + offset && canvasTranslateX <= mDistance + 2 * offset) {
            radius = getFuncRadius((mDistance + offset) * 2 - canvasTranslateX);
            canvasTranslateX = -mDistance;
            imitationTranslateX = getImitationTranslateX(canvasTranslateX);
        } else if (canvasTranslateX > mDistance + 2 * offset && canvasTranslateX <= 3 * mDistance + 2 * offset) {
            canvasTranslateX = canvasTranslateX - 2 * mDistance - 2 * offset;
            imitationTranslateX = getImitationTranslateX(canvasTranslateX);
            radius = getFuncRadius(imitationTranslateX);
        } else {
            imitationTranslateX = getImitationTranslateX(canvasTranslateX);
            radius = getFuncRadius(imitationTranslateX);
        }
        //设置透明度
        paint.setAlpha(mAlpha);
        //把当前画布的原点移到(imitationTranslateX,0),后面的操作都以(imitationTranslateX,0)作为参照点，默认原点为(0,0)
        canvas.translate(imitationTranslateX, 0);
        //画圆
        canvas.drawCircle(mWidth / 2, mHeight / 2, radius, paint);
        canvas.translate(-imitationTranslateX, 0);
    }

    /**
     * 开始动画
     */
    public void startAnimation() {
        ValueAnimator lengthAnimator = ValueAnimator.ofFloat(0, 2 * mDistance + 2 * offset);
        lengthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mChangeWidth = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        lengthAnimator.setDuration(mDuration);
        lengthAnimator.setInterpolator(new LinearInterpolator());
        lengthAnimator.setRepeatCount(Integer.MAX_VALUE);
        animatorList.add(lengthAnimator);
        lengthAnimator.start();
    }



    /**
     * 用来模拟运动的速率,中间快,边缘慢
     */
    private float getImitationTranslateX(float canvasTranslateX) {
        if (canvasTranslateX >= -mDistance / 2f && canvasTranslateX <= mDistance / 2f) {
            return canvasTranslateX * 1.25f;
        } else if (canvasTranslateX > mDistance / 2f) {
            return 0.75f * canvasTranslateX + mDistance / 4f;
        } else {
            return 0.75f * canvasTranslateX - mDistance / 4f;
        }
    }

    /**
     * 通过数学计算得到的表达式,x代表变化的长度的值,根据变化的长度,计算出圆圈的半径
     * y = (M - N) / a * x + M (x < 0)
     * y = (N - M) / a * x + M (x > 0)
     */
    private float getFuncRadius(float canvasTranslateX) {
        if (canvasTranslateX < 0) {
            return (maxRadius - minRadius) / mDistance * canvasTranslateX + maxRadius;
        } else {
            return (minRadius - maxRadius) / mDistance * canvasTranslateX + maxRadius;
        }
    }


    /**
     * 开始动画
     */
    public void start() {
        if (state == STATE_ANIMATION_STOP) {
            clearAnimator();
            state = STATE_ANIMATION_START;
            startAnimation();
        }
    }

    /**
     * 停止动画
     */
    public void stop() {
        if (state == STATE_ANIMATION_START) {
            state = STATE_ANIMATION_STOP;
            clearAnimator();
            mChangeWidth = 0;
            invalidate();
        }
    }

    /**
     * 清除动画
     */
    private void clearAnimator() {
        for(int i = 0; i < animatorList.size(); i++) {
            Animator animator = animatorList.get(i);
            if (animator.isRunning()) {
                animator.cancel();
            }
        }
        animatorList.clear();
    }


    /**
     * 设置之间间距和透明度
     * @param zoom
     */
    public  void setDistanceAndAlpha(float zoom){
        this.mDistance= (int) (zoom*mMaxDistance);
        if(getVisibility()==GONE)setVisibility(VISIBLE);
        setAlpha(zoom);
        postInvalidate();
    }

    /**
     * 设置透明度
     * @param zoom
     */
    public  void setAlpha(float zoom){
        this.mAlpha= (int) (zoom*maxAlpha);
        if(mAlpha>=maxAlpha)mAlpha=maxAlpha;
        if(mAlpha<=minAlpha)mAlpha=minAlpha;
    }

    /**
     * dp转px
     * @param dp
     * @return
     */
    private int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}

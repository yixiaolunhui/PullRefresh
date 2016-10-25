package com.dalong.pullrefresh.view.qq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dalong.pullrefresh.R;
import com.dalong.refreshlayout.OnHeaderListener;

/**
 * Created by zhouweilong on 2016/10/25.
 */

public class QQHeader extends RelativeLayout implements OnHeaderListener {

    // 初始状态
    public static final int INIT = 0;
    // 释放刷新
    public static final int RELEASE_TO_REFRESH = 1;
    // 正在刷新
    public static final int REFRESHING = 2;
    // 操作完毕
    public static final int DONE = 5;
    // 当前状态
    private int state = INIT;
    // 刷新成功
    private  RotateAnimation refreshingAnimation;
    private  RotateAnimation rotateAnimation;
    private View headerView;
    // 下拉的箭头
    private ImageView pullView;
    // 正在刷新的图标
    private ImageView refreshingView;
    // 刷新结果图标
    private ImageView refreshStateImageView;
    // 刷新结果：成功或失败
    private TextView refreshStateTextView;
    private boolean isRefreshAfter=false;


    public QQHeader(Context context) {
        super(context);

        headerView= LayoutInflater.from(context).inflate(R.layout.qq_refresh_head, this, true);
        // 初始化下拉布局
        pullView = (ImageView) headerView.findViewById(R.id.pull_icon);
        refreshStateTextView = (TextView) headerView.findViewById(R.id.state_tv);
        refreshingView = (ImageView)headerView.findViewById(R.id.refreshing_icon);
        refreshStateImageView = (ImageView) headerView.findViewById(R.id.state_iv);

        rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                context, R.anim.reverse_anim);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                context, R.anim.rotating);
        // 添加匀速转动动画
        LinearInterpolator lir = new LinearInterpolator();
        rotateAnimation.setInterpolator(lir);
        refreshingAnimation.setInterpolator(lir);
        measureView(headerView);
        changeState(INIT);
    }
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }
    @Override
    public void onRefreshBefore(int scrollY, int refreshHeight, int headerHeight) {
        changeState(INIT);
    }

    /**
     *
     * @param scrollY 下拉移动的y值
     * @param refreshHeight 刷新的高度
     * @param headerHeight  header总高度
     */
    @Override
    public void onRefreshAfter(int scrollY, int refreshHeight, int headerHeight) {
        changeState(RELEASE_TO_REFRESH);
    }

    @Override
    public void onRefreshReady(int scrollY, int refreshHeight, int headerHeight) {
    }

    @Override
    public void onRefreshing(int scrollY, int refreshHeight, int headerHeight) {
        changeState(REFRESHING);
    }

    @Override
    public void onRefreshComplete(int scrollY, int refreshHeight, int headerHeight, boolean isRefreshSuccess) {
        changeState(DONE);
        // 刷新成功
        refreshStateImageView.setVisibility(View.VISIBLE);
        if(isRefreshSuccess){
            refreshStateTextView.setText(R.string.refresh_succeed);
            refreshStateImageView
                    .setBackgroundResource(R.mipmap.refresh_succeed);
        }else{
            refreshStateTextView.setText(R.string.refresh_fail);
            refreshStateImageView
                    .setBackgroundResource(R.mipmap.refresh_failed);
        }


    }

    @Override
    public void onRefreshCancel(int scrollY, int refreshHeight, int headerHeight) {
        changeState(DONE);
        // 刷新失败
        refreshStateImageView.setVisibility(View.VISIBLE);
        refreshStateTextView.setText(R.string.refresh_cancel);
        refreshStateImageView
                .setBackgroundResource(R.mipmap.refresh_failed);
    }


    private void changeState(int to) {
        state = to;
        switch (state) {
            case INIT:
                // 下拉布局初始状态
                isRefreshAfter=false;
                refreshStateImageView.setVisibility(View.GONE);
                refreshStateTextView.setText(R.string.pull_to_refresh);
                pullView.clearAnimation();
                pullView.setVisibility(View.VISIBLE);
                break;
            case RELEASE_TO_REFRESH:
                // 释放刷新状态
                pullView.setVisibility(View.VISIBLE);
                if(!isRefreshAfter){
                    isRefreshAfter=true;
                    pullView.startAnimation(rotateAnimation);
                }
              
                refreshStateTextView.setText(R.string.release_to_refresh);

                break;
            case REFRESHING:
                // 正在刷新状态
                pullView.clearAnimation();
                refreshingView.setVisibility(View.VISIBLE);
                pullView.setVisibility(View.INVISIBLE);
                refreshingView.startAnimation(refreshingAnimation);
                refreshStateTextView.setText(R.string.refreshing);
                break;
            case DONE:
                refreshingView.clearAnimation();
                refreshingView.setVisibility(View.GONE);
                break;
        }
    }
}

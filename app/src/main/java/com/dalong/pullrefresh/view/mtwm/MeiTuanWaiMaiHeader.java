package com.dalong.pullrefresh.view.mtwm;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dalong.pullrefresh.R;
import com.dalong.refreshlayout.OnHeaderListener;

/**
 * 美团加载头部
 * Created by zhouweilong on 2016/10/21.
 */

public class MeiTuanWaiMaiHeader extends RelativeLayout  implements OnHeaderListener {
    private  ImageView mPullAnimationImage;
    private AnimationDrawable mPullAnimation;
    private MeiTuanWaiMaiView mPullDownView;


    public MeiTuanWaiMaiHeader(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.mtwm_header, this, true);
        mPullDownView = (MeiTuanWaiMaiView) findViewById(R.id.refreshAnimView);
        mPullAnimationImage = (ImageView) findViewById(R.id.mPullAnimation);
        mPullAnimationImage.setBackgroundResource(R.drawable.loading);
        mPullAnimation = (AnimationDrawable) mPullAnimationImage.getBackground();
    }

    public void handleScale(float scale) {
        mPullDownView.setCurrentProgress(scale);
    }


    public void isRefreshState(boolean isRefresh){
        if(isRefresh){
            mPullAnimationImage.setVisibility(VISIBLE);
            mPullDownView.setVisibility(GONE);
            mPullAnimation.start();
        }else{
            mPullAnimationImage.setVisibility(GONE);
            mPullDownView.setVisibility(VISIBLE);
            mPullAnimation.stop();
        }
    }

    /**
     * 下拉刷新
     * @param scrollY 下拉移动的y值
     * @param refreshHeight 刷新的高度
     * @param headerHeight  header总高度
     */
    @Override
    public void onRefreshBefore(int scrollY,int refreshHeight,int headerHeight) {
        isRefreshState(false);
        handleScale(Math.abs(scrollY)/(1.0f*refreshHeight));
    }

    @Override
    public void onRefreshAfter(int scrollY,int refreshHeight,int headerHeight) {
    }

    /**
     * 准备刷新
     * @param scrollY
     */
    @Override
    public void onRefreshReady(int scrollY,int refreshHeight,int headerHeight) {
    }

    /**
     * 正在刷新
     * @param scrollY 下拉移动的y值
     * @param refreshHeight 刷新的高度
     * @param headerHeight  header总高度
     */
    @Override
    public void onRefreshing(int scrollY,int refreshHeight,int headerHeight) {
        isRefreshState(true);
    }

    /**
     * 刷新完成
     * @param scrollY 下拉移动的y值
     * @param refreshHeight 刷新的高度
     * @param headerHeight  header总高度
     * @param isRefreshSuccess  刷新的状态  是成功了 还是失败了
     */
    @Override
    public void onRefreshComplete(int scrollY,int refreshHeight,int headerHeight, boolean isRefreshSuccess) {
        isRefreshState(true);
    }

    /**
     * 刷新取消
     * @param scrollY 下拉移动的y值
     * @param refreshHeight 刷新的高度
     * @param headerHeight  header总高度
     */
    @Override
    public void onRefreshCancel(int scrollY,int refreshHeight,int headerHeight) {
        isRefreshState(true);
    }
}

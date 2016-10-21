package com.dalong.pullrefresh.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dalong.pullrefresh.R;
import com.dalong.refreshlayout.OnHeaderListener;

/**
 * Created by zhouweilong on 2016/10/21.
 */

public class MeiTuanHeader extends RelativeLayout  implements OnHeaderListener {
    private ImageView mPullDownView;
    private ImageView mReleaseRefreshingView;

    private AnimationDrawable mChangeToReleaseRefreshAd;
    private AnimationDrawable mRefreshingAd;

    private int mChangeToReleaseRefreshAnimResId=R.drawable.bga_refresh_mt_change_to_release_refresh;
    private int mRefreshingAnimResId=R.drawable.bga_refresh_mt_refreshing;

    public MeiTuanHeader(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.meituan_refresh_view, this, true);
        mPullDownView = (ImageView) findViewById(R.id.iv_meituan_pull_down);
        mReleaseRefreshingView = (ImageView) findViewById(R.id.iv_meituan_release_refreshing);
        mReleaseRefreshingView.setImageResource(mChangeToReleaseRefreshAnimResId);
        mPullDownView.setImageResource(R.mipmap.bga_refresh_mt_pull_down);
    }
    public MeiTuanHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    public void handleScale(float scale) {
        scale = 0.1f + 0.9f * scale;
        ViewCompat.setScaleX(mPullDownView, scale);
        ViewCompat.setPivotY(mPullDownView, mPullDownView.getHeight());
        ViewCompat.setScaleY(mPullDownView, scale);
        Log.v("111111","scale:"+scale);
        if(scale>=0.8f){
            changeToReleaseRefresh();
        }else{
            onEndRefreshing();
        }
    }

    public void changeToIdle() {
        stopChangeToReleaseRefreshAd();
        stopRefreshingAd();

        mPullDownView.setVisibility(VISIBLE);
        mReleaseRefreshingView.setVisibility(INVISIBLE);
    }

    public void changeToPullDown() {
        mPullDownView.setVisibility(VISIBLE);
        mReleaseRefreshingView.setVisibility(INVISIBLE);
    }

    public void changeToReleaseRefresh() {
        mReleaseRefreshingView.setImageResource(mChangeToReleaseRefreshAnimResId);
        mChangeToReleaseRefreshAd = (AnimationDrawable) mReleaseRefreshingView.getDrawable();

        mReleaseRefreshingView.setVisibility(VISIBLE);
        mPullDownView.setVisibility(INVISIBLE);

        mChangeToReleaseRefreshAd.start();
    }

    public void changeToRefreshing() {
        stopChangeToReleaseRefreshAd();

        mReleaseRefreshingView.setImageResource(mRefreshingAnimResId);
        mRefreshingAd = (AnimationDrawable) mReleaseRefreshingView.getDrawable();

        mReleaseRefreshingView.setVisibility(VISIBLE);
        mPullDownView.setVisibility(INVISIBLE);

        mRefreshingAd.start();
    }

    public void onEndRefreshing() {
        stopChangeToReleaseRefreshAd();
        stopRefreshingAd();
    }

    private void stopRefreshingAd() {
        if (mRefreshingAd != null) {
            mRefreshingAd.stop();
            mRefreshingAd = null;
        }
    }

    private void stopChangeToReleaseRefreshAd() {
        if (mChangeToReleaseRefreshAd != null) {
            mChangeToReleaseRefreshAd.stop();
            mChangeToReleaseRefreshAd = null;
        }
    }

    @Override
    public void onDownBefore(int scrollY) {
        changeToPullDown();
        handleScale(Math.abs(scrollY)/(1.0f*getHeight()));
    }

    @Override
    public void onDownAfter(int scrollY) {
    }

    /**
     * 准备刷新
     * @param scrollY
     */
    @Override
    public void onRefreshScrolling(int scrollY) {
    }

    @Override
    public void onRefreshDoing(int scrollY) {
        changeToRefreshing();
    }

    @Override
    public void onRefreshCompleteScrolling(int scrollY, boolean isRefreshSuccess) {
        onEndRefreshing();
        changeToIdle();
    }

    @Override
    public void onRefreshCancelScrolling(int scrollY) {
        onEndRefreshing();
        changeToIdle();
    }
}

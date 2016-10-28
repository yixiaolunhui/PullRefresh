package com.dalong.pullrefresh.view.alipay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.dalong.pullrefresh.R;
import com.dalong.refreshlayout.OnHeaderListener;

/**
 * 支付宝下拉头部
 * Created by zhouweilong on 2016/10/27.
 */

public class AlipayHeader  extends RelativeLayout implements OnHeaderListener  {


    private  SmileyLoadingView mSmileyLoadingView;
    private View headerView;

    public AlipayHeader(Context context) {
        super(context);
        headerView= LayoutInflater.from(context).inflate(R.layout.alipay_header_view, this, true);
        mSmileyLoadingView=(SmileyLoadingView)headerView.findViewById(R.id.loading_view);
    }

    @Override
    public void onRefreshBefore(int scrollY, int refreshHeight, int headerHeight) {
        float scan= Math.abs(scrollY)/(1.0f*refreshHeight);
        mSmileyLoadingView.setPaintAlpha(scan >= 1 ? 0xFF : (int) (scan * 0xFF));
    }

    @Override
    public void onRefreshAfter(int scrollY, int refreshHeight, int headerHeight) {

    }

    @Override
    public void onRefreshReady(int scrollY, int refreshHeight, int headerHeight) {

    }

    @Override
    public void onRefreshing(int scrollY, int refreshHeight, int headerHeight) {
        mSmileyLoadingView.start();
    }

    @Override
    public void onRefreshComplete(int scrollY, int refreshHeight, int headerHeight, boolean isRefreshSuccess) {
        mSmileyLoadingView.stop();
    }

    @Override
    public void onRefreshCancel(int scrollY, int refreshHeight, int headerHeight) {
        mSmileyLoadingView.stop();
    }
}

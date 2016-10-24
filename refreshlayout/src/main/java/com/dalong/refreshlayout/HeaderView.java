package com.dalong.refreshlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 默认的下拉刷新头部   可以按照这个例子 自定义头部
 * Created by zhouweilong on 2016/10/19.
 */

public class HeaderView extends FrameLayout implements OnHeaderListener {

    public TextView mRefreshTv;

    public HeaderView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.refresh_header, this, true);
        mRefreshTv = (TextView) findViewById(R.id.tv);
    }

    /**
     * 下拉刷新
     * @param scrollY
     */
    @Override
    public void onRefreshBefore(int scrollY,int refreshHeight,int headerHeight) {
        mRefreshTv.setText("下拉刷新");
    }

    /**
     * 松开刷新
     * @param scrollY
     */
    @Override
    public void onRefreshAfter(int scrollY,int refreshHeight,int headerHeight) {
        mRefreshTv.setText("松开刷新");
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
     * @param scrollY
     */
    @Override
    public void onRefreshing(int scrollY,int refreshHeight,int headerHeight) {
        mRefreshTv.setText("正在刷新");
    }

    /**
     * 刷新成功
     * @param scrollY
     * @param isRefreshSuccess  刷新的状态  是成功了 还是失败了
     */
    @Override
    public void onRefreshComplete(int scrollY,int refreshHeight,int headerHeight, boolean isRefreshSuccess) {
        mRefreshTv.setText(isRefreshSuccess ? "刷新成功" : "刷新失败");
    }

    /**
     * 取消刷新
     * @param scrollY
     */
    @Override
    public void onRefreshCancel(int scrollY,int refreshHeight,int headerHeight) {
        mRefreshTv.setText("取消刷新");
    }
}

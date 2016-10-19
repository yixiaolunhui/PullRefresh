package com.dalong.refreshlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by zhouweilong on 2016/10/19.
 */

public class HeaderView extends FrameLayout implements RefreshHeader {

    public TextView tvPullDown;

    public HeaderView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.refresh_header, this, true);
        tvPullDown = (TextView) findViewById(R.id.tv);
    }

    @Override
    public void onDownBefore(int scrollY) {
        tvPullDown.setText("下拉刷新");
    }

    @Override
    public void onDownAfter(int scrollY) {
        tvPullDown.setText("松开刷新");
    }

    @Override
    public void onRefreshScrolling(int scrollY) {
//        tvPullDown.setText("准备刷新");
    }

    @Override
    public void onRefreshDoing(int scrollY) {
        tvPullDown.setText("正在刷新...");
    }

    @Override
    public void onRefreshCompleteScrolling(int scrollY, boolean isLoadSuccess) {
        tvPullDown.setText(isLoadSuccess ? "刷新成功" : "刷新失败");
    }

    @Override
    public void onRefreshCancelScrolling(int scrollY) {
        tvPullDown.setText("取消刷新");
    }
}

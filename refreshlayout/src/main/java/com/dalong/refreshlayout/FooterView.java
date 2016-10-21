package com.dalong.refreshlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 加载更多布局  可以按照这个例子自定义自己的加载更多
 * Created by zhouweilong on 2016/10/19.
 */

public class FooterView  extends FrameLayout implements OnFooterListener {
    public TextView mLoadTv;

    public FooterView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.refresh_footer, this, true);
        mLoadTv = (TextView) findViewById(R.id.tv);
    }

    /**
     * 上拉加载
     * @param scrollY
     */
    @Override
    public void onLoadBefore(int scrollY) {
        mLoadTv.setText("上拉加载");
    }

    /**
     * 松开加载
     * @param scrollY
     */
    @Override
    public void onLoadAfter(int scrollY) {
        mLoadTv.setText("松开加载");
    }

    /**
     * 准备加载
     * @param scrollY
     */
    @Override
    public void onLoadReady(int scrollY) {
        mLoadTv.setText("准备加载");
    }

    /**
     * 正在加载
     * @param scrollY
     */
    @Override
    public void onLoading(int scrollY) {
        mLoadTv.setText("正在加载");
    }

    /**
     * 加载成功
     * @param scrollY
     * @param isLoadSuccess 加载的状态  是成功了 还是失败了
     */
    @Override
    public void onLoadComplete(int scrollY, boolean isLoadSuccess) {
        mLoadTv.setText(isLoadSuccess ? "加载成功" : "加载失败");
    }

    /**
     * 加载取消
     * @param scrollY
     */
    @Override
    public void onLoadCancel(int scrollY) {
        mLoadTv.setText("加载取消");
    }
}

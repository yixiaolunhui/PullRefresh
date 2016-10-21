package com.dalong.refreshlayout;

/**
 * 下拉刷新 加载更多接口
 * Created by zhouweilong on 2016/10/19.
 */

public interface OnRefreshListener {

    /**
     * 刷新回调
     */
    void onRefresh();

    /**
     * 加载更多回调
     */
    void onLoadMore();
}

package com.dalong.refreshlayout;

/**
 * 加载更多接口
 * Created by zhouweilong on 2016/10/19.
 */

public interface OnFooterListener {

    /**
     * 上拉加载   还没有达到可以加载之前的时候
     * @param scrollY
     */
    void onLoadBefore(int scrollY);

    /**
     * 松开加载   上拉已经到达可以刷新的时候
     * @param scrollY
     */
    void onLoadAfter(int scrollY);

    /**
     * 准备加载    达到可以加载的时候松开手指回到加载的位置状态
     * @param scrollY
     */
    void onLoadReady(int scrollY);

    /**
     * 正在加载中
     * @param scrollY
     */
    void onLoading(int scrollY);

    /**
     * 加载完成后 分成功和失败两个状态
     * @param scrollY
     * @param isLoadSuccess 加载的状态  是成功了 还是失败了
     */
    void onLoadComplete(int scrollY, boolean isLoadSuccess);

    /**
     * 取消加载
     * @param scrollY
     */
    void onLoadCancel(int scrollY);
}

package com.dalong.refreshlayout;

/**
 * 下拉刷新回调接口
 * Created by zhouweilong on 2016/10/19.
 */

public interface OnHeaderListener {


    /**
     * 下拉刷新中  还没有达到可以刷新之前的时候
     * @param scrollY 下拉移动的y值
     * @param refreshHeight 刷新的高度
     * @param headerHeight  header总高度
     */
    void onRefreshBefore(int scrollY,int refreshHeight,int headerHeight);


    /**
     * 松开刷新  下拉已经到达可以刷新的时候
     * @param scrollY 下拉移动的y值
     * @param refreshHeight 刷新的高度
     * @param headerHeight  header总高度
     */
    void onRefreshAfter(int scrollY,int refreshHeight,int headerHeight);


    /**
     * 准备刷新状态  达到可以刷新的时候松开手指回到刷新的位置状态
     * @param scrollY 下拉移动的y值
     * @param refreshHeight 刷新的高度
     * @param headerHeight  header总高度
     */
    void onRefreshReady(int scrollY,int refreshHeight,int headerHeight);


    /**
     * 正在刷新中
     * @param scrollY 下拉移动的y值
     * @param refreshHeight 刷新的高度
     * @param headerHeight  header总高度
     */
    void onRefreshing(int scrollY,int refreshHeight,int headerHeight);

    /**
     * 刷新完成  分刷新成功和失败
     * @param scrollY 下拉移动的y值
     * @param refreshHeight 刷新的高度
     * @param headerHeight  header总高度
     * @param isRefreshSuccess  刷新的状态  是成功了 还是失败了
     */
    void onRefreshComplete(int scrollY ,int refreshHeight,int headerHeight,boolean isRefreshSuccess);


    /**
     * 取消刷新  没有超过可刷新的距离
     * @param scrollY 下拉移动的y值
     * @param refreshHeight 刷新的高度
     * @param headerHeight  header总高度
     */
    void onRefreshCancel(int scrollY,int refreshHeight,int headerHeight);
}

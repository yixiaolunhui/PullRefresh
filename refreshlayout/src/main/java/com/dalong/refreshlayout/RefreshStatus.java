package com.dalong.refreshlayout;

/**
 * 下拉刷新和加载更多状态
 * Created by zhouweilong on 2016/10/19.
 */

public enum RefreshStatus {

    DEFAULT,//默认状态

    /**
     * 刷新状态
     */
    REFRESH_BEFORE,   // 下拉刷新中  还没有达到可以刷新之前的时候
    REFRESH_AFTER,    // 松开刷新    下拉已经到达可以刷新的时候
    REFRESH_READY,    // 准备刷新状态 达到可以刷新的时候松开手指回到刷新的位置状态
    REFRESH_DOING,    // 正在刷新中
    REFRESH_COMPLETE, // 刷新完成    分刷新成功和失败
    REFRESH_CANCEL,   // 取消刷新    没有超过可刷新的距离

    LOAD_BEFORE,      // 上拉加载    还没有达到可以加载之前的时候
    LOAD_AFTER,       // 松开加载    上拉已经到达可以刷新的时候
    LOAD_READY,       // 准备加载    达到可以加载的时候松开手指回到加载的位置状态
    LOAD_DOING,       // 正在加载中
    LOAD_COMPLETE,    // 加载完成后   分成功和失败两个状态
    LOAD_CANCEL,      // 取消加载

}

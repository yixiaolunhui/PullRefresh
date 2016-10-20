package com.dalong.refreshlayout;

/**
 * 下拉刷新和加载更多状态
 * Created by zhouweilong on 2016/10/19.
 */

public enum RefreshStatus {

    DEFAULT,//默认状态

    DOWN_BEFORE,//下拉中，到达有效刷新距离前
    DOWN_AFTER,//下拉中，到达有效刷新距离后
    REFRESH_SCROLLING,//放手后，开始刷新前，回到刷新的位置中
    REFRESH_DOING,//正在刷新中
    REFRESH_COMPLETE_SCROLLING,//刷新完成后，回到默认状态中
    REFRESH_CANCEL_SCROLLING,//刷新取消后，回到默认状态中

    UP_BEFORE,//上拉中，到达有效刷新距离前
    UP_AFTER,//上拉中，到达有效刷新距离后
    LOADMORE_SCROLLING,//放手后，开始加载前，从手势位置回到加载的位置中
    LOADMORE_DOING,//正在加载中
    LOADMORE_COMPLETE_SCROLLING,//加载完成后，回到默认状态中
    LOADMORE_CANCEL_SCROLLING,//加载取消后，回到默认状态中

}

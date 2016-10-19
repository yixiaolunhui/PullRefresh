package com.dalong.refreshlayout;

/**
 * Created by zhouweilong on 2016/10/19.
 */

public interface RefreshFooter {

    //上拉加载
    void onUpBefore(int scrollY);

    //松开加载
    void onUpAfter(int scrollY);

    //准备加载
    void onLoadScrolling(int scrollY);

    //正在加载……
    void onLoadDoing(int scrollY);

    //加载完成后，回到默认状态中
    void onLoadCompleteScrolling(int scrollY, boolean isLoadSuccess);

    //加载取消后，回到默认状态中
    void onLoadCancelScrolling(int scrollY);
}

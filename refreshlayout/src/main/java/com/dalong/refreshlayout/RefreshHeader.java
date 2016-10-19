package com.dalong.refreshlayout;

/**
 * Created by zhouweilong on 2016/10/19.
 */

public interface RefreshHeader {

    //下拉刷新(下拉中，到达有效刷新距离前)
    public void onDownBefore(int scrollY);

    //松开刷新(下拉中，到达有效刷新距离后)
    public  void onDownAfter(int scrollY);

    //准备刷新(从松手后的位置滚动到刷新的位置)
    public void onRefreshScrolling(int scrollY);

    //正在刷新……
    public void onRefreshDoing(int scrollY);

    //刷新完成后，回到默认状态中
    public void onRefreshCompleteScrolling(int scrollY, boolean isRefreshSuccess);

    //刷新取消后，回到默认状态中（没有超过有效的下拉距离）
    public void onRefreshCancelScrolling(int scrollY);
}

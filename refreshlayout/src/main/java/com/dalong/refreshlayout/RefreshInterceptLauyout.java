package com.dalong.refreshlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ScrollView;

/**
 * 这个类主要是对子view的顶部和底部的判别  触摸事件处理
 * Created by zhouweilong on 2016/10/19.
 */

public abstract  class RefreshInterceptLauyout extends RefreshBaseLayout {

    // 用于计算滑动距离的Y坐标中介
    public int lastYMove;
    // 用于判断是否拦截触摸事件的Y坐标中介
    public int lastYIntercept;

    //是否可以加载更多
    public boolean isCanLoad=true;
    //是否可以下拉刷新
    public boolean isCanRefresh=true;

    public RefreshInterceptLauyout(Context context) {
        super(context);
    }

    public RefreshInterceptLauyout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        // 记录此次触摸事件的y坐标
        int y = (int) event.getY();
        // 判断触摸事件类型
        switch (event.getAction()) {
            // Down事件
            case MotionEvent.ACTION_DOWN: {
                // 记录下本次系列触摸事件的起始点Y坐标
                lastYMove = y;
                // 不拦截ACTION_DOWN，因为当ACTION_DOWN被拦截，后续所有触摸事件都会被拦截
                intercept = false;
                break;
            }
            // Move事件
            case MotionEvent.ACTION_MOVE: {
                if (y > lastYIntercept) { // 下滑操作
                    // 检查子试图是否达到了顶部
                    if(mOnCheckCanRefreshListener!=null){
                        intercept=mOnCheckCanRefreshListener.checkCanDoRefresh();
                    }else{
                        intercept=getPullDownIntercept();
                    }

                    if(!isCanRefresh)intercept=false;
                } else if (y < lastYIntercept) { // 上拉操作

                    // 检查子试图是否到达了底部
                    if(mOnCheckCanLoadMoreListener!=null){
                        intercept=mOnCheckCanLoadMoreListener.checkCanDoLoadMore();
                    }else{
                        intercept=getPullUpIntercept();
                    }

                    if(!isCanLoad)intercept=false;
                } else {
                    intercept = false;
                }
                break;
            }
            // Up事件
            case MotionEvent.ACTION_UP: {
                intercept = false;
                break;
            }
        }

        lastYIntercept = y;
        return intercept;
    }

    /**
     * 能不能下拉刷新
     * @return
     */
    public boolean getPullDownIntercept() {
        boolean  intercept=false;
        View child = getFirstVisiableChild();
        if (child == null) {
            intercept = false;
        } else if (child instanceof AdapterView) {
            intercept = avPullDownIntercept(child);
        } else if (child instanceof ScrollView) {
            intercept = svPullDownIntercept(child);
        } else if (child instanceof RecyclerView) {
            intercept = rvPullDownIntercept(child);
        } else if (child instanceof WebView){
            intercept = wvPullDownIntercept(child);
        }
        return intercept;
    }

    /**
     * 能不能加载
     * @return
     */
    public  boolean getPullUpIntercept(){
        boolean  intercept=false;
        View child = getLastVisiableChild();
        if (child == null) {
            intercept = false;
        } else if (child instanceof AdapterView) {
            intercept = avPullUpIntercept(child);
        } else if (child instanceof ScrollView) {
            intercept = svPullUpIntercept(child);
        } else if (child instanceof RecyclerView) {
            intercept = rvPullUpIntercept(child);
        } else if (child instanceof WebView){
            intercept = wvPullUpIntercept(child);
        }
        return intercept;
    }

    /**
     * 获取最后一个可见的子view
     * @return
     */
    private View getLastVisiableChild() {
        for (int i = lastChildIndex; i >= 0; i--) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            } else {
                return child;
            }
        }
        return null;
    }

    /**
     * 获取第一个可见的子view
     * @return
     */
    private View getFirstVisiableChild() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            } else {
                return child;
            }
        }
        return null;
    }
    /**
     * 判别webview是否滚动到底部
     * @param child
     * @return
     */
    private boolean wvPullUpIntercept(View child) {
        boolean intercept = false;
        WebView webView = (WebView) child;
        if(webView.getContentHeight()*webView.getScale()-(webView.getHeight()+webView.getScrollY())==0){
            //已经处于底端
            intercept = true;
        }
        return intercept;
    }

    /**
     * 判别webview是否滚动到顶部
     * @param child
     * @return
     */
    private boolean wvPullDownIntercept(View child) {
        boolean intercept = false;
        if (child.getScrollY() <= 0) {
            intercept = true;
        }
        return intercept;
    }

    /**
     * 获取adapterView是否滚动到顶部
     * @param child
     * @return
     */
    public boolean avPullDownIntercept(View child) {
        boolean intercept = true;
        AdapterView adapterChild = (AdapterView) child;
        // 判断AbsListView是否已经到达内容最顶部
        if (adapterChild.getFirstVisiblePosition() != 0
                || adapterChild.getChildAt(0).getTop() != 0) {
            // 如果没有达到最顶端，则仍然将事件下放
            intercept = false;
        }
        return intercept;
    }

    /**
     * 获取adapterView是否滚动到底部
     * @param child
     * @return
     */
    public boolean avPullUpIntercept(View child) {
        boolean intercept = false;
        AdapterView adapterChild = (AdapterView) child;

        // 判断AbsListView是否已经到达内容最底部
        if (adapterChild.getLastVisiblePosition() == adapterChild.getCount() - 1
                && (adapterChild.getChildAt(adapterChild.getChildCount() - 1).getBottom() == getMeasuredHeight())) {
            // 如果到达底部，则拦截事件
            intercept = true;
        }
        return intercept;
    }
    /**
     * 获取ScrollView是否滚动到顶部
     * @param child
     * @return
     */
    public boolean svPullDownIntercept(View child) {
        boolean intercept = false;
        if (child.getScrollY() <= 0) {
            intercept = true;
        }
        return intercept;
    }
    /**
     * 获取ScrollView是否滚动到底部
     * @param child
     * @return
     */
    public boolean svPullUpIntercept(View child) {
        boolean intercept = false;
        ScrollView scrollView = (ScrollView) child;
        View scrollChild = scrollView.getChildAt(0);

        if (scrollView.getScrollY() >= (scrollChild.getHeight() - scrollView.getHeight())) {
            intercept = true;
        }
        return intercept;
    }

    /**
     * 获取RecyclerView是否滚动到顶部
     * @param child
     * @return
     */
    public boolean rvPullDownIntercept(View child) {
        boolean intercept = false;

        RecyclerView recyclerChild = (RecyclerView) child;
        if (recyclerChild.computeVerticalScrollOffset() <= 0)
            intercept = true;

        return intercept;
    }
    /**
     * 获取RecyclerView是否滚动到底部
     * @param child
     * @return
     */
    public boolean rvPullUpIntercept(View child) {
        boolean intercept = false;

        RecyclerView recyclerChild = (RecyclerView) child;
        if (recyclerChild.computeVerticalScrollExtent() + recyclerChild.computeVerticalScrollOffset()
                >= recyclerChild.computeVerticalScrollRange())
            intercept = true;

        return intercept;
    }


    /**
     * 检查是否可以下拉刷新
     * 当使用过程中需要自己控制是否需要刷新时可以实现这个回调接口
     */
    public OnCheckCanRefreshListener mOnCheckCanRefreshListener;
    public void setOnCheckCanRefreshListener(OnCheckCanRefreshListener mOnCheckCanRefreshListener){
        this.mOnCheckCanRefreshListener=mOnCheckCanRefreshListener;
    }

    /**
     * 检查是否可以加载更多
     * 当使用过程中需要自己控制是否需要加载更多时可以实现这个回调接口
     */
    public OnCheckCanLoadMoreListener mOnCheckCanLoadMoreListener;
    public void setOnCheckCanLoadMoreListener(OnCheckCanLoadMoreListener mOnCheckCanLoadMoreListener){
        this.mOnCheckCanLoadMoreListener=mOnCheckCanLoadMoreListener;
    }
}

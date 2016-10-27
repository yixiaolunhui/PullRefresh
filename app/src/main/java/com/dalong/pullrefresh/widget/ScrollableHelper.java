package com.dalong.pullrefresh.widget;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ScrollView;

/**
 * 判别当前view是否到达了底部或者顶部  告知是否需要下拉刷新和加载更多
 * Created by zhouweilong on 2016/10/27.
 */
public class ScrollableHelper {

    private ScrollableContainer mCurrentScrollableCainer;

    /**
     * 一个自定义view包含 ScrollView/ListView/RecycelerView..
     */
    public interface ScrollableContainer{
        /**
         * @return ScrollView/ListView/RecycelerView..'s instance
         */
        View getScrollableView();
    }

    public void setCurrentScrollableContainer(ScrollableContainer scrollableContainer) {
        this.mCurrentScrollableCainer = scrollableContainer;
    }

    private View getScrollableView() {
        if (mCurrentScrollableCainer == null) {
            return null;
        }
        return mCurrentScrollableCainer.getScrollableView();
    }

    /**
     *
     * 判断是否滑动到顶部方法,ScrollAbleLayout根据此方法来做一些逻辑判断
     * 目前只实现了AdapterView,ScrollView,RecyclerView
     * 需要支持其他view可以自行补充实现
     * @return
     */
    public boolean isTop() {
        View scrollableView = getScrollableView();
        if (scrollableView == null) {
            return true;
        }
        if (scrollableView instanceof AdapterView) {
            return isAdapterViewTop((AdapterView) scrollableView);
        }
        if (scrollableView instanceof ScrollView) {
            return isScrollViewTop((ScrollView) scrollableView);
        }
        if (scrollableView instanceof RecyclerView) {
            return isRecyclerViewTop((RecyclerView) scrollableView);
        }
        if (scrollableView instanceof WebView) {
            return isWebViewTop((WebView) scrollableView);
        }
        throw new IllegalStateException("scrollableView must be a instance of AdapterView|ScrollView|RecyclerView");
    }

    private static boolean isRecyclerViewTop(RecyclerView recyclerView) {
        if (recyclerView != null) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                int firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                View childAt = recyclerView.getChildAt(0);
                if (childAt == null || (firstVisibleItemPosition == 0 && childAt.getTop() == 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isAdapterViewTop(AdapterView adapterView){
        if(adapterView != null){
            int firstVisiblePosition = adapterView.getFirstVisiblePosition();
            View childAt = adapterView.getChildAt(0);
            if(childAt == null || (firstVisiblePosition == 0 && childAt.getTop() == 0)){
                return true;
            }
        }
        return false;
    }

    private static boolean isScrollViewTop(ScrollView scrollView){
        if(scrollView != null) {
            int scrollViewY = scrollView.getScrollY();
            return scrollViewY <= 0;
        }
        return false;
    }

    private static boolean isWebViewTop(WebView scrollView){
        if(scrollView != null) {
            int scrollViewY = scrollView.getScrollY();
            return scrollViewY <= 0;
        }
        return false;
    }

    @SuppressLint("NewApi")
    public void smoothScrollBy(int velocityY, int distance, int duration) {
        View scrollableView = getScrollableView();
        if (scrollableView instanceof AbsListView) {
            AbsListView absListView = (AbsListView) scrollableView;
            if (Build.VERSION.SDK_INT >= 21) {
                absListView.fling(velocityY);
            } else {
                absListView.smoothScrollBy(distance, duration);
            }
        } else if (scrollableView instanceof ScrollView) {
            ((ScrollView) scrollableView).fling(velocityY);
        } else if (scrollableView instanceof RecyclerView) {
            ((RecyclerView) scrollableView).fling(0, velocityY);
        } else if (scrollableView instanceof WebView) {
            ((WebView) scrollableView).flingScroll(0, velocityY);
        }
    }



    /**
     *
     * 判断是否滑动到底部方法,ScrollAbleLayout根据此方法来做一些逻辑判断
     * 目前只实现了AdapterView,ScrollView,RecyclerView
     * 需要支持其他view可以自行补充实现
     * @return
     */
    public boolean isBottom() {
        View scrollableView = getScrollableView();
        if (scrollableView == null) {
            return true;
        }
        if (scrollableView instanceof AdapterView) {
            return isAdapterViewBottom((AdapterView) scrollableView);
        }
        if (scrollableView instanceof ScrollView) {
            return isScrollViewBottom((ScrollView) scrollableView);
        }
        if (scrollableView instanceof RecyclerView) {
            return isRecyclerViewBottom((RecyclerView) scrollableView);
        }
        if (scrollableView instanceof WebView) {
            return isWebViewBottom((WebView) scrollableView);
        }
        throw new IllegalStateException("scrollableView must be a instance of AdapterView|ScrollView|RecyclerView");
    }

    /**
     * 检查WebView 是否到了底部
     * @param scrollableView
     * @return
     */
    private boolean isWebViewBottom(WebView scrollableView) {
        boolean intercept = false;
        WebView webView = (WebView) scrollableView;
        if(webView.getContentHeight()*webView.getScale()-(webView.getHeight()+webView.getScrollY())==0){
            //已经处于底端
            intercept = true;
        }
        return intercept;
    }

    /**
     * 判别RecyclerView是否到了底部
     * @param scrollableView
     * @return
     */
    private boolean isRecyclerViewBottom(RecyclerView scrollableView) {
        boolean intercept = false;
        RecyclerView recyclerChild = (RecyclerView) scrollableView;
        if (recyclerChild.computeVerticalScrollExtent() + recyclerChild.computeVerticalScrollOffset()
                >= recyclerChild.computeVerticalScrollRange())
            intercept = true;

        return intercept;
    }

    /**
     * 检查ScrollView是否达到了底部
     * @param scrollableView
     * @return
     */
    private boolean isScrollViewBottom(ScrollView scrollableView) {
        boolean intercept = false;
        ScrollView scrollView = (ScrollView) scrollableView;
        View scrollChild = scrollView.getChildAt(0);
        if (scrollView.getScrollY() >= (scrollChild.getHeight() - scrollView.getHeight())) {
            intercept = true;
        }
        return intercept;
    }

    /**
     * 检查AdapterView是否到了底部
     * @param scrollableView
     * @return
     */
    private boolean isAdapterViewBottom(AdapterView scrollableView) {
        boolean intercept = false;
        AdapterView adapterChild = (AdapterView) scrollableView;
        // 判断AbsListView是否已经到达内容最底部
        if (adapterChild.getLastVisiblePosition() == adapterChild.getCount() - 1
                && (adapterChild.getChildAt(adapterChild.getChildCount() - 1).getBottom() == adapterChild.getMeasuredHeight())) {
            // 如果到达底部，则拦截事件
            intercept = true;
        }
        return intercept;
    }




}

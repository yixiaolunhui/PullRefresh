package com.dalong.refreshlayout;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhouweilong on 2016/10/19.
 */

public class RefreshLayout extends RefreshInterceptLauyout {

    // 事件监听接口
    private OnRefreshListener listener;
    // Layout状态
    private RefreshStatus status = RefreshStatus.DEFAULT;
    //阻尼系数
    private float damp = 0.4f;
    //恢复动画的执行时间
    public int SCROLL_TIME = 300;
    //是否刷新完成
    private boolean isRefreshSuccess = false;
    //是否加载完成
    private boolean isLoadSuccess = false;
    //是否可以下拉刷新
    private boolean isCanRefresh=true;
    //是否可以加载更多
    private boolean isCanLoad=true;
    //是否自动下拉刷新
    private boolean isAutoRefresh=false;
    //正在加载中
    public boolean isLoading=false;
    //正在刷新中
    public boolean isRefreshing=false;

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置是否支持下拉刷新
     * @param isCanRefresh
     */
    public void setCanRefresh(boolean isCanRefresh){
        this.isCanRefresh=isCanRefresh;
    }

    /**
     * 设置是否支持加载更多
     * @param isCanLoad
     */
    public void setCanLoad(boolean isCanLoad){
        this.isCanLoad=isCanLoad;
    }

    /**
     * 设置是否支持自动刷新
     * @param isAutoRefresh
     */
    public void setAutoRefresh(boolean isAutoRefresh){
        this.isAutoRefresh=isAutoRefresh;
        autoRefresh();
    }


    /**
     * 自动刷新
     */
    public void autoRefresh(){
        if(!isAutoRefresh)return;
        isRefreshing=true;
        measureView(header);
        int end = header.getMeasuredHeight();
        performAnim(0, -end, new AnimListener() {
            @Override
            public void onDoing() {
                updateStatus(status.REFRESH_SCROLLING);
            }

            @Override
            public void onEnd() {
                updateStatus(status.REFRESH_DOING);
            }
        });

    }

    /**
     * 测量view
     * @param v
     */
    public void measureView(View v) {
        if (v == null) {
            return;
        }
        int w = MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
    }
    /**
     * 设置接口回调
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                // 计算本次滑动的Y轴增量(距离)
                int dy = y - lastYMove;
                // 如果getScrollY<0，即下拉操作
                if (getScrollY() < 0) {
                    if (header != null&&isCanRefresh&&!isLoading&&!isRefreshing) {
                        // 进行Y轴上的滑动
                        performScroll(dy);
                        if (Math.abs(getScrollY()) > header.getMeasuredHeight()) {
                            updateStatus(status.DOWN_AFTER);
                        } else {
                            updateStatus(status.DOWN_BEFORE);
                        }
                    }
                }
                // 如果getScrollY>=0，即上拉操作
                else {
                    if (footer != null&&isCanLoad&&!isRefreshing&&!isLoading) {
                        // 进行Y轴上的滑动
                        performScroll(dy);
                        if (getScrollY() >= bottomScroll + footer.getMeasuredHeight()) {
                            updateStatus(status.UP_AFTER);
                        } else {
                            updateStatus(status.UP_BEFORE);
                        }
                    }
                }
                // 记录y坐标
                lastYMove = y;
                break;
            }

            case MotionEvent.ACTION_UP: {
                // 判断本次触摸系列事件结束时,Layout的状态
                switch (status) {
                    //下拉刷新
                    case DOWN_BEFORE:
                        scrolltoDefaultStatus(status.REFRESH_CANCEL_SCROLLING);
                        break;
                    case DOWN_AFTER:
                        scrolltoRefreshStatus();
                        break;
                    //上拉加载更多
                    case UP_BEFORE:
                        scrolltoDefaultStatus(status.LOADMORE_CANCEL_SCROLLING);
                        break;
                    case UP_AFTER:
                        scrolltoLoadStatus();
                        break;
                    default:
                        break;
                }
            }
        }
        lastYIntercept = 0;
        postInvalidate();
        return true;
    }

    /**
     * 刷新状态
     * @param status
     */
    private void updateStatus(RefreshStatus status) {
        this.status = status;
        int scrollY = getScrollY();
        // 判断本次触摸系列事件结束时,Layout的状态
        switch (status) {
            //默认状态
            case DEFAULT:
                onDefault();
                break;
            //下拉刷新
            case DOWN_BEFORE:
                pullHeader.onDownBefore(scrollY);
                break;
            case DOWN_AFTER:
                pullHeader.onDownAfter(scrollY);
                break;
            case REFRESH_SCROLLING:
                pullHeader.onRefreshScrolling(scrollY);
                break;
            case REFRESH_DOING:
                pullHeader.onRefreshDoing(scrollY);
                if(listener!=null)
                    listener.onRefresh();
                break;
            case REFRESH_COMPLETE_SCROLLING:
                pullHeader.onRefreshCompleteScrolling(scrollY, isRefreshSuccess);
                break;
            case REFRESH_CANCEL_SCROLLING:
                pullHeader.onRefreshCancelScrolling(scrollY);
                break;
            //上拉加载更多
            case UP_BEFORE:
                pullFooter.onUpBefore(scrollY);
                break;
            case UP_AFTER:
                pullFooter.onUpAfter(scrollY);
                break;
            case LOADMORE_SCROLLING:
                pullFooter.onLoadScrolling(scrollY);
                break;
            case LOADMORE_DOING:
                pullFooter.onLoadDoing(scrollY);
                if(listener!=null)
                    listener.onLoadMore();
                break;
            case LOADMORE_COMPLETE_SCROLLING:
                pullFooter.onLoadCompleteScrolling(scrollY, isLoadSuccess);
                break;
            case LOADMORE_CANCEL_SCROLLING:
                pullFooter.onLoadCancelScrolling(scrollY);
                break;
        }
    }

    /**
     * 默认状态
     */
    private void onDefault() {
        isRefreshSuccess = false;
        isLoadSuccess = false;
    }

    /**
     * 滚动到加载状态
     */
    private void scrolltoLoadStatus() {
        isLoading=true;
        int start = getScrollY();
        int end = footer.getMeasuredHeight() + bottomScroll;
        performAnim(start, end, new AnimListener() {
            @Override
            public void onDoing() {
                updateStatus(status.LOADMORE_SCROLLING);
            }

            @Override
            public void onEnd() {
                updateStatus(status.LOADMORE_DOING);
            }
        });
    }

    /**
     * 滚动到刷新状态
     */
    private void scrolltoRefreshStatus() {
        isRefreshing=true;
        int start = getScrollY();
        int end = -header.getMeasuredHeight();
        performAnim(start, end, new AnimListener() {
            @Override
            public void onDoing() {
                updateStatus(status.REFRESH_SCROLLING);
            }

            @Override
            public void onEnd() {
                updateStatus(status.REFRESH_DOING);
            }
        });
    }

    /**
     * 滚动到默认状态
     * @param startStatus
     */
    private void scrolltoDefaultStatus(final RefreshStatus startStatus) {
        int start = getScrollY();
        int end = 0;
        performAnim(start, end, new AnimListener() {
            @Override
            public void onDoing() {
                updateStatus(startStatus);
            }

            @Override
            public void onEnd() {
                updateStatus(status.DEFAULT);
            }
        });
    }

    /**
     * 停止刷新
     * @param isSuccess
     */
    public void stopRefresh(boolean isSuccess) {
        isRefreshSuccess = isSuccess;
        isRefreshing=false;
        scrolltoDefaultStatus(RefreshStatus.REFRESH_COMPLETE_SCROLLING);
    }

    /**
     * 停止加载更多
     * @param isSuccess
     */
    public void stopLoadMore(boolean isSuccess) {
        isLoadSuccess = isSuccess;
        isLoading=false;
        scrolltoDefaultStatus(RefreshStatus.LOADMORE_COMPLETE_SCROLLING);
    }

    /**
     * 执行滑动
     * @param dy
     */
    public void performScroll(int dy) {
        scrollBy(0, (int) (-dy * damp));
    }

    /**
     * 执行动画
     * @param start
     * @param end
     * @param listener
     */
    private void performAnim(int start, int end, final AnimListener listener) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(SCROLL_TIME).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                scrollTo(0, value);
                postInvalidate();
                listener.onDoing();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                listener.onEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    interface AnimListener {
        void onDoing();
        void onEnd();
    }

}

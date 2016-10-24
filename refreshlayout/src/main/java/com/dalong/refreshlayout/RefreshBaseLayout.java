package com.dalong.refreshlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * 自定义viewgrop 添加头部和底部 默认隐藏头和底部
 * Created by zhouweilong on 2016/10/19.
 */

public class RefreshBaseLayout extends ViewGroup {

    //头部布局
    public View header;
    //头部content
    public View headerContent;
    //底部布局
    public View footer;
    //头部下拉监听接口
    public OnHeaderListener mOnHeaderListener;
    //底部上啦监听接口
    public OnFooterListener mOnFooterListener;
    // 当滚动到内容最底部时Y轴所需要的滑动值
    public int bottomScroll;
    // 最后一个childview的index
    public int lastChildIndex;

    public RefreshBaseLayout(Context context) {
        super(context);
    }

    public RefreshBaseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置头部监听回调
     * @param mOnHeaderListener
     */
    public void setOnHeaderListener(OnHeaderListener mOnHeaderListener) {
        this.mOnHeaderListener = mOnHeaderListener;
    }

    /**
     * 设置底部监听回调
     * @param mOnFooterListener
     */
    public void setOnFooterListener(OnFooterListener mOnFooterListener) {
        this.mOnFooterListener = mOnFooterListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //获取最后一个子view的index 等于所有子view的数量-1
        lastChildIndex = getChildCount() - 1;
    }

    /**
     * 添加上拉刷新布局作为header
     * @param header 头布局
     */
    public void addHeader(View header) {
        this.header = header;
        headerContent =header.findViewById(R.id.refresh_header_content);
        if(headerContent==null)headerContent=header;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        addView(header, layoutParams);
    }

    /**
     * 添加下拉加载布局作为footer
     * @param footer 底布局
     */
    public void addFooter(View footer) {
        this.footer = footer;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        addView(footer, layoutParams);
    }

    /**
     * 测量方法  遍历左右子view进行测量  当子view显示状态为GONE的时候不测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 遍历所有子view 区分头部放置viewgrop的上面 底部放置viewgrop的下面 剩下的按顺序摆放
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 重置(避免重复累加)
        int contentHeight = 0;

        // 遍历进行子视图的置位工作
        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);
            if (child.getVisibility() == GONE) {
                continue;
            }
            // 头视图隐藏在ViewGroup的顶端
            if (child == header) {
                child.layout(0, 0 - child.getMeasuredHeight(), child.getMeasuredWidth(), 0);
            } else if (child == footer) {// 尾视图隐藏在ViewGroup所有内容视图之后
                child.layout(0, contentHeight, child.getMeasuredWidth(), contentHeight + child.getMeasuredHeight());
            }
            // 内容视图根据定义(插入)顺序,按由上到下的顺序在垂直方向进行排列
            else {
                child.layout(0, contentHeight, child.getMeasuredWidth(), contentHeight + child.getMeasuredHeight());
                if (index <= lastChildIndex) {
                    if (child instanceof ScrollView) {
                        contentHeight += getMeasuredHeight();
                        continue;
                    }
                    contentHeight += child.getMeasuredHeight();
                }
            }
        }
        // 计算到达内容最底部时ViewGroup的滑动距离
        bottomScroll = contentHeight - getMeasuredHeight();
    }
}

package com.dalong.pullrefresh.view.jd;

import android.content.Context;
import android.util.AttributeSet;

import com.dalong.refreshlayout.FooterView;
import com.dalong.refreshlayout.RefreshLayout;

/**
 * 京东下拉刷新的layout
 * Created by zhouweilong on 2016/10/24.
 */

public class JDRefreshLayout extends RefreshLayout {
    public JDRefreshLayout(Context context) {
        super(context);
    }

    public JDRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void init() {
        JdHeader header = new JdHeader(getContext());
        FooterView footer = new FooterView(getContext());

        addHeader(header);
        addFooter(footer);
        setOnHeaderListener(header);
        setOnFooterListener(footer);
    }
}

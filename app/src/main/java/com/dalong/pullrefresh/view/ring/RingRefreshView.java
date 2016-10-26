package com.dalong.pullrefresh.view.ring;


import android.content.Context;
import android.util.AttributeSet;

import com.dalong.refreshlayout.FooterView;
import com.dalong.refreshlayout.RefreshLayout;

/**
 * Ring下拉刷新
 * Created by zhouweilong on 2016/10/19.
 */

public class RingRefreshView extends RefreshLayout {

    public RingRefreshView(Context context) {
        super(context);
    }

    public RingRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void init() {
        RingHeader header = new RingHeader(getContext());
        FooterView footer = new FooterView(getContext());

        addHeader(header);
        addFooter(footer);
        setOnHeaderListener(header);
        setOnFooterListener(footer);
    }
}

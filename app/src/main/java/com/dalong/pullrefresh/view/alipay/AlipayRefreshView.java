package com.dalong.pullrefresh.view.alipay;


import android.content.Context;
import android.util.AttributeSet;

import com.dalong.refreshlayout.FooterView;
import com.dalong.refreshlayout.RefreshLayout;

/**
 * 支付宝下拉刷新
 * Created by zhouweilong on 2016/10/19.
 */

public class AlipayRefreshView extends RefreshLayout {

    public AlipayRefreshView(Context context) {
        super(context);
    }

    public AlipayRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void init() {
        AlipayHeader header = new AlipayHeader(getContext());
        FooterView footer = new FooterView(getContext());

        addHeader(header);
        addFooter(footer);
        setOnHeaderListener(header);
        setOnFooterListener(footer);
    }
}

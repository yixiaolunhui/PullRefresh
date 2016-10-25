package com.dalong.pullrefresh.view.qq;


import android.content.Context;
import android.util.AttributeSet;

import com.dalong.pullrefresh.view.meituan.MeiTuanHeader;
import com.dalong.refreshlayout.FooterView;
import com.dalong.refreshlayout.RefreshLayout;

/**
 * qq下拉刷新
 * Created by zhouweilong on 2016/10/19.
 */

public class QQRefreshView extends RefreshLayout {

    public QQRefreshView(Context context) {
        super(context);
    }

    public QQRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void init() {
        QQHeader header = new QQHeader(getContext());
        FooterView footer = new FooterView(getContext());

        addHeader(header);
        addFooter(footer);
        setOnHeaderListener(header);
        setOnFooterListener(footer);
    }
}

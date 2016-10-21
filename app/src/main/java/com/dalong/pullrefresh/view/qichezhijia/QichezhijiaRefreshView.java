package com.dalong.pullrefresh.view.qichezhijia;


import android.content.Context;
import android.util.AttributeSet;

import com.dalong.pullrefresh.view.meituan.MeiTuanHeader;
import com.dalong.refreshlayout.FooterView;
import com.dalong.refreshlayout.RefreshLayout;

/**
 * Created by zhouweilong on 2016/10/19.
 */

public class QichezhijiaRefreshView extends RefreshLayout {

    public QichezhijiaRefreshView(Context context) {
        super(context);
    }

    public QichezhijiaRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void init() {
        QiCheZhiJiaHeader header = new QiCheZhiJiaHeader(getContext());
        FooterView footer = new FooterView(getContext());

        addHeader(header);
        addFooter(footer);
        setHeader(header);
        setFooter(footer);
    }
}

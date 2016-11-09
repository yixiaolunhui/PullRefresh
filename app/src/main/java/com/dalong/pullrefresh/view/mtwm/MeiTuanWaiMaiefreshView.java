package com.dalong.pullrefresh.view.mtwm;


import android.content.Context;
import android.util.AttributeSet;

import com.dalong.pullrefresh.view.qichezhijia.QiCheZhiJiaHeader;
import com.dalong.refreshlayout.FooterView;
import com.dalong.refreshlayout.RefreshLayout;

/**
 * Created by zhouweilong on 2016/10/19.
 */

public class MeiTuanWaiMaiefreshView extends RefreshLayout {

    public MeiTuanWaiMaiefreshView(Context context) {
        super(context);
    }

    public MeiTuanWaiMaiefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void init() {
        MeiTuanWaiMaiHeader header = new MeiTuanWaiMaiHeader(getContext());
        FooterView footer = new FooterView(getContext());

        addHeader(header);
        addFooter(footer);
        setOnHeaderListener(header);
        setOnFooterListener(footer);
    }
}

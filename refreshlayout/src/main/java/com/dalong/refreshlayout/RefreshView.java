package com.dalong.refreshlayout;


import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by zhouweilong on 2016/10/19.
 */

public class RefreshView extends RefreshLayout {

    public RefreshView(Context context) {
        super(context);
    }

    public RefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void init() {
        HeaderView header = new HeaderView(getContext());
        FooterView footer = new FooterView(getContext());

        addHeader(header);
        addFooter(footer);

        setHeader(header);
        setFooter(footer);
    }
}

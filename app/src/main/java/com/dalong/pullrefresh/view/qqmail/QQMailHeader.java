package com.dalong.pullrefresh.view.qqmail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.dalong.pullrefresh.R;
import com.dalong.refreshlayout.OnHeaderListener;

/**
 * Created by zhouweilong on 2016/11/9.
 */

public class QQMailHeader  extends RelativeLayout implements OnHeaderListener {

    private  QQMailView mQQMailView;
    private View headerView;

    public QQMailHeader(Context context) {
        super(context);
        headerView= LayoutInflater.from(context).inflate(R.layout.qq_mail_view, this, true);
        mQQMailView = (QQMailView) headerView.findViewById(R.id.qq_mail);
        measureView(headerView);
    }

    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }
    @Override
    public void onRefreshBefore(int scrollY, int refreshHeight, int headerHeight) {
        mQQMailView.setDistanceAndAlpha(Math.abs(scrollY)/(1.0f*refreshHeight));
    }

    @Override
    public void onRefreshAfter(int scrollY, int refreshHeight, int headerHeight) {

    }

    @Override
    public void onRefreshReady(int scrollY, int refreshHeight, int headerHeight) {

    }

    @Override
    public void onRefreshing(int scrollY, int refreshHeight, int headerHeight) {
        mQQMailView.start();
    }

    @Override
    public void onRefreshComplete(int scrollY, int refreshHeight, int headerHeight, boolean isRefreshSuccess) {
        mQQMailView.stop();
        mQQMailView.setDistanceAndAlpha(Math.abs(scrollY)/(1.0f*refreshHeight));
    }

    @Override
    public void onRefreshCancel(int scrollY, int refreshHeight, int headerHeight) {
        mQQMailView.setDistanceAndAlpha(Math.abs(scrollY)/(1.0f*refreshHeight));
        mQQMailView.stop();
    }
}

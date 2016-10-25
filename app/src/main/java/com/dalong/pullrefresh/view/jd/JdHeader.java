package com.dalong.pullrefresh.view.jd;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dalong.pullrefresh.R;
import com.dalong.refreshlayout.OnHeaderListener;

/**
 * 京东下拉刷新头布局
 * Created by zhouweilong on 2016/10/24.
 */

public class JdHeader extends RelativeLayout implements OnHeaderListener  {
    private static final int DONE = 0;
    private static final int PULL_TO_REFRESH = 1;
    private static final int RELEASE_TO_REFRESH = 2;
    private static final int REFRESHING = 3;

    private  AnimationDrawable secondAnimation;
    private  int headerViewHeight;
    private  SecondStepView secondStepView;
    private  FirstSetpView firstSetpView;
    private  TextView tv_pull_to_refresh;
    private  View headerView;

    public JdHeader(Context context) {
        super(context);
        headerView=LayoutInflater.from(context).inflate(R.layout.jd_refresh_view, this, true);
        tv_pull_to_refresh = (TextView) headerView.findViewById(R.id.tv_pull_to_refresh);
        firstSetpView = (FirstSetpView) headerView.findViewById(R.id.first_step_view);
        secondStepView = (SecondStepView) headerView.findViewById(R.id.second_step_view);
        secondStepView.setBackgroundResource(R.drawable.jd_animation);
        secondAnimation = (AnimationDrawable) secondStepView.getBackground();
        measureView(headerView);
        changeHeaderByState(DONE);
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
        firstSetpView.setCurrentProgress(Math.abs(scrollY)/(1.0f*getHeight()));
        firstSetpView.postInvalidate();
        changeHeaderByState(PULL_TO_REFRESH);
    }

    @Override
    public void onRefreshAfter(int scrollY, int refreshHeight, int headerHeight) {
        changeHeaderByState(RELEASE_TO_REFRESH);
    }

    @Override
    public void onRefreshReady(int scrollY, int refreshHeight, int headerHeight) {

    }

    @Override
    public void onRefreshing(int scrollY, int refreshHeight, int headerHeight) {
        changeHeaderByState(REFRESHING);
    }

    @Override
    public void onRefreshComplete(int scrollY, int refreshHeight, int headerHeight, boolean isRefreshSuccess) {
        changeHeaderByState(DONE);
    }

    @Override
    public void onRefreshCancel(int scrollY, int refreshHeight, int headerHeight) {
        changeHeaderByState(DONE);
    }


    private void changeHeaderByState(int state){
        switch (state) {
            case DONE:
                headerView.setPadding(0, -headerViewHeight, 0, 0);
                firstSetpView.setVisibility(View.VISIBLE);
                secondAnimation.stop();
                secondStepView.setVisibility(View.GONE);
                break;
            case RELEASE_TO_REFRESH:
                tv_pull_to_refresh.setText("松开刷新");

                break;
            case PULL_TO_REFRESH:
                tv_pull_to_refresh.setText("下拉刷新");
                state = DONE;
                firstSetpView.setVisibility(View.VISIBLE);
                secondAnimation.stop();
                secondStepView.setVisibility(View.GONE);
                break;
            case REFRESHING:
                tv_pull_to_refresh.setText("正在刷新");
                firstSetpView.setVisibility(View.GONE);
                secondStepView.setVisibility(View.VISIBLE);
                secondAnimation.stop();
                secondAnimation.start();
                break;
            default:
                break;
        }
    }
}

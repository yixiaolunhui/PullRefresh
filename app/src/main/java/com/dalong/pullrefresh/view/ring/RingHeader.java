package com.dalong.pullrefresh.view.ring;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dalong.pullrefresh.R;
import com.dalong.pullrefresh.widget.RingProgressBar;
import com.dalong.refreshlayout.OnHeaderListener;

/**
 * Created by zhouweilong on 2016/10/25.
 */

public class RingHeader extends RelativeLayout implements OnHeaderListener {

    private  RotateAnimation refreshingAnimation;
    private  TextView ring_refresh_status;
    private  RingProgressBar progress_bar;
    private View headerView;

    public RingHeader(Context context) {
        super(context);
        headerView= LayoutInflater.from(context).inflate(R.layout.ring_refresh_header, this, true);
        progress_bar=(RingProgressBar)headerView.findViewById(R.id.progress_bar);
        ring_refresh_status=(TextView)headerView.findViewById(R.id.ring_refresh_status);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                context, R.anim.rotating);
        // 添加匀速转动动画
        LinearInterpolator lir = new LinearInterpolator();
        refreshingAnimation.setInterpolator(lir);
        progress_bar.setProgress(90);
    }
    @Override
    public void onRefreshBefore(int scrollY, int refreshHeight, int headerHeight) {
        int progress=(int) ((Math.abs(scrollY)/(1.0f*refreshHeight))*100);
        ring_refresh_status.setText("下拉刷新");
        progress_bar.setProgress(progress>90?90:progress);
        progress_bar.setIsShowIcon(true);
    }

    @Override
    public void onRefreshAfter(int scrollY, int refreshHeight, int headerHeight) {
        ring_refresh_status.setText("松开刷新");
        progress_bar.setIsShowIcon(false);
    }

    @Override
    public void onRefreshReady(int scrollY, int refreshHeight, int headerHeight) {

    }

    @Override
    public void onRefreshing(int scrollY, int refreshHeight, int headerHeight) {
        ring_refresh_status.setText("正在刷新");
        progress_bar.startAnimation(refreshingAnimation);
    }

    @Override
    public void onRefreshComplete(int scrollY, int refreshHeight, int headerHeight, boolean isRefreshSuccess) {
        ring_refresh_status.setText(isRefreshSuccess?"刷新完成":"刷新失败");
        progress_bar.clearAnimation();
    }

    @Override
    public void onRefreshCancel(int scrollY, int refreshHeight, int headerHeight) {
        ring_refresh_status.setText("刷新取消");
        progress_bar.clearAnimation();
    }
}

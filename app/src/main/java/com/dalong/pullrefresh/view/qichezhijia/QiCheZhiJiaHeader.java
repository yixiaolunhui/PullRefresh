package com.dalong.pullrefresh.view.qichezhijia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dalong.pullrefresh.R;
import com.dalong.refreshlayout.OnHeaderListener;

/**
 * 汽车之家的下拉刷新
 * Created by zhouweilong on 2016/10/21.
 */

public class QiCheZhiJiaHeader  extends RelativeLayout implements OnHeaderListener {
    private static final int DONE = 0;
    private static final int PULL_TO_REFRESH = 1;
    private static final int RELEASE_TO_REFRESH = 2;
    private static final int REFRESHING = 3;
    private static final int RATIO = 3;
    private  QicheZjView mQicheZjView;
    private  PointerView mPointerView;
    private  FrameLayout mAnimContainer;
    private  TextView mPullRefreshTV;
    private  Animation animation;
    private  View headerView;
    private int headerViewHeight;

    public QiCheZhiJiaHeader(Context context) {
        super(context);
        headerView=LayoutInflater.from(context).inflate(R.layout.qczj_refresh_view, this, true);
        mQicheZjView = (QicheZjView) headerView.findViewById(R.id.auto_home);
        mPullRefreshTV = (TextView) headerView.findViewById(R.id.tv_pull_to_refresh);
        mAnimContainer = (FrameLayout) headerView.findViewById(R.id.anim_container);
        mPointerView = (PointerView) headerView.findViewById(R.id.anim_pointer);
        animation = AnimationUtils.loadAnimation(context, R.anim.pointer_rotate);
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

    /**
     * 下拉刷新
     * @param scrollY
     */
    @Override
    public void onRefreshBefore(int scrollY,int  refreshHeight,int headerHeight) {
        mQicheZjView.setCurrentProgress(Math.abs(scrollY)/(1.0f*getHeight()));
        mQicheZjView.postInvalidate();
        changeHeaderByState(PULL_TO_REFRESH);
    }

    /**
     * 松手刷新
     * @param scrollY
     */
    @Override
    public void onRefreshAfter(int scrollY,int  refreshHeight,int headerHeight) {
        changeHeaderByState(RELEASE_TO_REFRESH);
    }

    @Override
    public void onRefreshReady(int scrollY,int refreshHeight,int headerHeight) {

    }

    /**
     * 正在刷新
     * @param scrollY
     */
    @Override
    public void onRefreshing(int scrollY,int refreshHeight,int headerHeight) {
        changeHeaderByState(REFRESHING);
    }

    /**
     * 刷新完成
     * @param scrollY
     * @param isRefreshSuccess  刷新的状态  是成功了 还是失败了
     */
    @Override
    public void onRefreshComplete(int scrollY,int refreshHeight,int headerHeight, boolean isRefreshSuccess) {
        changeHeaderByState(DONE);
    }

    /**
     * 取消刷新
     * @param scrollY
     */
    @Override
    public void onRefreshCancel(int scrollY,int refreshHeight,int headerHeight) {
        changeHeaderByState(DONE);
    }

    /**
     * 刷新状态
     * @param state
     */
    private void changeHeaderByState(int state){
        switch (state) {
            case DONE:
                headerView.setPadding(0, -headerViewHeight, 0, 0);
                //第一状态的view显示出来
                mQicheZjView.setVisibility(View.VISIBLE);
                //先停止一下第二阶段view的动画
                mPointerView.clearAnimation();
                //将第二阶段view隐藏起来
                mAnimContainer.setVisibility(View.GONE);
                break;
            case RELEASE_TO_REFRESH:
                mPullRefreshTV.setText("放开刷新");
                break;
            case PULL_TO_REFRESH:
                mPullRefreshTV.setText("下拉刷新");
                //第一状态view显示出来
                mQicheZjView.setVisibility(View.VISIBLE);
                //停止第二阶段动画
                mPointerView.clearAnimation();
                //将第二阶段view隐藏
                mAnimContainer.setVisibility(View.GONE);
                break;
            case REFRESHING:
                mPullRefreshTV.setText("正在刷新");
                //将第一阶段view隐藏
                mQicheZjView.setVisibility(View.GONE);
                //将第二阶段view显示出来
                mAnimContainer.setVisibility(View.VISIBLE);
                //先停止第二阶段动画
                mPointerView.clearAnimation();
                //启动第二阶段动画
                mPointerView.startAnimation(animation);
                break;
            default:
                break;
        }
    }

}

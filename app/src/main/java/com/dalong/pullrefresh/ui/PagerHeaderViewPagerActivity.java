package com.dalong.pullrefresh.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.dalong.pullrefresh.R;
import com.dalong.pullrefresh.adapter.LocalImageHolderView;
import com.dalong.pullrefresh.adapter.MyFragmentPagerAdapter;
import com.dalong.pullrefresh.fragment.ListViewFragment;
import com.dalong.pullrefresh.fragment.RecyclerViewFragment;
import com.dalong.pullrefresh.fragment.ScrollAbleFragment;
import com.dalong.pullrefresh.fragment.ScrollViewFragment;
import com.dalong.pullrefresh.fragment.WebViewFragment;
import com.dalong.pullrefresh.view.ring.RingRefreshView;
import com.dalong.pullrefresh.widget.PagerSlidingTabStrip;
import com.dalong.pullrefresh.widget.ScrollableLayout;
import com.dalong.refreshlayout.OnCheckCanRefreshListener;
import com.dalong.refreshlayout.OnRefreshListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 带有头部固定的下拉刷新加载更多
 */
public class PagerHeaderViewPagerActivity extends AppCompatActivity {

    private ScrollableLayout mScrollLayout;
    private RingRefreshView mRingRefreshView;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private ConvenientBanner convenientBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_header);
        initData();
        initView();
    }

    private void initData() {
        //本地图片集合
        for (int position = 0; position < 7; position++) {
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
        }
    }

    private void initView() {
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        mRingRefreshView = (RingRefreshView)findViewById(R.id.refresh_view);
        // 头部图片集
        convenientBanner = (ConvenientBanner)findViewById(R.id.imagepager);
        convenientBanner.setPages( new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, localImages)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
        mScrollLayout = (ScrollableLayout)findViewById(R.id.scrollableLayout);
        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip)findViewById(R.id.pagerStrip);
        initFragmentPager(viewPager,pagerSlidingTabStrip,mScrollLayout);
        mRingRefreshView.setOnCheckCanRefreshListener(new OnCheckCanRefreshListener() {
            @Override
            public boolean checkCanDoRefresh() {
                return mScrollLayout.canRefresh();
            }
        });
        mRingRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
            }

            @Override
            public void onLoadMore() {
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,3000);
            }
        });
    }

    Handler mHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mRingRefreshView.stopRefresh(true);
                    break;
                case 1:
                    mRingRefreshView.stopLoadMore(true);
                    break;
            }
        }
    };


    public void initFragmentPager( ViewPager viewPager,PagerSlidingTabStrip pagerSlidingTabStrip, final ScrollableLayout mScrollLayout) {
        final ArrayList<ScrollAbleFragment> fragmentList = new ArrayList<>();
        fragmentList.add(ListViewFragment.newInstance());
        fragmentList.add(ScrollViewFragment.newInstance());
        fragmentList.add(RecyclerViewFragment.newInstance());
        fragmentList.add(WebViewFragment.newInstance());
        List<String> titleList = new ArrayList<>();
        titleList.add("ListView");
        titleList.add("ScrollView");
        titleList.add("RecyclerView");
        titleList.add("WebView");
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList));
        mScrollLayout.getHelper().setCurrentScrollableContainer(fragmentList.get(0));
        pagerSlidingTabStrip.setViewPager(viewPager);
        pagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.e("onPageSelected", "page:" + i);
                /** 标注当前页面 **/
                mScrollLayout.getHelper().setCurrentScrollableContainer(fragmentList.get(i));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.setCurrentItem(0);
    }

    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }
}

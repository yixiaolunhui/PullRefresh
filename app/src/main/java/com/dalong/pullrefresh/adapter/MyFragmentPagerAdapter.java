package com.dalong.pullrefresh.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dalong.pullrefresh.fragment.ScrollAbleFragment;

import java.util.List;

/**
 * fragment 适配器
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<ScrollAbleFragment> fragmentList;
    private List<String> titleList;

    public MyFragmentPagerAdapter(FragmentManager fm, List<ScrollAbleFragment> fragmentList, List<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}

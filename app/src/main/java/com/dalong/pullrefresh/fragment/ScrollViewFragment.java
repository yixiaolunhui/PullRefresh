package com.dalong.pullrefresh.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.dalong.pullrefresh.R;
import com.dalong.pullrefresh.widget.ScrollableHelper;

/**
 * scrollview例子
 */
public class ScrollViewFragment extends ScrollAbleFragment implements ScrollableHelper.ScrollableContainer{

    private ScrollView scrollView;

    public static ScrollViewFragment newInstance() {
        ScrollViewFragment scrollViewFragment = new ScrollViewFragment();
        return scrollViewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scrollview, container, false);
        scrollView = (ScrollView) view.findViewById(R.id.scrollview);
        return view;
    }

    @Override
    public View getScrollableView() {
        return scrollView;
    }
}

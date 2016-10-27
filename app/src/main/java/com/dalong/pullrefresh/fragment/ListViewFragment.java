package com.dalong.pullrefresh.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dalong.pullrefresh.R;
import com.dalong.pullrefresh.adapter.MyAdapter;
import com.dalong.pullrefresh.widget.ScrollableHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * listview例子
 */
public class ListViewFragment extends ScrollAbleFragment implements ScrollableHelper.ScrollableContainer{

    private ListView mListview;

    public static ListViewFragment newInstance() {
        ListViewFragment listFragment = new ListViewFragment();
        return listFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mListview = (ListView) view.findViewById(R.id.listview);
        List<String> strlist = new ArrayList<String>();
        for (int i = 0; i < new Random().nextInt(100) + 31; i++) {
            strlist.add(String.valueOf(i));
        }
        mListview.setAdapter(new MyAdapter(getActivity(), strlist));
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "点击item" + position, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public View getScrollableView() {
        return mListview;
    }
}

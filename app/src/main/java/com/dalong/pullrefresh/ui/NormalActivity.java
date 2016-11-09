package com.dalong.pullrefresh.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dalong.pullrefresh.R;
import com.dalong.pullrefresh.view.alipay.AlipayRefreshView;
import com.dalong.pullrefresh.view.mtwm.MeiTuanWaiMaiefreshView;
import com.dalong.pullrefresh.view.qqmail.QQMailRefreshLayout;
import com.dalong.refreshlayout.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class NormalActivity extends AppCompatActivity {

    private QQMailRefreshLayout refreshview;
    private ListView listview;

    public List<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        refreshview=(QQMailRefreshLayout)findViewById(R.id.refreshview);
        refreshview.setCanRefresh(true);
        refreshview.setCanLoad(true);
        listview=(ListView)findViewById(R.id.normal_listview);
        refreshview.setOnRefreshListener(new OnRefreshListener() {
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

        listview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list));
    }

    Handler mHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    refreshview.stopRefresh(true);
                    break;
                case 1:
                    refreshview.stopLoadMore(true);
                    break;
            }
        }
    };

    public void initData(){
        for (int i=0;i<15;i++){
            list.add("测试的"+i);
        }
    }
}

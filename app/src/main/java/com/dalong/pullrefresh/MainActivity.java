package com.dalong.pullrefresh;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dalong.refreshlayout.OnRefreshListener;
import com.dalong.refreshlayout.RefreshView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RefreshView refreshview;
    private ListView listview;

    public List<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        refreshview=(RefreshView)findViewById(R.id.refreshview);
        refreshview.setAutoRefresh(true);
        listview=(ListView)findViewById(R.id.listview);
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this, "onRefresh", Toast.LENGTH_SHORT).show();
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,10000);
            }

            @Override
            public void onLoadMore() {
                Toast.makeText(MainActivity.this, "onLoadMore", Toast.LENGTH_SHORT).show();
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1,10000);
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
        for (int i=0;i<16;i++){
            list.add("测试的"+i);
        }
    }
}

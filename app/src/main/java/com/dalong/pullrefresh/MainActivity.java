package com.dalong.pullrefresh;

import android.os.Handler;
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
        listview=(ListView)findViewById(R.id.listview);
        refreshview.setOnPullListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this, "onRefresh", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshview.stopRefresh(false);
                    }
                },3000);
            }

            @Override
            public void onLoadMore() {
                Toast.makeText(MainActivity.this, "onLoadMore", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshview.stopLoadMore(false);
                    }
                },3000);
            }
        });

        listview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list));
    }



    public void initData(){
        for (int i=0;i<16;i++){
            list.add("测试的"+i);
        }
    }
}

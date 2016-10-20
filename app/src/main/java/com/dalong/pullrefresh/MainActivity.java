package com.dalong.pullrefresh;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dalong.refreshlayout.OnRefreshListener;
import com.dalong.refreshlayout.DefaultRefreshView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DefaultRefreshView refreshview;
    private ListView listview;

    public List<String> list=new ArrayList<>();
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        refreshview=(DefaultRefreshView)findViewById(R.id.refreshview);
        refreshview.setAutoRefresh(true);
        listview=(ListView)findViewById(R.id.listview);
        webview=(WebView)findViewById(R.id.webview);
        webview.loadUrl("http://baidu.com");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.v("111111","onRefresh");
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,3000);
            }

            @Override
            public void onLoadMore() {
                Log.v("111111","onLoadMore");
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
        for (int i=0;i<16;i++){
            list.add("测试的"+i);
        }
    }
}

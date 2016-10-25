# PullRefresh
下拉刷新

##使用范围
1、适合所有listview recycleView scrollview other下拉刷新加载更多

####2、自定义刷新头部和底部 方便易用

##测试demo
http://fir.im/c8ex?release_id=58085c52ca87a840ec000547

二维码：
![image](https://github.com/dalong982242260/PullRefresh/blob/master/img/refresh.png?raw=true)


##效果图
###美团
![image](https://github.com/dalong982242260/PullRefresh/blob/master/img/meituan.gif?raw=true)
###汽车之家
![image](https://github.com/dalong982242260/PullRefresh/blob/master/img/qichezhijia.gif?raw=true)
###京东
![image](https://github.com/dalong982242260/PullRefresh/blob/master/img/jd.gif?raw=true)
###QQ
![image](https://github.com/dalong982242260/PullRefresh/blob/master/img/qq.gif?raw=true)


##Usage

Use  it in your gradle

    dependencies {
       compile 'com.dalong:refreshlayout:1.0.1'
    }


Use it in your layout xml.

        <com.dalong.pullrefresh.view.meituan.MeiTuanRefreshView
             android:id="@+id/refreshview"
             android:layout_width="match_parent"
                android:layout_height="match_parent">
            
                <!-- ListView、ScrollView、RecyclerView、Other -->
    
    
        </com.dalong.pullrefresh.view.meituan.MeiTuanRefreshView>

Get instance and use it.

        refreshview=(MeiTuanRefreshView)findViewById(R.id.refreshview);
        refreshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
               // start refresh
            }

            @Override
            public void onLoadMore() {
                // start load
            }
        });

自定义头部图片：

如果需要添加刷新顶部图片需要设置刷新部分的id为refresh_header_content 不设置默认所有view为刷新部分



##版本 

        1.0.1版本
        1、增加自定义刷新头部图标


        1.0.0版本
        1、自动刷新 
        2、自定义刷新布局
        3、支持listview scrollview recycleview等等布局


##Thanks
* [BeautifulRefreshLayout](https://github.com/android-cjj/BeautifulRefreshLayout)
* [android-Ultra-Pull-To-Refresh](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh) 
* [SmartRefreshLayout]( https://github.com/RawnHwang/SmartRefreshLayout) 


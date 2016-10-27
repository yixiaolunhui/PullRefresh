# PullRefresh
下拉刷新

##使用范围
1、适合所有listview recycleView scrollview other下拉刷新加载更多

####2、自定义刷新头部和底部 方便易用

##测试demo
http://fir.im/c8ex?release_id=581196bcca87a832160001c0

二维码：
![image](data:image/png;base64,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)


##效果图

###自定义子view的
![image](https://github.com/dalong982242260/PullRefresh/blob/master/img/pullRefresh.gif?raw=true)
###美团
![image](https://github.com/dalong982242260/PullRefresh/blob/master/img/meituan.gif?raw=true)
###汽车之家
![image](https://github.com/dalong982242260/PullRefresh/blob/master/img/qichezhijia.gif?raw=true)
###京东
![image](https://github.com/dalong982242260/PullRefresh/blob/master/img/jd.gif?raw=true)
###QQ
![image](https://github.com/dalong982242260/PullRefresh/blob/master/img/qq.gif?raw=true)
###淘宝
![image](https://github.com/dalong982242260/PullRefresh/blob/master/img/taobao.gif?raw=true)


##Usage

Use  it in your gradle

    dependencies {
       compile 'com.dalong:refreshlayout:1.0.2'
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
        
        如果子view是自定义的view需要告诉刷新控件是否可以刷新或者加载可以实现此接口
        
             mRingRefreshView.setOnCheckCanRefreshListener(new OnCheckCanRefreshListener() {
                   @Override
                   public boolean checkCanDoRefresh() {
                         return mScrollLayout.canRefresh();
                   }     
             });
             mRingRefreshView.setOnCheckCanLoadMoreListener(new OnCheckCanLoadMoreListener() {
                    @Override
                    public boolean checkCanDoLoadMore() {
                        return mScrollLayout.canLoadMore();
                    }
              });

自定义头部图片：

如果需要添加刷新顶部图片需要设置刷新部分的id为refresh_header_content 不设置默认所有view为刷新部分



##版本 
        1.0.2版本
        1、增加固定头部
        2、刷新库添加检查是否可以刷新加载回调
        
        
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
* [Android-ConvenientBanner](https://github.com/saiwu-bigkoo/Android-ConvenientBanner) 


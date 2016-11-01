# PullRefresh
下拉刷新

##使用范围
1、适合所有listview recycleView scrollview other下拉刷新加载更多

####2、自定义刷新头部和底部 方便易用

##测试demo
http://fir.im/c8ex?release_id=581196bcca87a832160001c0

二维码：
![image](https://github.com/dalong982242260/PullRefresh/blob/master/img/refresh.png?raw=true)


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
###支付宝
![image](https://github.com/dalong982242260/PullRefresh/blob/master/img/alipay.gif?raw=true)


##Usage

Use  it in your gradle

    dependencies {
       compile 'com.dalong:refreshlayout:1.0.3'
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
        1.0.3版本
        1、修复下拉刷新的时候不抬手再上拉会出现加载的view的问题。

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
* [SmileyLoadingView](https://github.com/andyxialm/SmileyLoadingView) 


package com.dalong.pullrefresh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dalong.pullrefresh.ui.NormalActivity;
import com.dalong.pullrefresh.ui.PagerHeaderViewPagerActivity;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    /**
     * 正常的下拉刷新加载更多
     * @param view
     */
    public void onNormal(View view){
        startActivity(new Intent(this, NormalActivity.class));
    }
    public void onFixedAndViewpager(View view){
        startActivity(new Intent(this, PagerHeaderViewPagerActivity.class));
    }
}

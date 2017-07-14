package com.logn.sbook.ui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.logn.sbook.R;
import com.logn.sbook.util.NetworkChangeReceiver;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.shizhefei.view.viewpager.SViewPager;

/**
 * Created by oureda on 2017/6/29.
 */

public class GuideActivity extends FragmentActivity {
    private IndicatorViewPager indicatorViewPager;
    private View centerView;
    private FixedIndicatorView indicator;
    private long formerTime = 0;

    //监听网络链接情况
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //软键盘默认不弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //监听网络
        CheckNetwork();

        SViewPager viewPager = (SViewPager) findViewById(R.id.tabmain_viewPager);
        indicator = (FixedIndicatorView) findViewById(R.id.indicator);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(Color.CYAN, Color.GRAY));
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
//        inflater=LayoutInflater.from(getApplicationContext());

        //这里可以添加一个view，作为centerView，会位于Indicator的tab的中间
        centerView = getLayoutInflater().inflate(R.layout.tab_main_center, indicator, false);
        indicator.setCenterView(centerView);
        centerView.setOnClickListener(onClickListener);
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        viewPager.setCanScroll(false);

        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(1);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == centerView) {
                Intent intent = new Intent();
                if (hasLogin()) {
                    intent.setClass(GuideActivity.this, AddActivity.class);
                } else {
                    intent.setClass(GuideActivity.this, LoginActivity.class);
                }
                startActivity(intent);
            }
        }
    };

    private boolean hasLogin() {
        SharedPreferences sp = getSharedPreferences("sp_login", Context.MODE_PRIVATE);
        String time = sp.getString("login_time", "0");
        if (time.equals("0")) {
            return false;
        }
        return true;
    }

    //监听网络情况
    private void CheckNetwork() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    //中间页面的适配器
    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private String[] tabNames = {"首页", "我"};
        private int[] tabIcons = {R.drawable.tabbar_home,
                R.drawable.tabbar_profile};
        private LayoutInflater inflater;

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.tab_guide
                        , container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(tabNames[position]);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[position], 0, 0);
            return textView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            viewPagerFragment viewPagerFragment = new viewPagerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(viewPagerFragment.INTENT_STRING_TABNAME, tabNames[position]);
            viewPagerFragment.setArguments(bundle);
            return viewPagerFragment;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        long presentTime = System.currentTimeMillis();
        if ((presentTime - formerTime) > 2000) {
            formerTime = presentTime;
            Toast.makeText(this, "在按一次退出旧书店", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }
}

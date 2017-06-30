package com.logn.sbook.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.logn.sbook.MainActivity;
import com.logn.sbook.R;
import com.logn.sbook.beans.BookInfo;
import com.logn.sbook.util.BookAdapter;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oureda on 2017/6/29.
 */

public class GuideActivity extends FragmentActivity {
    private IndicatorViewPager indicatorViewPager;
    private View centerView;
    private FixedIndicatorView indicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        indicator = (FixedIndicatorView) findViewById(R.id.indicator);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(Color.CYAN, Color.GRAY));
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
//        inflater=LayoutInflater.from(getApplicationContext());

        //这里可以添加一个view，作为centerView，会位于Indicator的tab的中间
        centerView = getLayoutInflater().inflate(R.layout.tab_main_center, indicator, false);
        indicator.setCenterView(centerView);
        centerView.setOnClickListener(onClickListener);
        indicatorViewPager.setAdapter(adapter);

        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(1);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == centerView) {
                //还可以移除哦
                //indicator.removeCenterView();
                Toast.makeText(getApplicationContext(), "点击了CenterView", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private IndicatorViewPager.IndicatorPagerAdapter adapter = new
            IndicatorViewPager.IndicatorViewPagerAdapter() {
                private LayoutInflater inflater;
                public int[] layout = {
                        R.layout.activity_main,
                        R.layout.activity_mine
                };
                private String[] tabNames = {"首页", "我"};
                private int[] tabIcons = {
                        R.drawable.main, R.drawable.mine
                };

                @Override
                public int getCount() {
                    return layout.length;
                }


                @Override
                public View getViewForTab(int position, View convertView, ViewGroup container) {
                    if (convertView == null) {
                        inflater = LayoutInflater.from(getApplicationContext());
                        convertView = inflater.inflate(R.layout.tab_guide
                                , container, false);
                    }
                    TextView textView = (TextView) convertView;
                    textView.setText(tabNames[position]);
                    textView.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[position], 0, 0);
                    return textView;
                }

                @Override
                public View getViewForPage(int position, View convertView, ViewGroup container) {
                    int resourceId = layout[position];
//                    if (convertView==null){
//                        convertView=new View(getApplicationContext());
//                        convertView.setLayoutParams(new ViewGroup.LayoutParams(
//                                ViewGroup.LayoutParams.MATCH_PARENT,
//                                ViewGroup.LayoutParams.MATCH_PARENT
//                        ));
//                    }
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                        convertView.setLayoutDirection();
//                    }
//                    return convertView;
                    View view;
                    if (convertView == null) {

                        view = LayoutInflater.from(getApplicationContext()).inflate(resourceId, container, false);

                        if (resourceId == R.layout.activity_main) {
                            List<BookInfo> list = new ArrayList<>();
                            initBook(list);
                            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
                            Log.e("132", "null???");
                            LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            BookAdapter bookAdapter = new BookAdapter(GuideActivity.this, list);
                            recyclerView.setAdapter(bookAdapter);
                        }
                    } else {
                        view = convertView;
                    }
                    return view;

                }
            };

    //从后台数据库获取Book数据
    private void initBook(List<BookInfo> list) {
        //example for test
        for (int i = 0; i < 5; i++) {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setAuthor("banz" + i);
            bookInfo.setBookImageId(R.drawable.displayview);
            bookInfo.setBookName("2222" + i);
            bookInfo.setDate("2017.6.28");
            bookInfo.setNewPrice("8.00");
            bookInfo.setOldPrice("15.00");
            bookInfo.setQuality("9");
            bookInfo.setUserAddress("SSDUT");
            bookInfo.setSexImageId(R.mipmap.ic_launcher);
            bookInfo.setUserImageId(R.mipmap.ic_launcher);
            bookInfo.setUserName("banz");
            list.add(bookInfo);
        }
    }

}

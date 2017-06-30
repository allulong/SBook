package com.logn.sbook.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.logn.sbook.R;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

/**
 * Created by oureda on 2017/6/29.
 */

public class GuideActivity extends FragmentActivity{
    private IndicatorViewPager indicatorViewPager;
    private View centerView;
    private FixedIndicatorView indicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ViewPager viewPager= (ViewPager) findViewById(R.id.viewPager);
         indicator= (FixedIndicatorView) findViewById(R.id.indicator);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(Color.CYAN, Color.GRAY));
        indicatorViewPager=new IndicatorViewPager(indicator,viewPager);
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
    private IndicatorViewPager.IndicatorPagerAdapter adapter=new
            IndicatorViewPager.IndicatorViewPagerAdapter() {
                private LayoutInflater inflater;
                public int[] layout={
                        R.layout.activity_main,
                        R.layout.activity_mine
                };
                private String[] tabNames = {"首页","我"};
                private int[] tabIcons={
                        R.drawable.main,R.drawable.mine
                };
                @Override
                public int getCount() {
                    return layout.length;
                }

                @Override
                public View getViewForTab(int position, View convertView, ViewGroup container) {
                    if (convertView==null){
                        inflater=LayoutInflater.from(getApplicationContext());
                        convertView=inflater.inflate(R.layout.tab_guide
                        ,container,false);
                    }
                    TextView textView = (TextView) convertView;
                    textView.setText(tabNames[position]);
                    textView.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[position], 0, 0);
                    return textView;
                }

                @Override
                public View getViewForPage(int position, View convertView, ViewGroup container) {
                    int resourceId=layout[position];
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
                    if (convertView==null){
                        view=LayoutInflater.from(getApplicationContext()).inflate(
                                resourceId,container,false

                        );
                    }else {
                        view=convertView;
                    }
                    return view;

                }
            };
}

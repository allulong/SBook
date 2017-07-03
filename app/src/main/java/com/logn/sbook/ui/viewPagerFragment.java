package com.logn.sbook.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

//import com.logn.sbook.MainActivity;
import com.logn.sbook.R;
import com.logn.sbook.beans.BookInfo;
import com.logn.sbook.util.BookAdapter;
import com.logn.sbook.util.viewPagerAdapter;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Handler;
/**
 * Created by banz on 2017/7/1.
 */

public class viewPagerFragment extends LazyFragment{
    public static final String INTENT_STRING_TABNAME = "intent_String_tabName";
    public static final String KIND_OF_BOOK="intent_String_kindOfBook";
    private String tabName;
    private int position;
    private SearchView searchView;
    //    private ViewFlipper viewFlipper;
    private GridView gridView;
    //ViewPager
    private View viewpager1,viewpager2;
    private ViewPager viewPager;
    //创建list，保存获得的数据
    private List<BookInfo> bookList=new ArrayList<>();
    String []kindsOfBooks=new String[]{
            "计算机","小说","题库","考研","其他"
    } ;

    @Override
    protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_preview,container,false);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        tabName=getArguments().getString(INTENT_STRING_TABNAME);
        if (tabName=="首页") {
            setContentView(R.layout.activity_main);


            implSearchView();
            displayWithViewPager();
            kindsOfBooks();

            //实现recyclerview
            initBook();
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            BookAdapter bookAdapter = new BookAdapter(getApplicationContext(), bookList);
            recyclerView.setAdapter(bookAdapter);
        }else if (tabName=="我"){
            setContentView(R.layout.activity_mine);
        }

    }


    //从后台数据库获取Book数据-首页
    private void initBook(){
        //example for test
        for (int i=0;i<5;i++){
            BookInfo bookInfo=new BookInfo();
            bookInfo.setAuthor("banz"+i);
            bookInfo.setBookImageId(R.drawable.displayview);
            bookInfo.setBookName("2222"+i);
            bookInfo.setDate("2017.6.28");
            bookInfo.setNewPrice("8.00");
            bookInfo.setOldPrice("15.00");
            bookInfo.setQuality("9");
            bookInfo.setUserAddress("SSDUT");
            bookInfo.setSexImageId(R.mipmap.ic_launcher);
            bookInfo.setUserImageId(R.mipmap.ic_launcher);
            bookInfo.setUserName("banz");
            bookList.add(bookInfo);
        }
    }
    //搜索框-首页
    public void implSearchView(){
        searchView= (SearchView) findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    //Display自动播放图片
//    public void autoPlay(){
//        viewFlipper= (ViewFlipper) findViewById(R.id.displayView);
//        viewFlipper.setInAnimation(this,R.anim.slide_in_left);
//        viewFlipper.setOutAnimation(this,R.anim.slide_out_right);
//        viewFlipper.startFlipping();
//    }
    //实现ViewPager-首页
    public void displayWithViewPager(){
        viewPagerAdapter viewPagerAdapter = new viewPagerAdapter();
        viewPager= (ViewPager) findViewById(R.id.displayView);

        viewpager1=inflater.inflate(R.layout.viewpager1,null);
        viewpager2=inflater.inflate(R.layout.viewpager2,null);
        viewPagerAdapter.viewList=new ArrayList<View>();
        viewPagerAdapter.viewList.add(viewpager1);
        viewPagerAdapter.viewList.add(viewpager2);
        viewPager.setAdapter(viewPagerAdapter);
    }

    //点击非编辑框退出键盘
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if (isShouldHideInput(v, ev)) {
//
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (imm != null) {
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//            }
//            return super.dispatchTouchEvent(ev);
//        }
//        // 必不可少，否则所有的组件都不会有TouchEvent了
//        if (getWindow().superDispatchTouchEvent(ev)) {
//            return true;
//        }
//        return onTouchEvent(ev);
//    }
//    public  boolean isShouldHideInput(View v, MotionEvent event) {
//        if (v != null && (v instanceof EditText)) {
//            int[] leftTop = { 0, 0 };
//            //获取输入框当前的location位置
//            v.getLocationInWindow(leftTop);
//            int left = leftTop[0];
//            int top = leftTop[1];
//            int bottom = top + v.getHeight();
//            int right = left + v.getWidth();
//            if (event.getX() > left && event.getX() < right
//                    && event.getY() > top && event.getY() < bottom) {
//                // 点击的是输入框区域，保留点击EditText的事件
//                return false;
//            } else {
//                return true;
//            }
//        }
//        return false;
//    }
    //GridView-书的分类-首页
    public void kindsOfBooks(){
        gridView= (GridView) findViewById(R.id.gridView);
        int []imageIds=new int[]{
                R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher
                ,R.mipmap.ic_launcher,R.mipmap.ic_launcher
        };

        ArrayList<HashMap<String,Object>> IsImageItem=new
                ArrayList<HashMap<String, Object>>();
        for (int i=0;i<imageIds.length;i++){
            HashMap<String,Object>map=new HashMap<String,Object>();
            map.put("ItemImage",imageIds[i]);
            map.put("ItemText",kindsOfBooks[i]);
            IsImageItem.add(map);
        }

        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                IsImageItem,R.layout.item_gridview,
                new String[]{"ItemImage","ItemText"},
                new int[]{R.id.itemGridimage,R.id.itemGridText});
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(new viewPagerFragment.ItemClickListener());
    }

    //实现gridView的点击事件-首页
    class ItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent,
                                View view, int position, long id) {
//            Toast.makeText(getApplicationContext(),position+""
//                    ,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),kindsOfBooksDetail.class);
            intent.putExtra(KIND_OF_BOOK,kindsOfBooks[position]);
            startActivity(intent);
        }
    }
}

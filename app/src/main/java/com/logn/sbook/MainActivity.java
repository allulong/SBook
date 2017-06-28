package com.logn.sbook;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.SearchView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SearchView;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.logn.sbook.beans.BookInfo;
import com.logn.sbook.util.BookAdapter;
import com.logn.sbook.util.viewPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private SearchView searchView;
//    private ViewFlipper viewFlipper;
    private GridView gridView;
    //ViewPager
    private View viewpager1,viewpager2;
    private ViewPager viewPager;
    //创建list，保存获得的数据
    private List<BookInfo> bookList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        implSearchView();
        displayWithViewPager();
        kindsOfBooks();

        //实现recyclerview
        initBook();
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        BookAdapter bookAdapter=new BookAdapter(MainActivity.this,bookList);
        recyclerView.setAdapter(bookAdapter);
    }
    //从后台数据库获取Book数据
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
    //搜索框
    public void implSearchView(){
        searchView= (SearchView) findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("书名");

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
    //实现ViewPager
    public void displayWithViewPager(){
        viewPagerAdapter viewPagerAdapter = new viewPagerAdapter();
        viewPager= (ViewPager) findViewById(R.id.displayView);
        LayoutInflater inflater=getLayoutInflater();
        viewpager1=inflater.inflate(R.layout.viewpager1,null);
        viewpager2=inflater.inflate(R.layout.viewpager2,null);
        viewPagerAdapter.viewList=new ArrayList<View>();
        viewPagerAdapter.viewList.add(viewpager1);
        viewPagerAdapter.viewList.add(viewpager2);
        viewPager.setAdapter(viewPagerAdapter);
    }
    //点击非编辑框退出键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    //GridView-书的分类
    public void kindsOfBooks(){
        gridView= (GridView) findViewById(R.id.gridView);
        int []imageIds=new int[]{
                R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher
                ,R.mipmap.ic_launcher,R.mipmap.ic_launcher
        };
        String []kinds=new String[]{
                "计算机","小说","题库","考研","其他"
        } ;
        ArrayList<HashMap<String,Object>> IsImageItem=new
                ArrayList<HashMap<String, Object>>();
        for (int i=0;i<imageIds.length;i++){
            HashMap<String,Object>map=new HashMap<String,Object>();
            map.put("ItemImage",imageIds[i]);
            map.put("ItemText",kinds[i]);
            IsImageItem.add(map);
        }

        SimpleAdapter simpleAdapter=new SimpleAdapter(this,
                IsImageItem,R.layout.item_gridview,
                new String[]{"ItemImage","ItemText"},
                new int[]{R.id.itemGridimage,R.id.itemGridText});
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(new ItemClickListener());
    }
    //实现gridView的点击事件
    class ItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent,
                                View view, int position, long id) {
            Toast.makeText(MainActivity.this,position+""
                    ,Toast.LENGTH_SHORT).show();
        }
    }
}

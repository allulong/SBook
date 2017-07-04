package com.logn.sbook.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.logn.sbook.R;
import com.logn.sbook.beans.BookInfo;
import com.logn.sbook.util.BookAdapter;
import com.logn.titlebar.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class kindsOfBooksDetail extends AppCompatActivity {
    //创建list，保存获得的数据
    private List<BookInfo> bookList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinds_of_books_detail);
        Intent intent=getIntent();

        CharSequence kindOfBook = intent.getStringExtra(viewPagerFragment.KIND_OF_BOOK);

        TitleBar titleBar= (TitleBar) findViewById(R.id.kind_titlebar);
        titleBar.setTitle(kindOfBook);
        titleBar.setOnTitleClickListener(listener);
        //实现recyclerview
        initBook();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.kind_of_gridview_recycleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        BookAdapter bookAdapter = new BookAdapter(getApplicationContext(), bookList);
        recyclerView.setAdapter(bookAdapter);
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
            bookInfo.setISBN(000000);
            bookInfo.setPublisher("SSDUT");
            bookInfo.setUserContact("DalianSSDUT");
            bookList.add(bookInfo);
        }
    }
    TitleBar.OnTitleClickListener listener=new TitleBar.OnTitleClickListener() {
        @Override
        public void onLeftClick() {
            finish();
        }

        @Override
        public void onRightClick() {

        }

        @Override
        public void onTitleClick() {

        }
    };
}

package com.logn.sbook.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.logn.sbook.R;
import com.logn.sbook.beans.BookInfo;
import com.logn.titlebar.TitleBar;

public class BookDetailInfo extends AppCompatActivity {
    TitleBar titleBar;
    private ImageView detailBookImage;
    private TextView detailBookName;
    private TextView detailBookAuthor;
    private TextView detailBookPublisher;
    private TextView detailBookNewprice;
    private TextView detailBookOldprice;
    private TextView detailBookDate;
    private TextView detailBookQulity;
    private TextView detailBookNumber;
    private TextView detailUserName;
    private TextView detailUserAddress;
    private TextView detailUserContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail_info);
        initWdiget();

        titleBar.setOnTitleClickListener(listener);

        BookInfo bookInfo= (BookInfo) getIntent().getSerializableExtra("bookInfo");
        detailUserContact.setText(bookInfo.getUserContact());
        detailUserAddress.setText(bookInfo.getUserAddress());
        detailUserName.setText(bookInfo.getUserName());
        detailBookNumber.setText(bookInfo.getBookNumber());
        detailBookAuthor.setText(bookInfo.getAuthor());
        detailBookQulity.setText(bookInfo.getQuality());
        detailBookOldprice.setText(bookInfo.getOldPrice());
        detailBookDate.setText(bookInfo.getDate());
        detailBookImage.setImageResource(bookInfo.getBookImageId());
        detailBookNewprice.setText(bookInfo.getNewPrice());
        detailBookName.setText(bookInfo.getBookName());
        detailBookPublisher.setText(bookInfo.getPublisher());


    }

    public void initWdiget(){
        titleBar= (TitleBar) findViewById(R.id.titlebar_book_info);
        detailBookAuthor= (TextView) findViewById(R.id.detail_book_author);
        detailBookImage= (ImageView) findViewById(R.id.detail_book_image);
        detailBookName= (TextView) findViewById(R.id.detail_book_name);
        detailBookPublisher= (TextView) findViewById(R.id.detail_book_publisher);
        detailBookNewprice= (TextView) findViewById(R.id.detail_book_newprice);
        detailBookOldprice= (TextView) findViewById(R.id.detail_book_oldprice);
        detailBookDate= (TextView) findViewById(R.id.detail_book_date);
        detailBookQulity= (TextView) findViewById(R.id.detail_qulity_book);
        detailBookNumber= (TextView) findViewById(R.id.detail_book_number);
        detailUserName= (TextView) findViewById(R.id.detail_username);
        detailUserAddress= (TextView) findViewById(R.id.detail_useraddress);
        detailUserContact= (TextView) findViewById(R.id.detail_user_contact);
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

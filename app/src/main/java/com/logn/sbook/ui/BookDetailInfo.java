package com.logn.sbook.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.logn.sbook.R;
import com.logn.sbook.beans.BookInfo;
import com.logn.sbook.util.FileUtil;
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

    private LinearLayout llInfo;

    private String shareman;
    private String phone;
    private String remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail_info);
        initWdiget();

        titleBar.setOnTitleClickListener(listener);

        initValue();
    }

    private void initValue() {
        BookInfo bookInfo = (BookInfo) getIntent().getSerializableExtra("bookInfo");
        shareman = bookInfo.getUserName();
        phone = bookInfo.getPhone();
        remark = bookInfo.getUserContact();

        detailUserContact.setText(remark);
        detailUserAddress.setText(bookInfo.getUserAddress());
        detailUserName.setText(shareman);
        detailBookNumber.setText(bookInfo.getBookNumber());
        detailBookAuthor.setText(bookInfo.getAuthor());
        detailBookQulity.setText(bookInfo.getQuality());
        detailBookOldprice.setText(bookInfo.getOldPrice());
        detailBookDate.setText(bookInfo.getDate());
//        detailBookImage.setImageResource(bookInfo.getBookImageId());
        detailBookNewprice.setText(bookInfo.getNewPrice());
        detailBookName.setText(bookInfo.getBookName());
        detailBookPublisher.setText(bookInfo.getPublisher());

        Bitmap bitmap;
        if ((bitmap = FileUtil.getBitmap(bookInfo.getBookName())) != null) {
            Log.e("bitmap", "" + bitmap);
            detailBookImage.setImageBitmap(bitmap);
        } else {
            detailBookImage.setImageResource(bookInfo.getBookImageId());
        }

    }

    public void initWdiget() {
        titleBar = (TitleBar) findViewById(R.id.titlebar_book_info);
        detailBookAuthor = (TextView) findViewById(R.id.detail_book_author);
        detailBookImage = (ImageView) findViewById(R.id.detail_book_image);
        detailBookName = (TextView) findViewById(R.id.detail_book_name);
        detailBookPublisher = (TextView) findViewById(R.id.detail_book_publisher);
        detailBookNewprice = (TextView) findViewById(R.id.detail_book_newprice);
        detailBookOldprice = (TextView) findViewById(R.id.detail_book_oldprice);
        detailBookDate = (TextView) findViewById(R.id.detail_book_date);
        detailBookQulity = (TextView) findViewById(R.id.detail_qulity_book);
        detailBookNumber = (TextView) findViewById(R.id.detail_book_number);
        detailUserName = (TextView) findViewById(R.id.detail_username);
        detailUserAddress = (TextView) findViewById(R.id.detail_useraddress);
        detailUserContact = (TextView) findViewById(R.id.detail_user_contact);
        llInfo = (LinearLayout) findViewById(R.id.detail_info);

        llInfo.setOnClickListener(clickListener);
    }

    TitleBar.OnTitleClickListener listener = new TitleBar.OnTitleClickListener() {
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

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.detail_info) {
                Toast.makeText(BookDetailInfo.this, "联系方式", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("shareman", shareman);
                intent.putExtra("phone", phone);
                intent.putExtra("remark", remark);
                intent.setClass(BookDetailInfo.this, ContactInfoActivity.class);
                startActivity(intent);
            }
        }
    };
}

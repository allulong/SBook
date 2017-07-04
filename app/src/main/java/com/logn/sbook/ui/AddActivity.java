package com.logn.sbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.logn.sbook.R;
import com.logn.titlebar.TitleBar;

/**
 * Created by Vivian on 2017/7/1.
 */

public class AddActivity extends AppCompatActivity {
    //    private ImageButton imageButton_return;
    private EditText editText_isbn;
    private ImageButton imageButton_isbn_search;
    private ImageButton imageButton_isbn_qrcode;
    private ImageButton imageButton_book_look;
    private EditText editText_book_name;
    private EditText editText_book_author;
    private EditText editText_book_publisher;
    private EditText editText_book_oldprice;
    private EditText editText_book_newprice;
    private EditText editText_book_number;
    private EditText editText_remark;
    private Button choose_kind;
    private Button choose_qulity;
    TitleBar add_return;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initWidget();
        add_return.setOnTitleClickListener(listener);
    }

    public void initWidget() {
//        imageButton_return = (ImageButton) findViewById(R.id.button_return);
        add_return = (TitleBar) findViewById(R.id.add_titlebar);
        editText_isbn = (EditText) findViewById(R.id.isbn_edit);
        imageButton_isbn_search = (ImageButton) findViewById(R.id.isbn_search);
        imageButton_isbn_qrcode = (ImageButton) findViewById(R.id.isbn_qrcode);
        imageButton_book_look = (ImageButton) findViewById(R.id.add_book_image);
        editText_book_name = (EditText) findViewById(R.id.add_book_name);
        editText_book_author = (EditText) findViewById(R.id.add_book_author);
        editText_book_publisher = (EditText) findViewById(R.id.add_book_publisher);
        editText_book_oldprice = (EditText) findViewById(R.id.add_book_oldprice);
        editText_book_newprice = (EditText) findViewById(R.id.add_book_newprice);
        editText_book_number = (EditText) findViewById(R.id.add_book_number);
        editText_remark = (EditText) findViewById(R.id.add_book_publisher);
        choose_kind = (Button) findViewById(R.id.add_book_kind);
        choose_qulity = (Button) findViewById(R.id.add_book_qulity);
//        imageButton_return.setOnClickListener(this);
    }

    //    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.button_return:
//                Intent intent=new Intent(AddActivity.this,GuideActivity.class);
//                startActivity(intent);
//                finish();
//                break;
//        }
//    }
    TitleBar.OnTitleClickListener listener = new TitleBar.OnTitleClickListener() {
        @Override
        public void onLeftClick() {
            Intent intent = new Intent(AddActivity.this, GuideActivity.class);
            startActivity(intent);
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

package com.logn.sbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.logn.sbook.R;

/**
 * Created by long on 2017/7/18.
 */

public class ContactInfoActivity extends FragmentActivity {
    private TextView shareMan;
    private TextView phone;
    private TextView remark;

    private String strShareman;
    private String strPhone;
    private String strRemark;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        initView();
    }

    private void initView() {
        shareMan = (TextView) findViewById(R.id.contact_info_share_man);
        phone = (TextView) findViewById(R.id.contact_info_phone);
        remark = (TextView) findViewById(R.id.contact_info_remark);

        Intent intent = getIntent();

        shareMan.setText(intent.getStringExtra("shareman"));
        phone.setText(intent.getStringExtra("phone"));
        remark.setText(intent.getStringExtra("remark"));
    }
}

package com.logn.sbook.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.logn.sbook.R;
import com.logn.sbook.util.SpValue;
import com.logn.titlebar.TitleBar;

/**
 * Created by long on 2017/7/18.
 */

public class InfoSettingActivity extends FragmentActivity {

    private TitleBar titleBar;

    private EditText address, qq, email;

    private Button btnOK;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acrivity_info_setting);

        initView();
    }

    private void initView() {
        titleBar = (TitleBar) findViewById(R.id.info_title);

        titleBar.setOnTitleClickListener(titleClickListener);

        address = (EditText) findViewById(R.id.et_address);
        qq = (EditText) findViewById(R.id.et_qq);
        email = (EditText) findViewById(R.id.et_email);

        btnOK = (Button) findViewById(R.id.btn_ok);


    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_ok:
                    String addressStr = address.getText().toString();
                    String qqStr = qq.getText().toString();
                    String emailStr = email.getText().toString();
                    if (addressStr.isEmpty()) {
                        Toast.makeText(InfoSettingActivity.this, "地址必填", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    Toast.makeText(InfoSettingActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private TitleBar.OnTitleClickListener titleClickListener = new TitleBar.OnTitleClickListener() {
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

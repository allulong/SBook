package com.logn.sbook.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.logn.sbook.R;

public class SettingActivity extends AppCompatActivity {

    private LinearLayout headImage, adderess, sex, changePassword, contact;
    private TextView version;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_settings);

        initView();
    }

    private void initView() {
        headImage = (LinearLayout) findViewById(R.id.setting_head_image);
        adderess = (LinearLayout) findViewById(R.id.setting_address);
        sex = (LinearLayout) findViewById(R.id.setting_sex);
        changePassword = (LinearLayout) findViewById(R.id.setting_change_password);
        contact = (LinearLayout) findViewById(R.id.setting_contact);
        version = (TextView) findViewById(R.id.setting_tv_version);
        btnLogout = (Button) findViewById(R.id.setting_btn_logout);

        headImage.setOnClickListener(listener);
        adderess.setOnClickListener(listener);
        sex.setOnClickListener(listener);
        changePassword.setOnClickListener(listener);
        contact.setOnClickListener(listener);
        version.setOnClickListener(listener);
        btnLogout.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setting_head_image:
                    break;
                case R.id.setting_address:
                    break;
                case R.id.setting_sex:
                    break;
                case R.id.setting_change_password:
                    break;
                case R.id.setting_contact:
                    break;
                case R.id.setting_tv_version:
                    break;
                case R.id.setting_btn_logout:
                    logout();
                    break;
            }
        }
    };

    private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences("sp_login", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        finish();
    }
}

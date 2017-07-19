package com.logn.sbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.logn.sbook.R;
import com.logn.sbook.util.SpUtils;
import com.logn.sbook.util.SpValue;

public class SettingActivity extends AppCompatActivity {

    private LinearLayout headImage, detailInfo, changeInfo, changePassword;
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
        detailInfo = (LinearLayout) findViewById(R.id.setting_detail_info);
        changeInfo = (LinearLayout) findViewById(R.id.setting_change_info);
        changePassword = (LinearLayout) findViewById(R.id.setting_change_password);
        version = (TextView) findViewById(R.id.setting_tv_version);
        btnLogout = (Button) findViewById(R.id.setting_btn_logout);

        headImage.setOnClickListener(listener);
        detailInfo.setOnClickListener(listener);
        changeInfo.setOnClickListener(listener);
        changePassword.setOnClickListener(listener);
        version.setOnClickListener(listener);
        btnLogout.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.setting_head_image:
                    break;
                case R.id.setting_detail_info:
                    intent.putExtra(SpValue.infoType, SpValue.address);
                    intent.setClass(SettingActivity.this, PersonalInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.setting_change_info:
                    intent.putExtra(SpValue.infoType, SpValue.key_sex);
                    intent.setClass(SettingActivity.this, InfoSettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.setting_change_password:
                    Toast.makeText(SettingActivity.this, "修改密码", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.setting_tv_version:
                    Toast.makeText(SettingActivity.this, "已是最新版本", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.setting_btn_logout:
                    logout();
                    break;
            }
        }
    };

    private void logout() {
        SpUtils.clear(SettingActivity.this);
        finish();
    }
}

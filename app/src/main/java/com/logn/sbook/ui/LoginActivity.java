package com.logn.sbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.logn.sbook.R;
import com.logn.titlebar.TitleBar;

/**
 * Created by long on 2017/7/12.
 */

public class LoginActivity extends FragmentActivity {

    private TitleBar titleBar;

    private EditText etUsername;
    private EditText etPassword;
    private Button btn2register;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        titleBar = (TitleBar) findViewById(R.id.title_bar_login);
        titleBar.setOnTitleClickListener(titleClickListener);

        etUsername = (EditText) findViewById(R.id.et_username_login);
        etPassword = (EditText) findViewById(R.id.et_password_login);
        btn2register = (Button) findViewById(R.id.btn_2_register);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }

    private View.OnClickListener btnListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_2_register:
                    //跳转到注册界面
                    Intent intent = new Intent(LoginActivity.this, PhoneCheckActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_login:
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();
                    if (username.isEmpty() || password.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    login(username, password);
                    break;
            }
        }
    };

    private void login(String username, String password) {

    }


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

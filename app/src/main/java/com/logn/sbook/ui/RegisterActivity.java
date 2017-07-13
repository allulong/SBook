package com.logn.sbook.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.logn.sbook.R;
import com.logn.sbook.util.RegisterRunnable;
import com.logn.titlebar.TitleBar;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by long on 2017/7/3.
 */

public class RegisterActivity extends FragmentActivity {

    private TitleBar titleBar;

    private EditText etUserName, etPassword, etPasswordCheck;

    private Button btnBack, btnFinish;

    private String phone;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);

            if (msg.what == 100) {
                //获得信息
                String json = (String) msg.obj;
                JSONObject jsonObject = new JSONObject();
                int status = -100;
                try {
                    status = jsonObject.getInt("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {//注册成功
                    //存储数据，结束activity
                    SharedPreferences.Editor editor = getSharedPreferences("sp_login", MODE_PRIVATE).edit();
                    editor.putString("username", etUserName.getText().toString());
                    editor.putString("password", etPassword.getText().toString());
                    editor.putString("login_time", System.currentTimeMillis() + "");
                    editor.apply();


                    Intent toGuideActivity = new Intent();
                    toGuideActivity.setClass(RegisterActivity.this, GuideActivity.class);
                    toGuideActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    toGuideActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toGuideActivity);
                } else if (status == -1) {
                    //注册失败
                    Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                }

            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initValue();
    }

    private void initValue() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
    }

    private void initView() {
        titleBar = (TitleBar) findViewById(R.id.title_bar_register);
        titleBar.setOnTitleClickListener(listener);

        etUserName = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etPasswordCheck = (EditText) findViewById(R.id.et_password_check);

        btnBack = (Button) findViewById(R.id.btn_back_register);
        btnFinish = (Button) findViewById(R.id.btn_finish);

        btnBack.setOnClickListener(btnListener);
        btnFinish.setOnClickListener(btnListener);
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_back_register:
                    finish();
                    break;
                case R.id.btn_finish:
                    String username = etUserName.getText().toString();
                    String password = etPassword.getText().toString();
                    String passwordCheck = etPasswordCheck.getText().toString();
                    if (username.isEmpty() || password.isEmpty() || passwordCheck.isEmpty()) {
                        toast("请填写完整信息");
                    } else {
                        if (username.length() < 4) {
                            toast("用户名长度不符合要求");
                        } else if (password.length() < 6) {
                            toast("密码长度太短");
                        } else if (!password.equals(passwordCheck)) {
                            toast("两次输入放入密码不一致");
                        } else {//验证成功，向后台申请注册
                            startRegister(phone, username, password);
                        }
                    }
                    break;
            }
        }
    };

    private void startRegister(String phone, String username, String password) {
        toast("开始注册");
        RegisterRunnable runnable = new RegisterRunnable(phone, username, password);
        runnable.setHandler(handler);
        new Thread(runnable).start();
    }

    private TitleBar.OnTitleClickListener listener = new TitleBar.OnTitleClickListener() {
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


    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this, "" + str, Toast.LENGTH_SHORT).show();

            }
        });
    }
}

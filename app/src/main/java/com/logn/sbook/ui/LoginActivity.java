package com.logn.sbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.logn.sbook.R;
import com.logn.sbook.util.LoginRunnable;
import com.logn.sbook.util.SpUtils;
import com.logn.sbook.util.SpValue;
import com.logn.sbook.util.StatusData;
import com.logn.titlebar.TitleBar;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by long on 2017/7/12.
 */

public class LoginActivity extends FragmentActivity {

    private TitleBar titleBar;

    private EditText etUsername;
    private EditText etPassword;
    private Button btn2register;
    private Button btnLogin;
    private TextView tvForgetPassword;

    private String username;
    private String password;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                String json = msg.obj.toString();
                Log.e("login", json);
                checkJson(json);
            }
        }
    };

    private void checkJson(String json) {
        JSONObject jsonObject = null;
        int status = -100;
        String msg = null;
//        try {
//            jsonObject = new JSONObject(json);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        try {
//            status = jsonObject.getInt("status");
//            Log.e("status", "" + status);
//            msg = jsonObject.getString("msg");
//            Log.e("msg", "" + msg);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.e("msg", "error");
//        }
        Gson gson = new GsonBuilder().create();
        StatusData data = gson.fromJson(json, StatusData.class);
        status = data.getStatus();

        switch (status) {
            case 200:
                Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                SpUtils.put(LoginActivity.this, SpValue.key_username, username);
                SpUtils.put(LoginActivity.this, SpValue.key_password, password);
                SpUtils.put(LoginActivity.this, SpValue.key_login_time, System.currentTimeMillis() + "");
                finish();
                break;
            case 3:
                Toast.makeText(this, "账号不存在", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
        }
    }


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
        tvForgetPassword = (TextView) findViewById(R.id.tv_forget_password);

        btnLogin.setOnClickListener(btnListner);
        btn2register.setOnClickListener(btnListner);
        tvForgetPassword.setOnClickListener(btnListner);
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
                case R.id.tv_forget_password:
//                    Toast.makeText(LoginActivity.this, "暂未开通233", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void login(String username, String password) {
        this.username = username;
        this.password = password;
        LoginRunnable runnable = new LoginRunnable(username, password);
        runnable.setHandler(handler);
        new Thread(runnable).start();
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

package com.logn.sbook.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.logn.sbook.R;
import com.logn.sbook.util.EventInt;
import com.logn.titlebar.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by long on 2017/7/3.
 */

public class PhoneCheckActivity extends FragmentActivity {

    private TitleBar titleBar;

    private EditText etPhone;
    private EditText etCheckCode;

    private Button getCode;
    private Button back;
    private Button next;

    private EventHandler eventHandler;

    private String COUNTRY_CODE_CHINA = "86";

    private String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_check);

        EventBus.getDefault().register(this);

        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        toast("验证成功");
                        //跳转到第二个界面
                        changeToRegister();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        toast("获取验证码成功");
                    } else {
                        toast("获取到其他信息");
                    }
                } else {//操作失败
                    toast("验证失败");
                }
            }
        };

        SMSSDK.registerEventHandler(eventHandler);

        initView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        etPhone = (EditText) findViewById(R.id.et_phone);
        etCheckCode = (EditText) findViewById(R.id.et_check_code);

        getCode = (Button) findViewById(R.id.btn_send_check_msg);
        back = (Button) findViewById(R.id.btn_back);
        next = (Button) findViewById(R.id.btn_next_step);

        getCode.setOnClickListener(btnListener);
        back.setOnClickListener(btnListener);
        next.setOnClickListener(btnListener);

        titleBar = (TitleBar) findViewById(R.id.title_bar_phone_check);
        titleBar.setOnTitleClickListener(titleClickListener);
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

    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_send_check_msg:
                    phone = etPhone.getText().toString();
                    if (phone != null && !phone.isEmpty()) {
                        if (!checkPhone(phone)) {
                            Toast.makeText(PhoneCheckActivity.this, "手机号有误", Toast.LENGTH_SHORT).show();
                        } else {
                            //先检查一下是否已经注册了？
                            if (!checkPhoneForRegister(phone)) {//还未注册才可以注册
                                alterWarning(phone);
                            } else {
                                Toast.makeText(PhoneCheckActivity.this, "此号码已经注册", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(PhoneCheckActivity.this, "号码不能为空", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.btn_next_step:
                    //检查验证码
                    checkCode();
                    break;
            }
        }
    };

    private boolean checkPhoneForRegister(String phoneText) {
        return false;
    }

    private void changeToRegister() {
        if (phone == null || phone.isEmpty()) {
            return;
        }
        Intent intent = new Intent(PhoneCheckActivity.this, RegisterActivity.class);
        intent.putExtra("phone", "" + phone);
        startActivity(intent);
        PhoneCheckActivity.this.finish();
    }

    private void alterWarning(final String phone) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("即将将验证短信发送到手机:" + phone)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //发送验证短信
                        SMSSDK.getVerificationCode(COUNTRY_CODE_CHINA, phone);
                        getCode.setEnabled(false);
                        //开始倒计时
                        Toast.makeText(PhoneCheckActivity.this, "已发送", Toast.LENGTH_SHORT).show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final long nowTime = System.currentTimeMillis();
                                final Timer timer = new Timer();
                                final int seconds = 60;
                                TimerTask timerTask = new TimerTask() {
                                    @Override
                                    public void run() {
                                        long nextTime = System.currentTimeMillis();
                                        int lastTime = seconds - (int) ((nextTime - nowTime) / 1000);
                                        EventBus.getDefault().post(new EventInt(lastTime));
                                        if (lastTime < -1) {//为了使按钮能够在显示0后恢复为可使用的状态
                                            timer.cancel();
                                        }
                                    }
                                };
                                timer.schedule(timerTask, 0, 1000);
                            }
                        }).start();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private boolean checkPhone(String phone) {
        //正则
        String REGEX_MOBILE_SIMPLE = "[1][358]\\d{9}";
        //进行匹配
        Pattern pattern = Pattern.compile(REGEX_MOBILE_SIMPLE);
        Matcher matcher = pattern.matcher(phone);
        if (matcher.find()) {//匹配
            return true;
        }
        return false;
    }

    /**
     * 检查验证码
     *
     * @return
     */
    private void checkCode() {
        String code = etCheckCode.getText().toString();
        if (code != null && !code.isEmpty()) {
            SMSSDK.submitVerificationCode(COUNTRY_CODE_CHINA, phone, code);
        } else {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
        }
    }

    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PhoneCheckActivity.this, "" + str, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLastTime(EventInt eventInt) {
        int lastTime = eventInt.getTime();
        if (lastTime >= 0) {
            getCode.setText("重新发送(" + lastTime + ")");
        } else {
            getCode.setEnabled(true);
            getCode.setText("重新发送");
        }
    }
}

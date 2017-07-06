package com.logn.sbook.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.logn.sbook.R;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by long on 2017/7/3.
 */

public class PhoneCheckActivity extends FragmentActivity {

    private EditText phone;
    private EditText checkCode;
    private EditText password;

    private Button sendPhone;
    private Button back;
    private Button next;

    private EventHandler eventHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_check);

        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    String msg = throwable.getMessage();
                    Toast.makeText(PhoneCheckActivity.this, msg, Toast.LENGTH_SHORT).show();
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {

                    }
                }
            }
        };

        SMSSDK.registerEventHandler(eventHandler);

        initView();

    }

    private void initView() {
        phone = (EditText) findViewById(R.id.et_phone);
        checkCode = (EditText) findViewById(R.id.et_check_code);
        password = (EditText) findViewById(R.id.et_password);

        sendPhone = (Button) findViewById(R.id.btn_send_check_msg);
        back = (Button) findViewById(R.id.btn_back);
        next = (Button) findViewById(R.id.btn_next_step);

        sendPhone.setOnClickListener(btnListener);
        back.setOnClickListener(btnListener);
        next.setOnClickListener(btnListener);
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_send_check_msg:
                    String phoneText = phone.getText().toString();
                    if (phoneText != null && !phoneText.isEmpty()) {
                        //发送号码进行验证
                        SMSSDK.getVerificationCode("86", phoneText);
                        sendPhone.setClickable(false);
                        //开线程计时

                    } else {
                        Toast.makeText(PhoneCheckActivity.this, "号码不能为空", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_back:
                    break;
                case R.id.btn_next_step:
                    break;
            }
        }
    };
}

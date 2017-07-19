package com.logn.sbook.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.logn.sbook.R;
import com.logn.sbook.beans.BookInfo;
import com.logn.sbook.beans.StatusUserBean;
import com.logn.sbook.beans.User;
import com.logn.sbook.util.BaseRunnable;
import com.logn.sbook.util.GetUserRunnable;
import com.logn.sbook.util.SpUtils;
import com.logn.sbook.util.SpValue;

import java.net.URLDecoder;
import java.util.List;

/**
 * Created by long on 2017/7/18.
 */

public class PersonalInfoActivity extends FragmentActivity {

    private TextView username, phone, address, sex;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == BaseRunnable.RESPONSE_CODE) {
                String jsonData = msg.obj.toString();
                jsonData = URLDecoder.decode(jsonData);
                Log.e("jsonP-", jsonData);
                Gson gson = new Gson();
                StatusUserBean bean = gson.fromJson(jsonData, StatusUserBean.class);
                User user = bean.getUser();
                username.setText(user.getUsername());
                phone.setText(user.getPhone());
                address.setText(user.getAddress());
                sex.setText(user.getSex().endsWith("M") ? "男生" : "女生");
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_personal_info);

        initView();
        getValue();
    }

    private void getValue() {
        String url = "http://c1y7502888.iok.la:23110/userinfo";
        String username = SpUtils.get(this, SpValue.key_username, "long");
        Log.e("adsj;kal", username);
        GetUserRunnable getUserRunnable = new GetUserRunnable(handler, url, username);
        getUserRunnable.setMethod("GET");
        new Thread(getUserRunnable).start();
    }

    private void initView() {
        username = (TextView) findViewById(R.id.person_info_username);
        phone = (TextView) findViewById(R.id.person_info_phone);
        address = (TextView) findViewById(R.id.person_info_address);
        sex = (TextView) findViewById(R.id.person_info_sex);
    }
}

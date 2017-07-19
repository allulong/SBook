package com.logn.sbook.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by long on 2017/7/7.
 */

public class RegisterRunnable implements Runnable {

    private String phone;
    private String username;
    private String password;

    private int msgCode = 100;

    private String app_id = "cmkajd8733nb4hd3092jn";

    private String urlStr;

    private Handler handler;

    public RegisterRunnable(String phone, String username, String password) {
        this.phone = phone;
        this.username = username;
        this.password = password;
        urlStr = "http://c1y7502888.iok.la:23110/register?" +
                "app_id=" + app_id +
                "&" +
                "phone=" + phone +
                "&" +
                "username=" + username +
                "&" +
                "password=" + password;
        Log.e("RegisterRunnable", "start");
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setURL(String url) {
        this.urlStr = url;
    }

    @Override
    public void run() {
        if (urlStr == null) {
            return;
        }
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            int code = connection.getResponseCode();
            InputStream is = connection.getInputStream();
            String json = HttpUtils.getStrFromIS(is);
            Message msg = new Message();
            if (handler != null) {
                msg.obj = json;
                msg.what = msgCode;
                handler.sendMessage(msg);
            }
            Log.e(code + "获得了JSON：", json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

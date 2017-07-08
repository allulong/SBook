package com.logn.sbook.util;

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

    String phone;
    String username;
    String password;

    int msgCode = 100;

    String app_id = "cmkajd8733nb4hd3092jn";

    private String urlStr;

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

    public void setURL(String url) {
        this.urlStr = url;
    }

    @Override
    public void run() {
        if (urlStr == null) {
            Log.e("Runnable", "urlStr is empty.");
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
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            int code = connection.getResponseCode();
            if (true) {
                InputStream is = connection.getInputStream();
                String json = HttpUtils.getStrFromIS(is);
                Message msg = new Message();
//                msg.obj = json;
//                msg.what = 100;
                Log.e(code + "woc获得了JSON：", json);
            } else {
                Log.e("woc", "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

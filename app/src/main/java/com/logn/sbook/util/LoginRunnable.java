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
 * Created by long on 2017/7/12.
 */

public class LoginRunnable implements Runnable {

    private String username;
    private String password;

    private int msgCode = 100;

    private String app_id = "cmkajd8733nb4hd3092jn";

    private String urlStr;


    private Handler handler;

    public LoginRunnable(String username, String password) {
        this.username = username;
        this.password = password;
        urlStr = "http://c1y7502888.iok.la:23110/login?" +
                "app_id=" + app_id +
                "&" +
                "username=" + username +
                "&" +
                "password=" + password;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
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
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            int code = connection.getResponseCode();
            if (code == 200) {
                InputStream is = connection.getInputStream();
                String json = HttpUtils.getStrFromIS(is);
                Message msg = new Message();
                if (handler != null) {
                    msg.obj = json;
                    msg.what = msgCode;
                    handler.sendMessage(msg);
                }
            } else {
                Log.e("...", "nothing");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

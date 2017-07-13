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
 * Created by Vivian on 2017/7/13.
 */

public class GetBookDataRunnable implements Runnable {

    private Handler handler;

    private String app_id = "cmkajd8733nb4hd3092jn";
    private String urlStr;

    public GetBookDataRunnable() {
        urlStr = "http://c1y7502888.iok.la:23110/getbooksinfo?" +
                "app_id=" + app_id;
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
            if (code == 200) {
                InputStream is = connection.getInputStream();
                String json = HttpUtils.getStrFromIS(is);
                Message msg = new Message();
                if (handler != null) {
                    msg.obj = json;
                    msg.what = 100;
                    handler.sendMessage(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (handler != null) {
                Message msg = new Message();
                msg.what = 101;
                handler.sendMessage(msg);
            }
        }
    }
}

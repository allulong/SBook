package com.logn.sbook.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by long on 2017/7/17.
 */

public abstract class BaseRunnable implements Runnable {
    private Handler handler;
    private String url;
    private String method = "POST";

    public static final int RESPONSE_CODE = 100;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public BaseRunnable(Handler handler, String baseUrl) {
        this.handler = handler;
        this.url = baseUrl;
    }


    public abstract Map<String, String> setMap();

    @Override
    public void run() {
        try {
            sendPostRequest(setMap(), url, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送POST请求
     *
     * @param param  请求参数
     * @param urlStr 请求路径
     * @return
     * @throws Exception
     */
    private boolean sendPostRequest(Map<String, String> param, String urlStr, String encoding) throws Exception {
        // TODO Auto-generated method stub
        StringBuffer sb = new StringBuffer(urlStr);
        if (!urlStr.equals("") & !param.isEmpty()) {
            sb.append("?");
            sb.append("app_id=cmkajd8733nb4hd3092jn&");
            for (Map.Entry<String, String> entry : param.entrySet()) {
                sb.append(entry.getKey() + "=");
                sb.append(URLEncoder.encode(entry.getValue(), encoding));
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);//删除字符串最后 一个字符“&”
        }
        urlStr = sb.toString();

        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(5000);
            int code = connection.getResponseCode();
            InputStream is = connection.getInputStream();
            String json = HttpUtils.getStrFromIS(is);
            Message msg = new Message();
            if (handler != null) {
                msg.obj = json;
                msg.what = RESPONSE_CODE;
                handler.sendMessage(msg);
            }
            Log.e(code + "base-获得了JSON：", json);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

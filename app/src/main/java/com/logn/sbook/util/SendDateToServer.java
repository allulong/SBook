package com.logn.sbook.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Vivian on 2017/7/13.
 */

public class SendDateToServer {
    private static String url = "http://c1y7502888.iok.la:23110/bookinfo";
    public static final int SEND_SUCCESS = 0x123;
    public static final int SEND_FAIL = 0x124;
    private Handler handler;

    public SendDateToServer(Handler handler) {
        // TODO Auto-generated constructor stub
        this.handler = handler;
    }

    /**
     * 通过POST方式向服务器发送数据
     */
    public void SendDataToServer(String userName, String ISBN, String imageUrl, String bookName, String author
            , String publisher, String oldPrice, String newPrice, String bookNumber, String kind
            , String qulity, String remark) {
        // TODO Auto-generated method stub
        final Map<String, String> map = new HashMap<String, String>();
        map.put("userName", userName);
        map.put("ISBN", String.valueOf(ISBN));
        map.put("imageUrl", imageUrl);
        map.put("bookName", bookName);
        map.put("author", author);
        map.put("publisher", publisher);
        map.put("oldPrice", oldPrice);
        map.put("newPrice", newPrice);
        map.put("bookNumber", String.valueOf(bookNumber));
        map.put("kind", kind);
        map.put("qulity", qulity);
        map.put("remark", remark);

        String time = TimeUtils.getTimeWithLong(System.currentTimeMillis());
        map.put("create_time", time);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    if (sendPostRequest(map, url, "utf-8")) {
                        handler.sendEmptyMessage(SEND_SUCCESS);//通知主线程数据发送成功
                    } else {
                        //将数据发送给服务器失败
                        handler.sendEmptyMessage(SEND_FAIL);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
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
        //http://10.219.61.117:8080/ServerForPOSTMethod/ServletForPOSTMethod?name=aa&pwd=124
        StringBuffer sb = new StringBuffer(urlStr);
        if (!urlStr.equals("") & !param.isEmpty()) {
            sb.append("?");
            sb.append("app_id=cmkajd8733nb4hd3092jn&");
            for (Map.Entry<String, String> entry : param.entrySet()) {
                sb.append(entry.getKey() + "=");
                sb.append(URLEncoder.encode(entry.getValue(), encoding));
//                sb.append(entry.getValue());
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);//删除字符串最后 一个字符“&”
        }
        urlStr = sb.toString();
//        urlStr = SpaceUtils.replaceSpace(urlStr);
        Log.e("mapdata:", urlStr);
//        byte[] data = sb.toString().getBytes();
//        HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
//        conn.setConnectTimeout(5000);
//        conn.setRequestMethod("POST");//设置请求方式为POST
//        conn.setDoOutput(true);//允许对外传输数据
//        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设置窗体数据编码为名称/值对
//        conn.setRequestProperty("Content-Length", data.length + "");
//        OutputStream outputStream = conn.getOutputStream();//打开服务器的输入流
//        outputStream.write(data);//将数据写入到服务器的输出流
//        outputStream.flush();
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.e("url_book", url.toString());
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            int code = connection.getResponseCode();
            InputStream is = connection.getInputStream();
            String json = HttpUtils.getStrFromIS(is);
            Message msg = new Message();
            if (handler != null) {
                msg.obj = json;
                msg.what = 100;
                handler.sendMessage(msg);
            }
            Log.e(code + "发布-获得了JSON：", json);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

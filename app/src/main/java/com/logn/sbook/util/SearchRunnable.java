package com.logn.sbook.util;

import android.os.Handler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by long on 2017/7/17.
 */

public class SearchRunnable extends BaseRunnable {
    private String type;
    private String username;

    public SearchRunnable(Handler handler, String baseUrl, String type) {
        super(handler, baseUrl);
        this.type = type;
    }

    public SearchRunnable(Handler handler, String baseUrl, String type, String username) {
        this(handler, baseUrl, type);
        this.username = username;
    }

    @Override
    public Map<String, String> setMap() {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        if (type.equals("出售")) {
            map.put("username", username);
        }
        return map;
    }


}

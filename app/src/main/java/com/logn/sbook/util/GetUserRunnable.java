package com.logn.sbook.util;

import android.os.Handler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by long on 2017/7/18.
 */

public class GetUserRunnable extends BaseRunnable {
    private String username;

    public GetUserRunnable(Handler handler, String baseUrl, String username) {
        super(handler, baseUrl);
        this.username = username;
    }

    @Override
    public Map<String, String> setMap() {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        return map;
    }
}

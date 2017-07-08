package com.logn.sbook.util;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by long on 2017/7/6.
 */

public class EventInt {
    private int time;

    public EventInt(int time) {
        setTime(time);
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

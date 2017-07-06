package com.logn.sbook.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.logn.titlebar.TitleBar;

/**
 * Created by long on 2017/7/3.
 */

public class RegisterActivity extends FragmentActivity {

    TitleBar titleBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        titleBar.setOnTitleClickListener(listener);
    }

    TitleBar.OnTitleClickListener listener = new TitleBar.OnTitleClickListener() {
        @Override
        public void onLeftClick() {

        }

        @Override
        public void onRightClick() {

        }

        @Override
        public void onTitleClick() {

        }
    };
}

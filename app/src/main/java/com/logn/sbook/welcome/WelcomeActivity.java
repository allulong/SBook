package com.logn.sbook.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

//import com.logn.sbook.MainActivity;
import com.logn.sbook.R;
import com.logn.sbook.ui.GuideActivity;
import com.logn.sbook.ui.PhoneCheckActivity;

/**
 * Created by long on 2017/6/27.
 */

public class WelcomeActivity extends FragmentActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initView();
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.welcome_img);

        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}

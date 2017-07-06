package com.logn.sbook.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

import com.logn.sbook.R;

/**
 * Created by oureda on 2017/7/3.
 */

public class RoundView extends View {
    private BitmapShader bitmapShader=null;
    private Bitmap bitmap = null;
    private ShapeDrawable shapeDrawable = null;
    private int BitmapWidth = 0;
    private int BitmapHeight = 0;

    public RoundView(Context context) {
        super(context);
        bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.displayview)).getBitmap();
        BitmapWidth = bitmap.getWidth();
        BitmapHeight = bitmap.getHeight();
        //构造渲染器BitmapShader
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.REPEAT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        shapeDrawable = new ShapeDrawable(new OvalShape());
//得到画笔并设置渲染器
        Paint paint = shapeDrawable.getPaint();
        paint.setShader(bitmapShader);
//设置显示区域
        shapeDrawable.setBounds(20,20,100,100);
        shapeDrawable.draw(canvas);
    }
}

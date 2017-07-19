package com.logn.sbook.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * Created by long on 2017/7/18.
 */

public class FileUtil {
    public static String getSDPath1() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = new File("/sdcard");//获取根目录
        }
        if (!sdDir.exists()) {
            sdDir.mkdirs();
        }
        return sdDir.toString();
    }

    public static String getRootPath() {
        String path = Environment.getExternalStorageDirectory().getPath() + "/sbook/";
        return path;
    }

    public static boolean exist(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static void createDir(String name) {
        String path = Environment.getExternalStorageDirectory().getPath() + "/" + name;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static Bitmap getBitmap(String bookName) {
        Bitmap bitmap = null;
        String imageName = FileUtil.getRootPath() + bookName + ".jpg";
        File file = new File(imageName);
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(imageName);
        }
        return bitmap;
    }
}

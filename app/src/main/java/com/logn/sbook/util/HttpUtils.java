package com.logn.sbook.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by long on 2017/7/8.
 */

public class HttpUtils {
    public static String getStrFromIS(InputStream is) {
        byte[] result = null;
        try {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int len;
            while ((len = is.read(buffer)) != -1) {
                bao.write(buffer, 0, len);
            }
            is.close();
            bao.close();
            result = bao.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(result);
    }
}

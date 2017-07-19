package com.logn.sbook.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by long on 2017/7/18.
 */

public class ImageRunnable implements Runnable {

    private List<String> urls;
    private List<String> names;

    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ImageRunnable(List<String> urls, List<String> names) {
        this.urls = urls;
        this.names = names;
    }

    @Override
    public void run() {
        Log.e("image_r_start", urls + ":" + names);
        for (int i = 0; i < urls.size(); i++) {
            downloadPicture(urls.get(i), names.get(i));
        }
        if (handler != null) {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    private void downloadPicture(String urlString, String name) {
        URL url = null;

        try {
            url = new URL(urlString);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            String imageName = FileUtil.getRootPath() + name + ".jpg";
            File file = new File(imageName);
            Log.e("file-path", "" + file.getPath());
            if (file.exists()) {
                Log.e("image", "yes");
                return;
            }
            Log.e("image", "no");
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }

            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

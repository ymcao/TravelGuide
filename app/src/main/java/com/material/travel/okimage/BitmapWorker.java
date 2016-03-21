package com.material.travel.okimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *  Created by caoyamin on 15/11/26.
 *  OkHttpClient下载图片
 */

public class BitmapWorker implements  Runnable {
    public String urlPath;
    public ImageView imageView;
    private Handler handler;
    public BitmapWorker(String urlPath, ImageView imageView,Handler handler){
        this.urlPath=urlPath;
        this.imageView=imageView;
        this.handler=handler;
    }
    @Override
    public void run() {
        FileDescriptor fileDescriptor = null;
        FileInputStream fileInputStream = null;
        DiskLruCache.Snapshot snapShot = null;
        try {
            /*生成图片URL对应的key*/
            final String key = CacheHelper.hashKeyForDisk(urlPath);
            /*查找key对应的缓存*/
            snapShot = CacheHelper.mDiskCache.get(key);
            if (snapShot == null) {
                // 如果没有找到对应的缓存，则准备从网络上请求数据，并写入缓存
                DiskLruCache.Editor editor = CacheHelper.mDiskCache.edit(key);
                if (editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    if (downloadUrlToStream(urlPath, outputStream)) {
                        editor.commit();
                    } else {
                        editor.abort();
                    }
                    CacheHelper.fluchCache();
                }
                //缓存被写入后，再次查找key对应的缓存//
                snapShot = CacheHelper.mDiskCache.get(key);
            }
            if (snapShot != null) {
                fileInputStream = (FileInputStream) snapShot.getInputStream(0);
                fileDescriptor = fileInputStream.getFD();
            }
            Bitmap bitmap = null;
            if (fileDescriptor != null) {
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            }
            if (bitmap != null) {
                Message msg=new Message();
                msg.obj=bitmap;
                handler.sendMessage(msg);
                CacheHelper.putMemoryCache(urlPath, bitmap);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileDescriptor == null && fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                }
            }
        }

    }

    boolean downloadUrlToStream(String urlPath, OutputStream outputStream) {
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        Request request=new Request.Builder().url(urlPath).build();
        try {
           Response response= OkImageLoader.mOkHttpClient.newCall(request).execute();
             if(response!=null&&response.isSuccessful()){

                 in = new BufferedInputStream(response.body().byteStream(), 8 * 1024);
                 out = new BufferedOutputStream(outputStream, 8 * 1024);
                 int b;
                 while ((b = in.read()) != -1) {
                     out.write(b);
                 }
                 return true;
             }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}

package com.material.travel.okimage;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.util.LruCache;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * Created by caoyamin on 15/11/26.
 */
public class CacheHelper {
    private static LruCache<String, Bitmap> mMemoryCache;
    public  static DiskLruCache mDiskCache;
    private CacheHelper() {
    }
    /**
     * 初始化LruCache。
     */
    public static void initMemoryCache(Context activity) {
        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {

                return bitmap.getByteCount();
            }
        };
        initDiskLruCache(activity);
    }
    public static void initDiskLruCache(Context context) {
        try {
            File cacheDir = getDiskCacheDir(context, OkImageLoader.CACHE_NAME);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            // 第三个参数指定同一个key可以对应多少个缓存文件
            mDiskCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1,
                    40 * 1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 把图片写入缓存。
     */
    public static void putMemoryCache(String key, Bitmap value) {
        mMemoryCache.put(key, value);
    }
    /**
     * 从缓存中读取图片数据。
     */
    public static Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    private static File getDiskCacheDir(Context context, String uniqueName) {

        String cachePath=null;

        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }

        return new File(cachePath + File.separator + uniqueName);

    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);

            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 1;

    }

    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());

            cacheKey = byteToHexString(mDigest.digest());

        } catch (Exception e) {
            e.printStackTrace();
            cacheKey = String.valueOf(key.hashCode());
        }

        return cacheKey;

    }

    public static String byteToHexString(byte[] bytes) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
    /**
     * 将缓存记录同步到journal文件中。
     */
    public static void fluchCache() {
        if (mDiskCache != null) {
            try {
                mDiskCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
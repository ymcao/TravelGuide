package com.material.travel.okimage;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by caoyamin on 15/11/26.
 */

public class OkImageLoader
{
    private  Context context;
    static volatile OkImageLoader singleton = null;
    public static final String CACHE_NAME="imgloader-cache";
    public static final OkHttpClient mOkHttpClient = new OkHttpClient();
    private ExecutorService executorService;
    private static int threadCount=1;
    /*是否需要缓存*/
    private boolean isNeedCache=true;
    /*线程调度类*/
    private Dispatcher mDispatcher;
    static{
        mOkHttpClient.setConnectTimeout(300, TimeUnit.SECONDS);
    }

    public enum Type
    {
        FIFO, LIFO
    }

    public OkImageLoader(Context context, boolean isNeedCache){
        this.context=context;
        this.isNeedCache=isNeedCache;
        if(ImageUtils.getNetworkType(context).equals("WIFI")||ImageUtils.getNetworkType(context).equals("4G")){
            executorService= Executors.newFixedThreadPool(4);
            threadCount=4;
        }else{
            executorService= Executors.newFixedThreadPool(1);
        }
    }

    public  OkImageLoader with(Context context){

        if (singleton == null) {
            synchronized (OkImageLoader.class) {
                if (singleton == null) {
                    singleton=new OkImageLoader(context, true);
                    CacheHelper.initMemoryCache(context);
                }
            }
        }
        return singleton;
    }
    public static class Builder{
        private final Context context;
        private ExecutorService service;
        private List<RequestHandler> requestHandlers;
        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
        }
        public OkImageLoader build() {
            Context context = this.context;
            return new OkImageLoader(context, true);

           }
        }

}

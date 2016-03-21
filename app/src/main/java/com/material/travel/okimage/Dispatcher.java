package com.material.travel.okimage;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

/**
 * Created by caoyamin on 15/11/26.
 */
public class Dispatcher {
    /**
     * 线程池
     */
    private OkImageLoader.Type mType;
    private ExecutorService mThreadPool;
    /**
     * 任务队列
     */
    private LinkedList<Runnable> mTaskQueue;
    /**
     * 后台轮询线程
     */
    private Thread mPoolThread;
    private Handler mPoolThreadHandler;
    private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);
    private Semaphore mSemaphoreThreadPool;
    private String urlPath;
    private ImageView imgView;
    private int resourceId;
    public  UIHandler mHandler=new UIHandler();
    public Dispatcher(ExecutorService mThreadPool, OkImageLoader.Type type, int threadCount, String urlPath, ImageView imgView){
        this.mType = type;
        this. mThreadPool =mThreadPool;
        this.mTaskQueue = new LinkedList<>();
        this.mSemaphoreThreadPool = new Semaphore(threadCount);
        this.urlPath=urlPath;
        this.imgView=imgView;

    }
    public void dispatchBegin(){
        dispatchGoing();
        BitmapWorker downloader=new BitmapWorker(urlPath,imgView,mHandler);
        buildTask(downloader).run();

    }
    public synchronized void addTask(Runnable runnable){
        try {
            if (mPoolThreadHandler == null)
                mSemaphorePoolThreadHandler.acquire();
            mTaskQueue.add(runnable);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        mPoolThreadHandler.sendEmptyMessage(0x110);

    }
    public void dispatchGoing() {
        // 后台轮询线程
        mPoolThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mPoolThreadHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        mThreadPool.execute(getTask());
                        try {
                            mSemaphoreThreadPool.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // 释放一个信号量
                mSemaphorePoolThreadHandler.release();
                Looper.loop();
            }
        };
        mPoolThread.start();
    }

    Runnable  getTask(){
        if (mType == OkImageLoader.Type.FIFO) {
            return mTaskQueue.removeFirst();
        }else if (mType == OkImageLoader.Type.LIFO) {
            return mTaskQueue.removeLast();
        }
        return null;
    }

    Runnable buildTask(BitmapWorker downloader){
        return new Runnable() {
            @Override
            public void run() {

                if(CacheHelper.getBitmapFromMemoryCache(downloader.urlPath)==null){
                       addTask(downloader);
                }else{
                    Message msg=new Message();
                    msg.obj=CacheHelper.getBitmapFromMemoryCache(downloader.urlPath);
                    mHandler.sendMessage(msg);
                }
            }
        };
    }

    private  class  UIHandler  extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bitmap bmp=(Bitmap)msg.obj;
            if(bmp!=null){
                imgView.setImageBitmap(bmp);}
        }
    }

}

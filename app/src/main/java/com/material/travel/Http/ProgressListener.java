package com.material.travel.Http;

/**
 * Created by caoyamin on 15/11/8.
 */
/**
 * 进度回调接口，比如用于文件上传与下载
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-09-02
 * Time: 17:16
 */
public interface ProgressListener {
    void onProgress(long currentBytes, long contentLength, boolean done);
}
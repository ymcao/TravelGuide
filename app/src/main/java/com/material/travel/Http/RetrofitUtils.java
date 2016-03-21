package com.material.travel.Http;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okio.Buffer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by caoyamin on 15/9/16.
 */
public class RetrofitUtils {

    private static Retrofit singleIntance = null;
    private static OkHttpClient internalClient=null;
    private static OkHttpClient okHttpClient = null;
    private String baseUrl;

    public static Retrofit getSingleIntance(String baseurl) {
        synchronized (RetrofitUtils.class) {

            if (singleIntance == null) {
                singleIntance = new Retrofit.Builder()
                        .baseUrl(baseurl)
                        .client(getOkHttpClient())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();

            }
        }
        return singleIntance;
    }
    /*Retrofit内部使用*/
    public static OkHttpClient getSingleClient(){
        synchronized (RetrofitUtils.class) {
            if (internalClient == null) {
                internalClient = new OkHttpClient();
                //internalClient.interceptors().add(new LoggingInterceptor());
            }
        }
        return okHttpClient;
    }
   /*枚举实现单例*/
   public static  enum SingleRetorfit
    {
        INSTANCE{
           Retrofit singleIntance = new Retrofit.Builder()
                    .baseUrl("")
            .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                   .build();
          };

        public static SingleRetorfit getInstance() {
            return INSTANCE;
        }
    }

    /*OkHttpClient单独使用*/
    public static OkHttpClient getOkHttpClient() {
        synchronized (RetrofitUtils.class) {
            if (okHttpClient == null) {
                okHttpClient = new OkHttpClient();
                okHttpClient.interceptors().add(new LoggingInterceptor());
            }
        }
        return okHttpClient;
    }

    /**
     * 包装OkHttpClient，用于下载文件的回调
     * @param client           待包装的OkHttpClient
     * @param progressListener 进度回调接口
     * @return 包装后的OkHttpClient，使用clone方法返回
     */
    public static OkHttpClient addProgressResponseListener(OkHttpClient client, final ProgressListener progressListener) {
        //克隆
        OkHttpClient clone = client.clone();
        //增加拦截器
        clone.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拦截
                Response originalResponse = chain.proceed(chain.request());
                //包装响应体并返回
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                        .build();
            }
        });
        return clone;
    }

    /**
     * 包装请求体用于上传文件的回调
     *
     * @param requestBody             请求体RequestBody
     * @param progressRequestListener 进度回调接口
     * @return 包装后的进度回调请求体
     */
    public static ProgressRequestBody addProgressRequestListener(RequestBody requestBody, ProgressListener progressRequestListener) {
        //包装请求体
        return new ProgressRequestBody(requestBody, progressRequestListener);
    }

    /*private static class RetrofitHolder{
        public static Retrofit resource = new Retrofit.Builder()
                .baseUrl("http://apis.baidu.com/")
                //.client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static Retrofit getRetrofitInstance(){
        return RetrofitHolder.resource;
    }*/

    /**
     * Logging
     */
    private static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Log.i("Retrofit-Request", "request-url:" + request.urlString());
            Log.i("Retrofit-Request", "request-body:" + bodyToString(request));
            Response response = chain.proceed(request);
            Log.i("Retrofit-response", "response-body:" + response.body().string());
            return chain.proceed(request);
        }
    }

    private static String bodyToString(final Request request) {
        if (request != null) {
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                if (copy != null && copy.body() != null) {
                    copy.body().writeTo(buffer);
                    return buffer.readUtf8();
                }

            } catch (final IOException e) {
                return "did not work";
            }
        }
        return "null";
    }

    public static void transferFile(Response response,String path,String filename) {
        //将返回结果转化为流，并写入文件
        int len;
        byte[] buf = new byte[2048];
        InputStream inputStream=null;
        FileOutputStream fileOutputStream=null;
        try {
            inputStream = response.body().byteStream();
            File file = new File(path+"/"+filename);
            fileOutputStream = new FileOutputStream(file);

            while ((len = inputStream.read(buf)) != -1) {
                fileOutputStream.write(buf, 0, len);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
        } catch (IOException exception) {
               exception.printStackTrace();
        }

    }
}
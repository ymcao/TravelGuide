package com.material.travel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.material.mylibrary.ui.ButtonRectangle;
import com.material.travel.Http.ProgressListener;
import com.material.travel.Http.RetrofitUtils;
import com.material.travel.app.SafeHandler;
import com.material.travel.model.DownloadResult;
import com.material.travel.model.LoginResult;
import com.material.travel.ui.LoginUi;
import com.material.travel.view.ILoginView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by caoyamin on 15/11/8.
 */
public class LoginActivity extends BaseActivity implements ILoginView {
    RelativeLayout mainLayout;
    TextInputLayout textInput_layout_name;
    TextInputLayout textInput_layout_password;
    EditText userPwd;
    EditText userName;
    ImageView headIcon;
    ButtonRectangle submitBtn;
    ProgressBar progressBar;
    //LoginSubmitPresenter loginPresenter;
    APIService service;
    View viewMask;
    LoginUi loginUi;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //loginPresenter = new LoginSubmitPresenter(this);
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        mToolbar.setTitle("");
        mToolbar.setTitleTextColor(0XFFFFFF);
        titleView.setText("账号登录");
        headIcon = (ImageView) findViewById(R.id.headIcon);
        textInput_layout_name = (TextInputLayout) findViewById(R.id.textInput_layout_name);
        textInput_layout_password = (TextInputLayout) findViewById(R.id.textInput_layout_password);
        userName = (EditText) findViewById(R.id.userName);
        userPwd = (EditText) findViewById(R.id.userPwd);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        viewMask= (View) findViewById(R.id.view_mask);
        loginUi= (LoginUi) findViewById(R.id.mLoginUi);
        submitBtn = (ButtonRectangle) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    // TODO Auto-generated method stub
                    if(loginUi.isShow()){
                        loginUi.dismiss();
                    }else{
                        loginUi.show();
                    }

                //
                //Intent i=new Intent(LoginActivity.this,PostPictureActivity.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(i);
                /*UserModel model=new UserModel();
                model.body=new UserModel.User();
                model.body.items=new ArrayList<UserModel.Item>();
                for(int i=0;i<5;i++){
                    UserModel.Item item=new UserModel.Item();
                    item.id=i+100;
                    model.body.items.add(item);
                }
                model.body.setUsername(userName.getText().toString());
                model.body.setPassword(userPwd.getText().toString());
                loginPresenter.submitLogin(model);*/
                //download();
                //testRxJavaAndroid();
                //LoginActivity act=new LoginActivity();
                //act.invokeMthod();
            }
        });
        mHandler = new SafeHandler(this) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressBar.setProgress(msg.what);
            }
        };
        //设置遮罩阴影层点击消失该界面
        viewMask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(loginUi.isShow()){
                    loginUi.dismiss();
                }
            }
        });
        //设置登录界面状态监听
        loginUi.setOnStatusListener(new LoginUi.onStatusListener() {

            @Override
            public void onShow() {
                //显示
                viewMask.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDismiss() {
                //隐藏
                viewMask.setVisibility(View.GONE);
            }
        });

    }

    public  void testRxJavaAndroid() {

        //Snackbar.make(mainLayout, "show()", Snackbar.LENGTH_LONG).show();

        String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/a.png";
        String path1= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/b.jpg";
        String root= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/";
        String root1= Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/";
        File file=new File(root);
        File file1=new File(root1);
        File[] files={file,file1};
        String[] paths={path,path1};
        Observable
             .from(files)
             .flatMap(new Func1<File, Observable<File>>() {

                   @Override
                     public Observable<File> call(File file) {
                         return Observable.from(file.listFiles());
                   }

             }).filter(new Func1<File, Boolean>() {
                   @Override
                   public Boolean call(File file) {
                    return file.getName().endsWith(".png")
                            || file.getName().endsWith(".jpg");
                    }
                })
             .map(new Func1<File, Bitmap>() {
                 @Override
                 public Bitmap call(File s) {
                     Log.e("YM", s.getAbsolutePath());
                     return BitmapFactory.decodeFile(s.getAbsolutePath());
                 }
             })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        Log.e("YM", "onNext()");
                        headIcon.setImageBitmap(bitmap);
                    }
                });
    }

    void download() {
        service = RetrofitUtils.getSingleIntance("http://192.168.0.126:8080").create(APIService.class);
        Call<DownloadResult> call = service.downloadFile("1001");
        call.enqueue(new Callback<DownloadResult>() {
                         @Override
                         public void onResponse(Response<DownloadResult> response, Retrofit retrofit) {

                             DownloadResult result = response.body();
                             if (result != null && result.result != null) {
                                 Request request = new Request.Builder()
                                         .url(result.result)
                                         .build();
                                 int index = result.result.lastIndexOf("/");
                                 String fileName = result.result.substring(index + 1);

                                 OkHttpClient client = RetrofitUtils.addProgressResponseListener(RetrofitUtils.getSingleClient(), new ProgressListener() {
                                     @Override
                                     public void onProgress(long currentBytes, long contentLength, boolean done) {
                                         Message message = new Message();
                                         message.what = (int) ((100 * currentBytes) / contentLength);
                                         mHandler.sendMessage(message);
                                     }
                                 });

                                 client.newCall(request).enqueue(new com.squareup.okhttp.Callback() {
                                     @Override
                                     public void onFailure(Request request, IOException e) {
                                     }

                                     @Override
                                     public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                                         RetrofitUtils.transferFile(response, mApp.getStoragePath(), fileName);
                                     }
                                 });
                             }
                         }

                         @Override
                         public void onFailure(Throwable t) {

                         }
                     }

        );
    }

    @Override
    public void onSubmit(LoginResult result) {

    }

    @Override
    public Context getContext() {
        return LoginActivity.this;
    }

    @Override
    public void showLoading() {
        showDialog();
    }

    @Override
    public void hideLoading() {
        dismissDialog();
    }

    /*public native String gennerateStrFromNative(String name);

    public native String getStrFromNative();

    public native void invokeMthod();
    static {
        System.loadLibrary("guideLib");
    }*/

}

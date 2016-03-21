package com.material.travel;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.material.mylibrary.ui.AVLoadingIndicatorView;
import com.material.travel.app.MyApplication;

/**
 * Created by caoyamin on 15/9/16.
 */

public class BaseActivity extends AppCompatActivity {
    public Toolbar mToolbar;
    public TextView titleView;
    public AVLoadingIndicatorView progress;
    public Context context;
    public MyApplication mApp;
    protected Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp= MyApplication.getInstance();

    }

    public void setContentView(int layoutResId){
        super.setContentView(layoutResId);
        context=this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        titleView=(TextView)findViewById(R.id.titleView);
        progress=(AVLoadingIndicatorView)findViewById(R.id.progress);
        setSupportActionBar(mToolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //状态栏透明 需要在创建SystemBarTintManager 之前调用。
            setTranslucentStatus(true);
        }
    }
    @TargetApi(19)
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showDialog(){
        progress.setVisibility(View.VISIBLE);
    }
    //
    public void dismissDialog(){
        progress.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.setVisibility(View.GONE);
            }
        }, 1500);
    }


}

package com.material.travel;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by caoyamin on 15/10/15.
 */
public class WebViewActivity  extends BaseActivity{
    private WebView v_webview;
    private String url=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w_view);
        v_webview=(WebView)findViewById(R.id.v_webview);
        titleView.setVisibility(View.GONE);
        //mToolbar.setTitleTextColor(0XFFFFFF);
        mToolbar.setNavigationIcon(R.drawable.ic_back);

        if(!TextUtils.isEmpty(getIntent().getStringExtra("load_title"))) {
            mToolbar.setSubtitle(getIntent().getStringExtra("load_title"));
        }
        url=getIntent().getStringExtra("load_url");
        if(!TextUtils.isEmpty(url)) {
            v_webview.loadUrl(url);
        }


        v_webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }
        });
    }

}

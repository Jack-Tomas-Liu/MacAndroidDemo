package com.sj.webviewperformance;

/**
 *  tbs http://x5.tencent.com/doc?id=1003
 */

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
//import android.webkit.WebView;
import com.tencent.smtt.sdk.WebView;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebViewClient;
import android.widget.Button;

public class TBSActivity extends AppCompatActivity implements View.OnClickListener{
    WebView mWebView;
    String link =  "http://www.youku.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbs);
        mWebView = (WebView)findViewById(R.id.id_tbswebview);
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.setDrawingCacheEnabled(true);
        loadWebView();
    }

    /**
     * 加载webView
     */
    private void loadWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setLoadWithOverviewMode(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(com.tencent.smtt.sdk.WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        mWebView.loadUrl(link);
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 重写返回键的事件，让它返回上一个页面,是首页的时候回到上一级。
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();//返回上一页面
                return true;
            }else {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

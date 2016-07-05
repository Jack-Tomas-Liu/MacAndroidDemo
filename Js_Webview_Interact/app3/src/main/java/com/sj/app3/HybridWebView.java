package com.sj.app3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * 混合WebView
 * 
 * @author zl 2013-1-18
 * 
 */
public class HybridWebView extends WebView {

	public HybridWebView(Context context) {
		super(context);
		init();
	}

	@SuppressLint("NewApi")
	public HybridWebView(Context context, AttributeSet attrs, int defStyle, boolean privateBrowsing) {
		super(context, attrs, defStyle, privateBrowsing);
		init();
	}

	public HybridWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public HybridWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
        WebSettings settings = getSettings();
        if (settings != null) {

            settings.setJavaScriptEnabled(true);
            // settings.setAppCacheEnabled(true);
            setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            settings.setGeolocationEnabled(true);
            //
            // // 开启HTML5离线存储
            settings.setDomStorageEnabled(true);
            settings.setAllowFileAccess(true);
			settings.setAppCacheEnabled(true);
			settings.setAllowContentAccess(true);
			settings.setLightTouchEnabled(true);
            //
            // // 开启HTML5 Web SQL Database API
            settings.setDatabaseEnabled(true);
			settings.setNeedInitialFocus(true);

            settings.setJavaScriptCanOpenWindowsAutomatically(true);

            if (Build.VERSION.SDK_INT >= 11) {
                removeJavascriptInterface("searchBoxJavaBridge_");
            }

            //设置web页的User Agent
            String agent = settings.getUserAgentString();
        }

	}

	public void removeJavascriptInterface(String s) {

	}

}

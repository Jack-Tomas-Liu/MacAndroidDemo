package com.sj.js_webview_interact;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //加载页面
        webView = (WebView) findViewById(R.id.webview);
        //允许JavaScript执行
        webView.getSettings().setJavaScriptEnabled(true);
        //找到Html文件，也可以用网络上的文件
//        webView.loadUrl("file:///android_asset/index.html");
        webView.loadUrl("file:///android_asset/index.html");
        final Contact contact = new Contact();
        // 添加一个对象, 让JS可以访问该对象的方法, 该对象中可以调用JS中的方法
        webView.addJavascriptInterface(contact, "contact");// <button id="button" onclick = "javascript:contact.toast('123')">haha</button>

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                contact.showcontacts();
            }
        },100);
    }
    @SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
    private final class Contact {
        //JavaScript调用此方法拨打电话
        @JavascriptInterface
        public void call(String phone) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        	Toast.makeText(MainActivity.this, phone, Toast.LENGTH_LONG).show();
        }

        //Html调用此方法传递数据
        @JavascriptInterface
        public void showcontacts() {
            String json = "[{\"name\":\"zxx\", \"amount\":\"9999999\", \"phone\":\"18600012345\"}]"; 
            // 调用JS中的方法
            webView.loadUrl("javascript:show('" + json + "')");
        }
        @JavascriptInterface
        public void toast(String str){
        	Toast.makeText(MainActivity.this, "aaaaaaaaaaaa  --- " + str, Toast.LENGTH_LONG).show();
        }
    }

}

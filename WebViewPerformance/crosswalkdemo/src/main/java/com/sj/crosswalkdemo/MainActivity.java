package com.sj.crosswalkdemo;

/***
 *  参考
 *  http://blog.csdn.net/sslinp/article/details/51607237
 *  https://crosswalk-project.org/documentation/shared_mode_zh.html
 *  http://www.cnblogs.com/act262/p/4486771.html
 *  http://www.mobibrw.com/2015/1934
 *  搜索的关键子
 *  crosswalk android
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.xwalk.core.XWalkView;


public class MainActivity extends AppCompatActivity {
    private XWalkView xWalkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //可以new出来或者通过findViewById来获取XWalkView
        //xWalkView = new XWalkView(this,this);
        //setContentView(xWalkView);
        xWalkView = (XWalkView)findViewById(R.id.id_webview);
        //加载指定的地址
        xWalkView.load("http://www.baidu.com",null);
    }
}

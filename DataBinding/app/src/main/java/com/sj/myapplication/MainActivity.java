package com.sj.myapplication;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sj.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.tvHello.setText("this is data bind demo");
    }
}


//    当使用 RecyclerView，ViewPager 等不是调用 setContentView 的控件时，可以用下面的方法：
//
//        ActivityMainBinding binding = DataBindingUtil.inflate(
//        getLayoutInflater(), container, attchToContainer);
//        当需要将渲染的视图添加到其他 ViewGroup 中时，可以用 getRoot() 来得到根视图：
//
//        linearLayout.addView(binding.getRoot());
//        是不是感觉很方便？但更好的是这并没有用到反射或任何相对复杂的技术，在不影响性能的情况下，可以告别那麻烦又冗长的 findViewById 了。
//
//        文／Hevin丶（简书作者）
//        原文链接：http://www.jianshu.com/p/04244afc5ec5
//        著作权归作者所有，转载请联系作者获得授权，并标注“简书作者”。

//http://www.jianshu.com/p/04244afc5ec5?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
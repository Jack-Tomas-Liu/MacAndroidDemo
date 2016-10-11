package com.sj.clickarea;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sj.simplelistview.R;

/**
 * Created by liuxinxian on 2016/10/11.
 * scrollview滑动的是view的内容，内容不会显示在隔壁的控件上，点击事件区域不动。
 */


public class TestActivity extends Activity implements View.OnClickListener {
    TextView mScrollTextView;
    Button mScrollButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        mScrollTextView = (TextView) findViewById(R.id.txt_scrollview);
        mScrollTextView.setOnClickListener(this);
        mScrollButton = (Button) findViewById(R.id.btn_scrollview);
        mScrollButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        switch (viewID) {
            case R.id.btn_scrollview:
                mScrollTextView.scrollBy(-180, -20);
                break;
            case R.id.txt_scrollview:
                Toast.makeText(this,"textview click by handle",Toast.LENGTH_SHORT).show();
                break;

        }

    }
}

package com.sj.multistateimage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView mImageView1,mImageView2,mImageView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView1 = (ImageView)findViewById(R.id.img_1);
        mImageView2 = (ImageView)findViewById(R.id.img_2);
        mImageView3 = (ImageView)findViewById(R.id.img_3);
        mImageView1.getDrawable().setLevel(1);
        mImageView2.getDrawable().setLevel(2);
        mImageView3.getDrawable().setLevel(3);
    }
}

package com.sj.webviewperformance;

/**
 *  tbs http://x5.tencent.com/doc?id=1003
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button mTBS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTBS = (Button)findViewById(R.id.btn_tbs);
        mTBS.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_tbs:
                startActivity(new Intent(MainActivity.this,TBSActivity.class));
                break;
        }
    }
}
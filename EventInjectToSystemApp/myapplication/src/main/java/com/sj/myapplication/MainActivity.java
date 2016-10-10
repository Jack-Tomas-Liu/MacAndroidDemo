package com.sj.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContent = (TextView)findViewById(R.id.id_content);
        Intent intent = getIntent();
        if(intent!=null){
            String action = intent.getAction();
            if(Intent.ACTION_VIEW.equals(action)){
                Uri uri = intent.getData();
                if(uri!=null){
                    String arg = uri.getQueryParameter("arg");
                    if(!TextUtils.isEmpty(arg)){
                        mContent.setText(arg);
                    }
                }
            }
        }
    }
}

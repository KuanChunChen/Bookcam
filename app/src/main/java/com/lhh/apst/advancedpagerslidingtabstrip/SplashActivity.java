package com.lhh.apst.advancedpagerslidingtabstrip;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.io.File;


public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        if (Environment.getExternalStorageState()//確定SD卡可讀寫
                .equals(Environment.MEDIA_MOUNTED))
        {
            File  sd=Environment.getExternalStorageDirectory();
            String path=sd.getPath()+"/BookCam";
            File file=new File(path);
            if(!file.exists()) {file.mkdir();}
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent().setClass(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 3000);
    }
}

package com.example.user.jsonarrayrequestdemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.esp.jsonparsing.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread splashThread = new Thread() {
            public void run() {
                try {
                    sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        };
        splashThread.start();

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
    };

}

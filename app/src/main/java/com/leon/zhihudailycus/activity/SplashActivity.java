package com.leon.zhihudailycus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.leon.zhihudailycus.MainActivity;
import com.leon.zhihudailycus.R;
import com.leon.zhihudailycus.view.BaseActivity;

/**
 * Created by leon on 6/6/16.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1800);
    }
}

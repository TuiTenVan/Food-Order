package com.example.foodorder.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;

import com.example.foodorder.constant.GlobalFuntion;
import com.example.foodorder.databinding.ActivitySplashBinding;
import com.example.foodorder.view.BaseActivity;
import com.example.foodorder.viewmodel.SplashViewModel;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding activitySplashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        activitySplashBinding.setSplashViewModel(new SplashViewModel());
        setContentView(activitySplashBinding.getRoot());

        startMainActivity();
    }

    private void startMainActivity() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            GlobalFuntion.startActivity(SplashActivity.this, MainActivity.class);
            finish();
        }, 2000);
    }
}
package com.ufkdgn.boilerplate.ui;

import android.os.Bundle;

import com.ufkdgn.boilerplate.R;
import com.ufkdgn.boilerplate.common.ApplicationPreferences;
import com.ufkdgn.boilerplate.ui.base.BaseActivity;
import com.ufkdgn.boilerplate.ui.base.Navigation;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int splashDuration = ApplicationPreferences.getSplashDuration(this);
        runDelayed(() -> Navigation.navigateWarmup(this), splashDuration);
    }
}
package com.ufkdgn.boilerplate.ui.base;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.ufkdgn.boilerplate.common.BackgroundOperationQueue;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private boolean enableBack = true;
    private final BackgroundOperationQueue backgroundOperationQueue = new BackgroundOperationQueue();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + (this.getClass().getName()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + (this.getClass().getName()));
        super.onResume();
        Runnable runnable = this.backgroundOperationQueue.dequeueOperation();
        while (runnable != null) {
            runnable.run();
            runnable = this.backgroundOperationQueue.dequeueOperation();
        }
        this.setContentView();
    }

    @Override
    public void onBackPressed() {
        if (this.enableBack) super.onBackPressed();
    }

    public void runDelayed(Runnable runnable, long delayMillis) {
        new Handler().postDelayed(runnable, delayMillis); // wait for X seconds
    }

    public boolean isRunning() {
        return getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED);
    }

    @CallSuper
    public void setContentView() {
    }
}

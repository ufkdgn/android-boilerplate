package com.ufkdgn.boilerplate.ui;

import android.os.Bundle;

import com.ufkdgn.boilerplate.R;
import com.ufkdgn.boilerplate.common.ApplicationContext;
import com.ufkdgn.boilerplate.proxy.base.Proxy;
import com.ufkdgn.boilerplate.proxy.operations.DummyOperation;
import com.ufkdgn.boilerplate.ui.base.BaseActivity;

public class WarmUpActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warm_up);

        // initialize ApplicationContext
        ApplicationContext _applicationContext = ApplicationContext.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        DummyOperation operation = new DummyOperation();
        operation.setCallback(this::onDummyOperationResult);
        operation.process(this, "");
    }

    public void onDummyOperationResult(DummyOperation.Response content, int code) {
        if (code != Proxy.OK) {
        }
    }
}
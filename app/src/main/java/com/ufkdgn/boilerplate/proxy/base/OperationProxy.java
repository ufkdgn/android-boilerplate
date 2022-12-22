package com.ufkdgn.boilerplate.proxy.base;

public class OperationProxy<TRequest, TResponse> extends Proxy implements ProxyCallback<TResponse> {
    private static final String TAG = "OperationProxy";
    public TRequest request;
    private ProxyCallback<TResponse> callback;

    public void setCallback(ProxyCallback<TResponse> callback) {
        this.callback = callback;
    }

    @Override
    public void onResult(TResponse content, int code) {
        if (callback != null) callback.onResult(content, code);
    }
}

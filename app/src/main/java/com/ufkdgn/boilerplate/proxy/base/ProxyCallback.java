package com.ufkdgn.boilerplate.proxy.base;

public interface ProxyCallback<T> {
    void onResult(T content, int code);
}
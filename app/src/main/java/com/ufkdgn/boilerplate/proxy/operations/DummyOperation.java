package com.ufkdgn.boilerplate.proxy.operations;

import android.content.Context;

import com.ufkdgn.boilerplate.common.ApplicationPreferences;
import com.ufkdgn.boilerplate.proxy.base.OperationProxy;

public class DummyOperation extends OperationProxy<DummyOperation.Request, DummyOperation.Response> {
    public class Request {
        public String title;
    }

    public class Response {
        public Long id;
        public String title;
    }

    public void process(Context context, String title) {
        this.request = new Request();
        this.request.title = title;
        String endpoint = ApplicationPreferences.getDefaultApiUrl(context) + "products/add";
        post(endpoint, this.request, this, Response.class);
    }
}

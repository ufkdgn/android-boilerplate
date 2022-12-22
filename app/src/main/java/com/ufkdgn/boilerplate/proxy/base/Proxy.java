package com.ufkdgn.boilerplate.proxy.base;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.ufkdgn.boilerplate.common.ApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Proxy {
    private static final String TAG = "Proxy";
    private static final String ENDPOINT = "https://lawportal.azurewebsites.net/";
    public static final String SysError = "İşlem sırasında hata oluştu.";
    public static final int Error_InvalidResponse = 499;
    public static final int Error_GeneralFailure = 498;
    public static final int OK = 200;

    public <T> void post(String api, Object body, ProxyCallback<T> callback, Class<T> clazz) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .build();

        String json = null;
        try {
            json = new Gson().toJson(body);
            Log.d(TAG, "post: request body is " + json);
        } catch (Exception e) {
            json = body.toString();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Request.Builder builder = new Request.Builder()
                .url(ENDPOINT + api)
                .post(requestBody);

        String authorizationToken = ApplicationContext.getInstance().getAuthorizationToken();
        if (authorizationToken != null) {
            builder = builder.addHeader("Authorization", "Bearer " + authorizationToken);
        }

        Request request = builder.build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    int code = response.code();
                    if (body == null) {
                        Log.d(TAG, "post: response body was empty.");
                        if (callback != null) callback.onResult(null, code);
                    } else {
                        String responseBody = response.body().string();
                        T obj = new Gson().fromJson(responseBody, clazz);
                        Log.d(TAG, "post: response body is " + responseBody);
                        if (callback != null) callback.onResult(obj, code);
                    }
                } else {
                    Log.e(TAG, "post: http error " + response.code());
                    if (callback != null) callback.onResult(null, Error_InvalidResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: e ->", e);
                if (callback != null) {
                    callback.onResult(null, Error_GeneralFailure);
                }
            }
        });
    }

    public void post(Context context, String api, Object body, ProxyCallback callback) {
        OkHttpClient client = new OkHttpClient();

        String json = null;
        try {
            json = new Gson().toJson(body);
            Log.d(TAG, "post: request body is " + json);
        } catch (Exception e) {
            json = body.toString();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Request.Builder builder = new Request.Builder()
                .url(ENDPOINT + api)
                .post(requestBody);

        String authorizationToken = ApplicationContext.getInstance().getAuthorizationToken();
        if (authorizationToken != null) {
            builder = builder.addHeader("Authorization", "Bearer " + authorizationToken);
        }

        Request request = builder.build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Log.d(TAG, "post: response body is " + responseBody);
                    finishCall(callback, context, responseBody, response.code(), null);
                } else {
                    Log.e(TAG, "post: http error " + response.code());
                    finishCall(callback, context, null, Error_InvalidResponse, new IOException("Unhandled HTTP error occurred."));
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: e ->", e);
                finishCall(callback, context, null, Error_GeneralFailure, e);
            }
        });
    }

    public void post(Context context, String api, String token, Object body, ProxyCallback callback) {
        OkHttpClient client = new OkHttpClient();

        String json = null;
        try {
            json = new Gson().toJson(body);
            Log.d(TAG, "post: request body is " + json);
        } catch (Exception e) {
            json = body.toString();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Request.Builder builder = new Request.Builder()
                .url(ENDPOINT + api)
                .post(requestBody);

        builder = builder.addHeader("Authorization", "Bearer " + token);

        Request request = builder.build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    int code = response.code();
                    Log.d(TAG, "post: response body is " + responseBody);
                    finishCall(callback, context, responseBody, code, null);
                } else {
                    Log.e(TAG, "post: http error " + response.code());
                    finishCall(callback, context, null, Error_InvalidResponse, new IOException("Unhandled HTTP error occurred."));
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: e ->", e);
                finishCall(callback, context, null, Error_GeneralFailure, e);
            }
        });
    }

    private void finishCall(ProxyCallback callback, Context context, String response, int code, IOException exception) {
        if (callback == null) return;
        if (context instanceof AppCompatActivity)
            ((AppCompatActivity) context).runOnUiThread(() -> callback.onResult(response, code));
        else
            callback.onResult(response, code);
    }
}

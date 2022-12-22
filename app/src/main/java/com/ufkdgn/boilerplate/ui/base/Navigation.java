package com.ufkdgn.boilerplate.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ufkdgn.boilerplate.ui.WarmUpActivity;

public class Navigation {
    public static void navigateWarmup(Context context) {
        Intent intent = new Intent(context, WarmUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
}

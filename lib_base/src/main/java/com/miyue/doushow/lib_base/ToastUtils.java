package com.miyue.doushow.lib_base;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtils {

    private static Handler handler;

    private ToastUtils() {
        throw new AssertionError();
    }

    public static void show(Context context, int resId) {
        if (context == null) return;
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        if (context == null) return;
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(final Context context, final CharSequence text, final int duration) {
        if (TextUtils.isEmpty(text) || context == null)
            return;
        if (handler == null)
            synchronized (ToastUtils.class) {
                if (handler == null) handler = new Handler(Looper.getMainLooper());
            }
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, duration).show();
            }
        });
    }

    public static void show(Context context, int resId, Object... args) {
        if (context == null) return;
        show(context, String.format(context.getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        if (context == null) return;
        show(context, String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(Context context, String format, int duration, Object... args) {
        show(context, String.format(format, args), duration);
    }
}
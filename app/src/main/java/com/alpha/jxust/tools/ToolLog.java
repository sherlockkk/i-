package com.alpha.jxust.tools;

import android.util.Log;

/**
 * @author SongJian
 * @created 2016/1/19.
 * @e-mail 1129574214@qq.com
 */
public class ToolLog {

    private static final String TAG = "ToolLog";

    /**
     * 上线后关闭log
     */
    private static final Boolean DEBUG = true;

    public static void d(String tag, String msg) {
        if (DEBUG) {
            tag = Thread.currentThread().getName() + ":" + tag;
            Log.d(TAG, tag + " : " + msg);
        }
    }

    public static void d(String tag, String msg, Throwable error) {
        if (DEBUG) {
            tag = Thread.currentThread().getName() + ":" + tag;
            Log.d(TAG, tag + " : " + msg, error);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            tag = Thread.currentThread().getName() + ":" + tag;
            Log.i(TAG, tag + " : " + msg);
        }
    }

    public static void i(String tag, String msg, Throwable error) {
        if (DEBUG) {
            tag = Thread.currentThread().getName() + ":" + tag;
            Log.i(TAG, tag + " : " + msg, error);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            tag = Thread.currentThread().getName() + ":" + tag;
            Log.w(TAG, tag + " : " + msg);
        }
    }

    public static void w(String tag, String msg, Throwable error) {
        if (DEBUG) {
            tag = Thread.currentThread().getName() + ":" + tag;
            Log.w(TAG, tag + " : " + msg, error);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            tag = Thread.currentThread().getName() + ":" + tag;
            Log.e(TAG, tag + " : " + msg);
        }
    }

    public static void e(String tag, String msg, Throwable error) {
        if (DEBUG) {
            tag = Thread.currentThread().getName() + ":" + tag;
            Log.e(TAG, tag + " : " + msg, error);
        }
    }
}

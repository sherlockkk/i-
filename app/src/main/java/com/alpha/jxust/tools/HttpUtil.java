package com.alpha.jxust.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alpha.jxust.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * @author SongJian
 * @created 2016/1/19.
 * @e-mail 1129574214@qq.com
 */
public class HttpUtil {
    private static AsyncHttpClient client = new AsyncHttpClient();
    public static void get (String url, ResponseHandlerInterface responseHandlerInterface){
        client.get(url,responseHandlerInterface);
    }

    public static boolean isNetworkConnected(Context context){
        if (context!= null){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo!=null){
                return networkInfo.isAvailable();
            }
        }
        return false;
    }
}

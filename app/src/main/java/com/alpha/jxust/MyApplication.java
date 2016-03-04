package com.alpha.jxust;

import android.app.Application;
import android.content.Context;
import android.text.NoCopySpan;

import com.avos.avoscloud.AVOSCloud;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * @author SongJian
 * @created 2016/2/10.
 * @e-mail 1129574214@qq.com
 */
public class MyApplication extends Application {
    private static Context mContext;
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        myApplication = this;

        //LeanCloud组件初始化
        AVOSCloud.initialize(this,Constants.APPID_LEANCLOUD,Constants.APPKEY_LEANCLOUD);
        //uil初始化
        initImageLoader(this);

    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    public static Context getmContextInstance() {
        return mContext;
    }

    private void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPoolSize(3);
        config.memoryCache(new WeakMemoryCache());
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
//        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }


}

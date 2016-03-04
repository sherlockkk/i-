package com.alpha.jxust.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import com.alpha.jxust.R;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * @author SongJian
 * @created 2016/3/2.
 * @e-mail 1129574214@qq.com
 */
public class StatusUtil {
    public synchronized static void displayAvatar(AVUser user, ImageView avatarView){
        AVFile file = user.getAVFile("avatar");
        if (file!=null){
            String avatarUrl = file.getUrl();
            ImageLoader.getInstance().displayImage(avatarUrl,avatarView,avatarImageOptions);
        }else {
            avatarView.setImageResource(R.drawable.banner_01);
        }
    }

    //设置默认头像
    public static DisplayImageOptions avatarImageOptions = new DisplayImageOptions.Builder()
//            .showImageOnLoading(R.drawable.default_user_avatar)
//            .showImageForEmptyUri(R.drawable.default_user_avatar)
//            .showImageOnFail(R.drawable.default_user_avatar)
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .considerExifParams(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
            //.displayer(new RoundedBitmapDisplayer(20))
            //.displayer(new FadeInBitmapDisplayer(100))// 淡入
            .build();

    public static DisplayImageOptions normalImageOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.empty_photo)
            .showImageForEmptyUri(R.drawable.empty_photo)
            .showImageOnFail(R.drawable.image_load_fail)
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .considerExifParams(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
            //.displayer(new RoundedBitmapDisplayer(20))
            //.displayer(new FadeInBitmapDisplayer(100))// 淡入
            .build();

    public static void toast(Context context, int strId) {
        toast(context, context.getString(strId));
    }
    public static void toast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
    public static boolean filterException(Context ctx, Exception e) {
        if (e != null) {
            toast(ctx, e.getMessage());
            return false;
        } else {
            return true;
        }
    }
}



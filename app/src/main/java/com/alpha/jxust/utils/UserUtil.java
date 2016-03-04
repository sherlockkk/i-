package com.alpha.jxust.utils;

import com.avos.avoscloud.AVUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SongJian
 * @created 2016/3/3.
 * @e-mail 1129574214@qq.com
 */
public class UserUtil {
    public static Map<String, AVUser> userCache = new HashMap<>();

    public static void regiserUser(AVUser user) {
        userCache.put(user.getObjectId(), user);
    }

    public static void registerBatchUser(List<AVUser> users) {
        for (AVUser user : users) {
            regiserUser(user);
        }
    }

    public static AVUser lookupUser(String userId) {
        return userCache.get(userId);
    }
}

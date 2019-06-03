package com.ancely.share.others;

import com.ancely.share.ShareApplication;
import com.ancely.share.utils.PreferenceUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.others
 *  @文件名:   HttpConstant
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/31 7:46 PM
 *  @描述：    TODO
 */
public class HttpConstant {
    public static final String SAVE_USER_LOGIN_KEY = "user/login";
    public static final String SAVE_USER_REGISTER_KEY = "user/register";
    public static final String COLLECTIONS_WEBSITE = "lg/collect";
    public static final String UNCOLLECTIONS_WEBSITE = "lg/uncollect";
    public static final String ARTICLE_WEBSITE = "article";
    public static final String TODO_WEBSITE = "lg/todo";
    public static final String SET_COOKIE_KEY = "set-cookie";
    public static final String COOKIE_NAME = "Cookie";
    public static final long MAX_CACHE_SIZE = 52428800L;

    public static String encodeCookie(List<String> cookies) {
        StringBuilder builder = new StringBuilder();
        HashSet<String> set = new HashSet<>();
        for (String cookie : cookies) {
            String[] split = cookie.split(";");
            set.addAll(Arrays.asList(split));
        }
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String cookie = iterator.next();
            builder.append(cookie).append(";");
        }
        int last = builder.lastIndexOf(";");
        if (builder.length() - 1 == last) {
            builder.deleteCharAt(last);
        }
        return builder.toString();
    }

    public static void saveCookie(String requestUrl, String domain, String cookie) {
        if (requestUrl == null) {
            return;
        }
        PreferenceUtils.saveString(ShareApplication.getInstance(), requestUrl, cookie);
        if (domain == null) {
            return;
        }
        PreferenceUtils.saveString(ShareApplication.getInstance(), domain, cookie);
    }
}

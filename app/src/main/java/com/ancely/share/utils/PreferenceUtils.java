package com.ancely.share.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

/**
 * Created by SunShine on 2017/9/1.
 */

public class PreferenceUtils {

    private final static String PREFERENCE_CATCH = "share_android";

    public static String getString(Context mContext, String key) {
        return getString(mContext, key, "");
    }

    public static String getString(Context mContext, String key, String defaultValue) {
        SharedPreferences spf = mContext.getSharedPreferences(PREFERENCE_CATCH, Context.MODE_PRIVATE);
        return spf.getString(key, defaultValue);
    }

    public static void saveString(Context mContext, String key, String value) {
        SharedPreferences spf = mContext.getSharedPreferences(PREFERENCE_CATCH, Context.MODE_PRIVATE);
        spf.edit().putString(key, value).apply();
    }


    public static HashMap<String,String> getMap(Context mContext, String key) {
        SharedPreferences spf = mContext.getSharedPreferences(PREFERENCE_CATCH, Context.MODE_PRIVATE);
        String value = getString(mContext, key);
        HashMap<String,String> map = null;
        if (!TextUtils.isEmpty(value)){
            try {
                map = new Gson().fromJson(value,new TypeToken<HashMap<String,String>>(){}.getType());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return map;
    }

    public static void saveObject(Context mContext, String key, Object object) {
        SharedPreferences spf = mContext.getSharedPreferences(PREFERENCE_CATCH, Context.MODE_PRIVATE);
        spf.edit().putString(key, new Gson().toJson(object)).apply();
    }

    public static void saveBoolean(Context mContext, String key, boolean value) {
        SharedPreferences spf = mContext.getSharedPreferences(PREFERENCE_CATCH, Context.MODE_PRIVATE);
        spf.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context mContext, String key) {
        SharedPreferences spf = mContext.getSharedPreferences(PREFERENCE_CATCH, Context.MODE_PRIVATE);
        return spf.getBoolean(key, false);
    }

    public static void clear(Context context, String key){
        SharedPreferences spf = context.getSharedPreferences(PREFERENCE_CATCH,Context.MODE_PRIVATE);
        spf.edit().remove(key).apply();
    }

    public static void clearAll(Context context){
        SharedPreferences spf = context.getSharedPreferences(PREFERENCE_CATCH,Context.MODE_PRIVATE);
        spf.edit().clear().apply();
    }
}

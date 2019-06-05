package com.ancely.share.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.ancely.share.ShareApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

/**
 * Created by SunShine on 2017/9/1.
 */

public class PreferenceUtils {

    private final static String PREFERENCE_CATCH = "share_android";

    public static String getString( String key) {
        return getString(key, "");
    }

    public static String getString( String key, String defaultValue) {
        SharedPreferences spf = ShareApplication.getInstance().getSharedPreferences(PREFERENCE_CATCH, Context.MODE_PRIVATE);
        return spf.getString(key, defaultValue);
    }
    public static void saveString(String key, String value) {
        SharedPreferences spf = ShareApplication.getInstance().getSharedPreferences(PREFERENCE_CATCH, Context.MODE_PRIVATE);
        spf.edit().putString(key, value).apply();
    }
    public static HashMap<String,String> getMap(Context mContext, String key) {
        SharedPreferences spf = mContext.getSharedPreferences(PREFERENCE_CATCH, Context.MODE_PRIVATE);
        String value = getString(key);
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


    public static void saveBoolean( String key, boolean value) {
        SharedPreferences spf = ShareApplication.getInstance().getSharedPreferences(PREFERENCE_CATCH, Context.MODE_PRIVATE);
        spf.edit().putBoolean(key, value).apply();
    }
    public static boolean getBoolean(String key) {
        SharedPreferences spf = ShareApplication.getInstance().getSharedPreferences(PREFERENCE_CATCH, Context.MODE_PRIVATE);
        return spf.getBoolean(key, false);
    }

    public static void clear(String key){
        SharedPreferences spf = ShareApplication.getInstance().getSharedPreferences(PREFERENCE_CATCH,Context.MODE_PRIVATE);
        spf.edit().remove(key).apply();
    }

    public static void clearAll(){
        SharedPreferences spf = ShareApplication.getInstance().getSharedPreferences(PREFERENCE_CATCH,Context.MODE_PRIVATE);
        spf.edit().clear().apply();
    }
}

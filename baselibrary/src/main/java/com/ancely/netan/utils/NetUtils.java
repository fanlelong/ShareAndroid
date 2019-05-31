package com.ancely.netan.utils;/*
 *  @项目名：  ancely_market
 *  @包名：    com.ancely.market.utils
 *  @文件名:   NetUtils
 *  @创建者:   fanlelong
 *  @创建时间:  2018/7/31 下午2:54
 *  @描述：    网络相关工具类
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {
    /**
     * 判断网络是否连接 * @param context * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                return info.getState() == NetworkInfo.State.CONNECTED;
            }
        }
        return false;
    }

    public static boolean isRealWifi(Context context) {

        Runtime runtime = Runtime.getRuntime();
        try {
            Process p = runtime.exec("ping -c 3 www.baidu.com");
            int ret = p.waitFor();
            return ret == 0;
        } catch (Exception e) {
            return false;
        }
    }
}
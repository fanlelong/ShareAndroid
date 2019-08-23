package com.ancely.share;

import android.app.Application;

import com.ancely.netan.NetWorkManager;
import com.squareup.leakcanary.LeakCanary;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share
 *  @文件名:   ShareApplication
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/31 2:20 PM
 *  @描述：    TODO
 */
public class ShareApplication extends Application {
    public static ShareApplication sInstance;

    public static ShareApplication getInstance() {
        return sInstance;
    }

    public static int destoryFlag;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
//        List<Interceptor> interceptors = new ArrayList<>();
//        interceptors.add(new HeaderInterceptor());
//        interceptors.add(new SaveCookieInterceptor());
        NetWorkManager.getInstance().init("https://www.wanandroid.com/", null, this);
    }
}

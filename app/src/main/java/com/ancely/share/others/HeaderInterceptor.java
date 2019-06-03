package com.ancely.share.others;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.ancely.share.ShareApplication;
import com.ancely.share.utils.PreferenceUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.others
 *  @文件名:   HeaderInterceptor
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/31 8:08 PM
 *  @描述：    TODO
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Content-type","application/x-www-form-urlencoded");
        String domain = request.url().host();
        String url = request.url().toString();
        if (TextUtils.isEmpty(domain) && url.contains(HttpConstant.COLLECTIONS_WEBSITE) || url.contains(HttpConstant.UNCOLLECTIONS_WEBSITE)
                || url.contains(HttpConstant.ARTICLE_WEBSITE)
                || url.contains(HttpConstant.TODO_WEBSITE)) {
            String cookie = PreferenceUtils.getString(ShareApplication.getInstance(), domain);
            if (TextUtils.isEmpty(cookie)) {
                builder.addHeader(HttpConstant.COOKIE_NAME, cookie);
            }
        }
        return chain.proceed(builder.build());
    }
}

package com.ancely.share.others;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.others
 *  @文件名:   SaveCookieInterceptor
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/31 7:35 PM
 *  @描述：    TODO
 */
public class SaveCookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String requestUrl = request.url().toString();
        String domain = request.url().host();
        // set-cookie maybe has multi, login to save cookie
        if ((requestUrl.contains(HttpConstant.SAVE_USER_LOGIN_KEY)
                || requestUrl.contains(HttpConstant.SAVE_USER_REGISTER_KEY))
                && !response.headers(HttpConstant.SET_COOKIE_KEY).isEmpty()) {
            List<String> cookies = response.headers(HttpConstant.SET_COOKIE_KEY);
            String cookie = HttpConstant.encodeCookie(cookies);
            HttpConstant.saveCookie(requestUrl, domain, cookie);
        }
        return response;
    }
}

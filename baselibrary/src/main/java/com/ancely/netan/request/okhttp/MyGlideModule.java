package com.ancely.netan.request.okhttp;

/*
 *  @项目名：  NewCalendar
 *  @包名：    calendar.ancyel.com.newcalendar.retrofit.mvp.glide
 *  @文件名:   MyGlideModule
 *  @创建者:   fanlelong
 *  @创建时间:  2018/8/8 下午5:29
 *  @描述：    TODO
 */

import android.content.Context;
import android.support.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

//@GlideModule
public class MyGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}

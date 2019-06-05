package com.ancely.share;

import com.ancely.share.base.HttpResult;
import com.ancely.share.bean.Article;
import com.ancely.share.bean.HomeBanner;
import com.ancely.share.bean.HomeBean;
import com.ancely.share.bean.HotTipsBean;
import com.ancely.share.bean.LoginBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share
 *  @文件名:   ShareApi
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/31 7:31 PM
 *  @描述：    请求
 */
public interface ShareApi {
    @FormUrlEncoded
    @POST("user/login")
    Observable<HttpResult<LoginBean>> login(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("user/register")
    Observable<HttpResult<LoginBean>> register(@FieldMap Map<String, Object> params);


    /**
     * 获取文章列表
     * http://www.wanandroid.com/article/list/0/json
     *
     * @param pageNum 当前页码
     */
    @GET("article/list/{pageNum}/json")
    Observable<HttpResult<HomeBean>> getArticles(@Path("pageNum") int pageNum);

    @GET("article/top/json")
    Observable<HttpResult<List<Article>>> getTopArticles();

    @GET("banner/json")
    Observable<HttpResult<List<HomeBanner>>> getBanner();

    @POST("lg/collect/{id}/json")
    Observable<HttpResult<String>> collect(@Path("id") int collectId);

    @POST("lg/uncollect_originId/{id}/json")
    Observable<HttpResult<String>> cancelCollect(@Path("id") int collectId);

    @GET("hotkey/json")
    Observable<HttpResult<List<HotTipsBean>>> getHotTips();
}

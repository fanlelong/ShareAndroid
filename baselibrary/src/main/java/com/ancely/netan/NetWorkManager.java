package com.ancely.netan;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.ancely.netan.network.NetChangerManager;
import com.ancely.netan.request.cookie.CookieManger;
import com.ancely.netan.request.life.LifeManagerRetriever;
import com.ancely.netan.request.okhttp.HttpLoggingInterceptor;
import com.ancely.netan.request.okhttp.HttpsSSL;
import com.ancely.netan.request.okhttp.ProgressInterceptor;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Zaifeng on 2018/2/28.
 * API初始化类
 */

public class NetWorkManager {

    @SuppressLint("StaticFieldLeak")
    private static volatile NetWorkManager mInstance;
    private Retrofit retrofit;
    private OkHttpClient mClient;
    private Headers mHeaders;
    private LinkedHashMap<String, String> heardsMap;
    private Context context;
    private HttpsSSL.SSLParams sslParams;
    private final LifeManagerRetriever lifeManagerRetriever;


    private NetWorkManager(LifeManagerRetriever retriever) {
        this.lifeManagerRetriever = retriever;
    }

    public static NetWorkManager getInstance() { //DCL单例,优化懒汉式单例

//        return SinglerNetWorkManager.sInstance;//静态内部类单例
        if (mInstance == null) {
            synchronized (NetWorkManager.class) {
                if (mInstance == null) {
                    initializeNetWorkManager();
                }
            }
        }
        return mInstance;

    }

//    //静态内部类单例
//    private static class SinglerNetWorkManager {
//        public static final NetWorkManager sInstance = initializeNetWorkManager();
//    }

    private static void initializeNetWorkManager() {
        LifeManagerRetriever retriever = new LifeManagerRetriever();

        //JVM虚拟机: 首先:会为 mInstance分配内存,第二:会调用NetWorkManager的构造方法来初始化变量,第三:将mInstance这个对象指向JVM分配的内存空间
        mInstance = new NetWorkManager(retriever);//这么代码不会以产面三步执行 解决方法mInstance用volatile修饰 禁止JVM指令重排序优化
    }

    /**
     * 初始化必要对象和参数
     */

    public NetWorkManager init(String host, Context context) {
        return init(host, null, context);
    }

    public Context getContext() {
        return context;
    }

    public NetWorkManager init(String host, List<Interceptor> interceptors, Context context) {
        if (context instanceof Application) {
            this.context = context;
        } else {
            this.context = context.getApplicationContext();
        }
        NetChangerManager.getDefault().init(this.context);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor("ancelyOkhttp:");
        httpLoggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        httpLoggingInterceptor.setColorLevel(Level.INFO);
        // 初始化okhttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new ProgressInterceptor())
                .addInterceptor(chain -> {
                    //默认添加 Accept-Language
                    //默认添加 User-Agent
                    Request.Builder builder1 = chain.request().newBuilder();
                    addHeards(builder1);
                    Request request = builder1
                            .addHeader("Content-type", "application/json; charset=utf-8")
                            .addHeader("Accept", "application/json")
                            .build();
                    return chain.proceed(request);
                })
                .addInterceptor(httpLoggingInterceptor)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .writeTimeout(60000, TimeUnit.MILLISECONDS)
                .connectTimeout(60000, TimeUnit.MILLISECONDS);
        if (interceptors != null && interceptors.size() > 0) {
            for (Interceptor interceptor : interceptors) {
                if (interceptor != null) builder.addInterceptor(interceptor);
            }
        }
        builder.cookieJar(new CookieManger(getContext()))
                .retryOnConnectionFailure(true);
        if (sslParams != null)
            builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        mClient = builder.build();
        // 初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(mClient)
                .baseUrl(host)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return this;
    }


    public OkHttpClient getClient() {
        return mClient;
    }

    public NetWorkManager sslSocketFactory(String sslSocket) {
        if (!TextUtils.isEmpty(sslSocket)) {
            sslParams = HttpsSSL.getSslSocketFactory
                    (new InputStream[]{new Buffer().writeUtf8(sslSocket).inputStream()}, null, null);
        }
        return this;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }


    /**
     * 添加全局请求头
     */
    public void addHeards(Headers headers) {
        mHeaders = headers;
    }

    /**
     * 添加全局请求头
     */
    public NetWorkManager addheard(String key, String value) {
        if (heardsMap == null) {
            heardsMap = new LinkedHashMap<>();
        }
        heardsMap.put(key, value);
        return this;
    }

    private void addHeards(Request.Builder builder) {
        if (mHeaders != null) {
            builder.headers(mHeaders);
        }
        if (heardsMap != null) {
            for (String s : heardsMap.keySet()) {
                builder.addHeader(s, heardsMap.get(s));
            }
        }
    }

    @NonNull
    public LifeManagerRetriever getRequestManagerRetriever() {
        return lifeManagerRetriever;
    }
}
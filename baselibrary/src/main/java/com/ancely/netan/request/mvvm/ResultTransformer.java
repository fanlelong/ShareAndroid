package com.ancely.netan.request.mvvm;

/*
 *  @项目名：  NewCalendar 
 *  @包名：    calendar.ancyel.com.newcalendar.retrofit.mvp
 *  @文件名:   ResultTransformer
 *  @创建者:   fanlelong
 *  @创建时间:  2018/8/7 下午5:05
 *  @描述：    请求错误
 */

import com.ancely.netan.request.exception.CustomException;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ResultTransformer {
    public static <T> ObservableTransformer<T, T> handleResult() {

        return upstream -> upstream.onErrorResumeNext(new ErrorResumeFunction<>());
//        return upstream -> upstream
//                .onErrorResumeNext(new ErrorResumeFunction<>());
//                .flatMap(new ResultBeanFunction<>());
    }

    /**
     * 统一线程处理
     * 发布事件io线程，接收事件主线程
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {//compose处理线程
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends T>> {

        @Override
        public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResultBeanFunction<T> implements Function<T, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(T resultBean) throws Exception {
            return Observable.just(resultBean);
        }
    }
}

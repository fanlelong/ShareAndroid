package com.ancely.netan.request.mvvm;

/*
 *  @项目名：  NewCalendar
 *  @包名：    calendar.ancyel.com.newcalendar.retrofit.mvp
 *  @创建者:   fanlelong
 *  @创建时间:  2018/8/7 下午2:43
 *  @描述：
 */

import com.ancely.netan.request.mvvm.bean.ResponseBean;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;

public interface IBaseModelP<T> {

    void accessSucceed(ResponseBean<T> responseBean, int flag, boolean isShowLoading);

    void accessMoreSuccess(ResponseBean<T> responseBean, int flag, boolean isShowLoading);

    void accessError(int code, String errorMsg,ResponseBean<T> responseBean);

    void unDisposable();

    void disposable(Disposable s);

    ObservableTransformer<T,T> getTransformer();


}

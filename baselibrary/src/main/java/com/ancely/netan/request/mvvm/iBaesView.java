package com.ancely.netan.request.mvvm;


import com.ancely.netan.request.mvvm.bean.RequestErrBean;
import com.ancely.netan.request.mvvm.bean.ResponseBean;

/*
 *  @项目名：  SharingTechnology
 *  @包名：    com.ancely.rxjava.mvvm
 *  @文件名:   iBaesView
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/17 3:18 PM
 *  @描述：    TODO
 */
public interface iBaesView<T> {
    void showloading(int flag);

    void hideLoading(int flag);

    void accessError(RequestErrBean errBean);

    void accessSuccess(ResponseBean<T> responseBean);

    void accessMoreSuccess(ResponseBean<T> responseBean);

}

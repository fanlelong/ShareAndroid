package com.ancely.netan.request.mvvm;

import android.arch.lifecycle.MutableLiveData;
import com.ancely.netan.request.mvvm.bean.RequestErrBean;
import com.ancely.netan.request.mvvm.bean.ResponseBean;

/*
 *  @项目名：  SharingTechnology
 *  @包名：    com.ancely.rxjava.mvvm
 *  @文件名:   IBaseViewModel
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/15 7:14 PM
 */
interface IBaseViewModel<T> {
    void hanlerDataRequestSuccess(ResponseBean<T> t);

    MutableLiveData<ResponseBean<T>> getResultLiveData();

    MutableLiveData<ResponseBean<T>> getMoreLiveData();

    MutableLiveData<RequestErrBean> getErrorLiveData();

    MutableLiveData<Integer> getShowLoadingLiveData();

    MutableLiveData<Integer> getHideLoadingLiveData();
}

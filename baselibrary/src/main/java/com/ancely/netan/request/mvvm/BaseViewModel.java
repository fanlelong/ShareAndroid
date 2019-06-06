package com.ancely.netan.request.mvvm;

/*
 *  @项目名：  BaseMvp
 *  @包名：    com.ancely.rxjava.mvp
 *  @文件名:   BaseViewModel
 *  @创建者:   fanlelong
 *  @创建时间:  2018/8/31 下午3:02
 *  @描述：    viewmodel基类
 */

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.ancely.netan.request.mvvm.bean.RequestErrBean;
import com.ancely.netan.request.mvvm.bean.ResponseBean;
import com.google.gson.Gson;

public class BaseViewModel<T> extends ViewModel implements IBaseViewModel<T> {

    private MediatorLiveData<ResponseBean<T>> resultLiveData;
    private MediatorLiveData<ResponseBean<T>> moreLiveData;
    private MutableLiveData<RequestErrBean> errorLiveData;
    private MutableLiveData<Integer> showLoadingLiveData;
    private MutableLiveData<Integer> hideLoadingLiveData;
        private LiveData<PagedList<Object>> mPagedListLiveData;
    private final Gson mGson = new Gson();


    public <R> R fromJson(String json, Class<R> classOfT) {
        return mGson.fromJson(json, classOfT);
    }

    @Override
    public MutableLiveData<ResponseBean<T>> getResultLiveData() {
        if (null == resultLiveData) {
            resultLiveData = new MediatorLiveData<>();
        }
        return resultLiveData;
    }

        public LiveData<PagedList<Object>> getPagedListLiveData(){

        if (null == mPagedListLiveData) {
            PagedList.Config config = new PagedList.Config.Builder().setPageSize(20).setPrefetchDistance(2).setInitialLoadSizeHint(14)
                    .setEnablePlaceholders(false)
                    .build();
            mPagedListLiveData = new LivePagedListBuilder<>(new PageDataFactory<>(), config).build();
        }

        return mPagedListLiveData;
    }
    @Override
    public MutableLiveData<ResponseBean<T>> getMoreLiveData() {
        if (null == moreLiveData) {
            moreLiveData = new MediatorLiveData<>();
        }
        return moreLiveData;
    }

    @Override
    public MutableLiveData<RequestErrBean> getErrorLiveData() {
        if (null == errorLiveData) {
            errorLiveData = new MutableLiveData<>();
        }
        return errorLiveData;
    }

    @Override
    public MutableLiveData<Integer> getShowLoadingLiveData() {
        if (null == showLoadingLiveData) {
            showLoadingLiveData = new MutableLiveData<>();
        }
        return showLoadingLiveData;
    }

    @Override
    public MutableLiveData<Integer> getHideLoadingLiveData() {
        if (null == hideLoadingLiveData) {
            hideLoadingLiveData = new MutableLiveData<>();
        }
        return hideLoadingLiveData;
    }

    @Override
    public void hanlerDataRequestSuccess(ResponseBean<T> t) {

    }
}

package com.ancely.netan.request.mvvm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

/*
 *  @项目名：  BaseMvp
 *  @包名：    com.ancely.rxjava.mvvm
 *  @文件名:   PageDataFactory
 *  @创建者:   fanlelong
 *  @创建时间:  2018/11/20 下午4:24
 */
public  class PageDataFactory<T, VM> extends DataSource.Factory<T, VM> {

    private MutableLiveData<PageDataSource<T, VM>> sourceLiveData = new MutableLiveData<>();

    @Override
    public DataSource<T, VM> create() {
        PageDataSource<T, VM> source = new PageDataSource<T, VM>() {
            @Override
            protected void setLoadAfterResult(LoadParams<T> params, LoadCallback<T, VM> callback) {

            }

            @Override
            protected void setLoadInitial(LoadInitialParams<T> params, LoadInitialCallback<T, VM> callback) {

            }
        };
        sourceLiveData.postValue(source);
        return source;
    }

//    public abstract PageDataSource<T, VM> createDataSource();
}

package com.ancely.netan.request.mvvm;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

/*
 *  @项目名：  BaseMvp
 *  @包名：    com.ancely.rxjava.mvvm
 *  @文件名:   PageDataSource
 *  @创建者:   fanlelong
 *  @创建时间:  2018/11/20 下午4:23
 *  @描述：    TODO
 */
public abstract class PageDataSource<T, VM> extends PageKeyedDataSource<T, VM> {

    @Override
    public void loadInitial(@NonNull LoadInitialParams<T> params, @NonNull LoadInitialCallback<T, VM> callback) {
        setLoadInitial(params, callback);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<T> params, @NonNull LoadCallback<T, VM> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<T> params, @NonNull LoadCallback<T, VM> callback) {
        setLoadAfterResult(params, callback);
    }

    /**
     * 加载更多
     */
    protected abstract void setLoadAfterResult(LoadParams<T> params, LoadCallback<T, VM> callback);


    /**
     * 初始化刷新
     */
    protected abstract void setLoadInitial(LoadInitialParams<T> params, LoadInitialCallback<T, VM> callback);
}

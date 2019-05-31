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
public class PageDataSource<Key, Value> extends PageKeyedDataSource<Key, Value> {

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Key> params, @NonNull LoadInitialCallback<Key, Value> callback) {

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Key> params, @NonNull LoadCallback<Key, Value> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Key> params, @NonNull LoadCallback<Key, Value> callback) {

    }
}

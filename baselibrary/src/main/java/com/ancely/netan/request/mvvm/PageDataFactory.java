package com.ancely.netan.request.mvvm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

/*
 *  @项目名：  BaseMvp
 *  @包名：    com.ancely.rxjava.mvvm
 *  @文件名:   PageDataFactory
 *  @创建者:   fanlelong
 *  @创建时间:  2018/11/20 下午4:24
 *  @描述：    TODO
 */
public class PageDataFactory<Key, Value> extends DataSource.Factory<Key, Value> {

    private MutableLiveData<PageDataSource<Key, Value>> sourceLiveData = new MutableLiveData<>();
    @Override
    public DataSource<Key, Value> create() {
        PageDataSource<Key, Value> source = new PageDataSource<>();
        sourceLiveData.postValue(source);
        return source;
    }
}

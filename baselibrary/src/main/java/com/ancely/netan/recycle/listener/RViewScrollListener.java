package com.ancely.netan.recycle.listener;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/*
 *  @项目名：  ModlerApp
 *  @包名：    luckly.ancely.com.commoentmodule.recycle.listener
 *  @文件名:   RViewScrollListener
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/30 2:25 PM
 *  @描述：    TODO
 */
public interface RViewScrollListener {
    void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState);

    void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy);

    void onLoadMoreDatas();
}

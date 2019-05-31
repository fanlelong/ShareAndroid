package com.ancely.netan.recycle.listener;

import android.view.View;

/*
 *  @项目名：  ModlerApp
 *  @包名：    luckly.ancely.com.commoentmodule.recycle.listener
 *  @文件名:   ItemListener
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/21 2:01 PM
 *  @描述：    条目点击监听
 */
public interface ItemListener<T> {
    void onItemClick(View view, T entity, int position);

    boolean onItemLongClick(View view, T entry, int position);
}

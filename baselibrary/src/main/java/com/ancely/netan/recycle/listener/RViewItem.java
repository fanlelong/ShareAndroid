package com.ancely.netan.recycle.listener;


import com.ancely.netan.recycle.holder.RViewHolder;

/*
 *  @项目名：  ModlerApp
 *  @包名：    luckly.ancely.com.commoentmodule.recycle
 *  @文件名:   RViewItem
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/21 1:44 PM
 *  @描述：    某一类对象的接口
 */
public interface RViewItem<T> {

    int getItemLayout();//获取item布局

    boolean openClick();//是否开始了点击

    boolean isItemView(T entry, int position);//是否为当前item布局

    void convert(RViewHolder holder, T entry, int position);//item控件和事件绑定


}

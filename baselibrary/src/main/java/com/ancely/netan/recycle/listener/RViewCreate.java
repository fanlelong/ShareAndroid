package com.ancely.netan.recycle.listener;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import com.ancely.netan.recycle.base.RViewAdapter;

/*
 *  @项目名：  ModlerApp
 *  @包名：    luckly.ancely.com.commoentmodule.recycle.listener
 *  @文件名:   RViewCreate
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/21 3:02 PM
 *  @描述：    创建RViewHelper所需要的数据,
 */
public interface RViewCreate<T> {
    SwipeRefreshLayout createSwipeRefresh();//创建下拉更新数据

    int[] colorResIds();//下拉刷新颜色

    RecyclerView createRecycleView();//创建/设置RecycleView

    RViewAdapter<T> createRecycleViewAdapter();//创建适配器

    RecyclerView.LayoutManager createLayoutManager();//设置RecycleView layoutmanager

    RecyclerView.ItemDecoration createItemDecoration();//RecycleView分割线

    int startPageNum();//开始页码


    int pageSize();//一页多少item

    boolean isSupportPaging();//是否支持分页
}

package com.ancely.netan.recycle;

import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;

/*
 *  @项目名：  ModlerApp
 *  @包名：    luckly.ancely.com.commoentmodule.recycle
 *  @文件名:   SwipeRefreshHelper
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/21 2:48 PM
 *  @描述：    下拉刷新帮助类
 */
public class SwipeRefreshHelper {

    private SwipeRefreshLayout swipeRefresh;
    private SwipeRefreshListener swipeRefreshListener;

    private SwipeRefreshHelper(SwipeRefreshLayout swipeRefresh, @ColorRes int... colorResIds) {
        this.swipeRefresh = swipeRefresh;
        init(colorResIds);
    }

    private void init(@ColorRes int... colorResIds) {
        if (colorResIds == null || colorResIds.length == 0) {
            swipeRefresh.setColorSchemeResources(android.R.color.holo_orange_dark, android.R.color.holo_green_dark, android.R.color.holo_blue_dark);

        } else {
            swipeRefresh.setColorSchemeResources(colorResIds);
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (swipeRefreshListener != null) {
                    swipeRefreshListener.onRefresh();
                }
            }
        });
    }

    public interface SwipeRefreshListener {

        void onRefresh();
    }

    static SwipeRefreshHelper createSwipRefreshHelper(SwipeRefreshLayout swipeRefresh, @ColorRes int... colorResIds) {
        return new SwipeRefreshHelper(swipeRefresh, colorResIds);
    }

    public void setSwipeRefreshListener(SwipeRefreshListener swipeRefreshListener) {
        this.swipeRefreshListener = swipeRefreshListener;
    }
}

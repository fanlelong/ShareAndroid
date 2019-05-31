package com.ancely.netan.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.ancely.netan.request.mvvm.BaseViewModel;

/*
 *  @项目名：  SharingTechnology
 *  @包名：    com.ancely.netan.base
 *  @文件名:   LazyModelFragment
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/31 11:12 AM
 *  @描述：    懒加载Fragment
 */
public abstract class LazyModelFragment<VM extends BaseViewModel<T>, T> extends BaseModelFragment {
    /**
     * 是否已经初始化结束
     */
    private boolean isPrepare;

    private boolean isFirstLoad;//是否只加载一次数据

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLazyLoad(true);
        isPrepare = true;
        isFirstLoad = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 创建时要判断是否已经显示给用户，加载数据
        onVisibleToUser();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 显示状态发生变化
        onVisibleToUser();
    }

    /**
     * 判断是否需要加载数据
     */
    private void onVisibleToUser() {
        // 如果已经初始化完成，并且显示给用户
        if (isPrepare && getUserVisibleHint()) {
            if (isFirstLoad) {//判断是否只加载一次
                isPrepare = false;
            }
            loadData();
        }
    }

    public void setFirstLoad() {
        isPrepare = false;
    }
}

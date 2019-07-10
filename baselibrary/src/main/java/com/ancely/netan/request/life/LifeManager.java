package com.ancely.netan.request.life;

/*
 *  @项目名：  BaseMvp
 *  @包名：    com.ancely.rxjava.life
 *  @文件名:   LifeManager
 *  @创建者:   fanlelong
 *  @创建时间:  2018/8/28 下午1:55
 */


import com.ancely.netan.network.NetChangerManager;
import com.ancely.netan.request.mvvm.ModelP;

import java.util.ArrayList;
import java.util.List;

public class LifeManager implements LifecycleListener {

    private ActivityFragmentLifecycle mLifecycle;
    private final List<ModelP> mPresenters = new ArrayList<>();

    LifeManager(ActivityFragmentLifecycle lifecycle, ModelP presenter) {
        mLifecycle = lifecycle;
        mPresenters.add(presenter);
        mLifecycle.addListener(this);
        if (presenter.isOpenNetChanger())
            NetChangerManager.getDefault().registerObserver(presenter);

    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroy() {
        mLifecycle.removeListener(this);
        for (ModelP presenter : mPresenters) {
            if (presenter.isOpenNetChanger()) {
                NetChangerManager.getDefault().unRegisterObserver(presenter);
            }
            presenter.unDisposable();
        }
        mPresenters.clear();
    }

    void addPresenter(ModelP presenter) {
        mPresenters.add(presenter);
    }
}

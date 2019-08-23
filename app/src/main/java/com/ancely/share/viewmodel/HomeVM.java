package com.ancely.share.viewmodel;

import android.arch.lifecycle.MediatorLiveData;

import com.ancely.netan.request.mvvm.bean.ResponseBean;
import com.ancely.share.base.BaseResultVM;
import com.ancely.share.base.HttpResult;
import com.ancely.share.bean.HomeBanner;
import com.ancely.share.bean.HomeBean;
import com.ancely.share.bean.HomeCollectBean;

import java.util.List;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.viewmodel
 *  @文件名:   HomeVM
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/2 4:10 PM
 *  @描述：    TODO
 */
public class HomeVM extends BaseResultVM<HomeBean> {

    public MediatorLiveData<List<HomeBanner>> getHomeBanner() {

        if (null == homeBannle) {
            homeBannle = new MediatorLiveData<>();
        }
        return homeBannle;
    }

    public MediatorLiveData<HomeCollectBean> getColleclLiveData() {

        if (null == colleclLiveData) {
            colleclLiveData = new MediatorLiveData<>();
        }
        return colleclLiveData;
    }

    private MediatorLiveData<List<HomeBanner>> homeBannle;
    private MediatorLiveData<HomeCollectBean> colleclLiveData;


    @Override
    public void hanlerDataRequestSuccess(ResponseBean<HttpResult<HomeBean>> t) {

    }
}

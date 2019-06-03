package com.ancely.share.viewmodel;

import android.arch.lifecycle.MediatorLiveData;

import com.ancely.netan.request.mvvm.BaseViewModel;
import com.ancely.share.base.HttpResult;
import com.ancely.share.bean.HomeBanner;
import com.ancely.share.bean.HomeBean;

import java.util.List;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.viewmodel
 *  @文件名:   HomeVM
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/2 4:10 PM
 *  @描述：    TODO
 */
public class HomeVM extends BaseViewModel<HttpResult<HomeBean>> {

    public MediatorLiveData<List<HomeBanner>> getHomeBanner() {

        if (null == homeBannle) {
            homeBannle = new MediatorLiveData<>();
        }
        return homeBannle;
    }

    private MediatorLiveData<List<HomeBanner>> homeBannle;


}

package com.ancely.share.model;

import android.support.v4.app.FragmentActivity;

import com.ancely.netan.request.mvvm.BaseViewModel;
import com.ancely.share.ShareApi;
import com.ancely.share.base.BaseModelP;
import com.ancely.share.base.HttpResult;
import com.ancely.share.bean.HotTipsBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.model
 *  @文件名:   HotTipsModelP
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/5 4:28 PM
 *  @描述：    TODO
 */
public class HotTipsModelP extends BaseModelP<List<HotTipsBean>> {

    public HotTipsModelP(FragmentActivity activity, Class<? extends BaseViewModel<HttpResult<List<HotTipsBean>>>> clazz) {
        super(activity, clazz);
    }

    @Override
    protected Observable<HttpResult<List<HotTipsBean>>> getObservable(ShareApi request, Map<String, Object> map, int flag) {
        return request.getHotTips();
    }
}

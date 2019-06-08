package com.ancely.share.model;

import android.support.v4.app.FragmentActivity;

import com.ancely.netan.request.mvvm.bean.ResponseBean;
import com.ancely.share.ShareApi;
import com.ancely.share.ShareApplication;
import com.ancely.share.base.BaseModelP;
import com.ancely.share.base.BaseResultVM;
import com.ancely.share.base.HttpResult;
import com.ancely.share.bean.HotTipsBean;
import com.ancely.share.database.AppDatabase;

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

    public HotTipsModelP(FragmentActivity activity, Class<? extends BaseResultVM<List<HotTipsBean>>> clazz) {
        super(activity, clazz);
    }

    @Override
    protected Observable<HttpResult<List<HotTipsBean>>> getObservable(ShareApi request, Map<String, Object> map, int flag) {
        return request.getHotTips();
    }

    @Override
    public boolean hanlerDataRequestSuccess(ResponseBean<HttpResult<List<HotTipsBean>>> responseBean) {
        AppDatabase.getInstance(ShareApplication.getInstance()).getHotTipsDao().deleteHotTips();
        AppDatabase.getInstance(ShareApplication.getInstance()).getHotTipsDao().insertAll(responseBean.body.getData());
        return super.hanlerDataRequestSuccess(responseBean);
    }
}

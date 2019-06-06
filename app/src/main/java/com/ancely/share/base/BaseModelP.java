package com.ancely.share.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.ancely.netan.request.mvvm.ModelP;
import com.ancely.netan.request.mvvm.bean.ResponseBean;
import com.ancely.share.ShareApi;

import org.jetbrains.annotations.NotNull;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.base
 *  @文件名:   BaseModelP
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/31 8:14 PM
 */
public abstract class BaseModelP<T> extends ModelP<HttpResult<T>, ShareApi> {


    public BaseModelP(@NotNull Fragment fragment, Class<? extends BaseResultVM<T>> clazz) {
        super(fragment, clazz);
    }

    public BaseModelP(FragmentActivity activity, Class<? extends BaseResultVM<T>> clazz) {
        super(activity, clazz);
    }

    @Override
    protected Class<ShareApi> getClazz() {
        return ShareApi.class;
    }

    @Override
    public void showProgress(int flag) {

    }

    @Override
    public void hideProgress(int flag) {

    }

    @Override
    public boolean hanlerDataRequestSuccess(ResponseBean<HttpResult<T>> responseBean) {
        HttpResult<T> body = responseBean.body;
        if (body.getErrorCode() != 0) {
            accessError(body.getErrorCode(), body.getErrorMsg(), responseBean.flag, responseBean.isShowLoading);
            return true;
        }
        return super.hanlerDataRequestSuccess(responseBean);
    }
}

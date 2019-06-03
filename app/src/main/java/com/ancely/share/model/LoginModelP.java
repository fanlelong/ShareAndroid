package com.ancely.share.model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.ancely.netan.request.mvvm.BaseViewModel;
import com.ancely.share.ShareApi;
import com.ancely.share.base.BaseModelP;
import com.ancely.share.base.HttpResult;
import com.ancely.share.bean.LoginBean;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import io.reactivex.Observable;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.model
 *  @文件名:   LoginModelP
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/31 8:14 PM
 *  @描述：    登陆
 */
public class LoginModelP extends BaseModelP<LoginBean> {


    public LoginModelP(@NotNull Fragment fragment, Class<? extends BaseViewModel<HttpResult<LoginBean>>> clazz) {
        super(fragment, clazz);
    }

    public LoginModelP(FragmentActivity activity, Class<? extends BaseViewModel<HttpResult<LoginBean>>> clazz) {
        super(activity, clazz);
    }

    @Override
    protected Observable<HttpResult<LoginBean>> getObservable(ShareApi request, Map<String, Object> params, int flag) {
        return request.login(params);

    }


}

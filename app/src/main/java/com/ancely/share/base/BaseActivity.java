package com.ancely.share.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ancely.netan.base.BaseModelActivity;
import com.ancely.share.ShareApplication;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.base
 *  @文件名:   BaseActivity
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/6 10:58 AM
 *  @描述：    TODO
 */
public abstract class BaseActivity<VM extends BaseResultVM<T>, T> extends BaseModelActivity<VM, HttpResult<T>> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

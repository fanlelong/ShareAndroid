package com.ancely.netan.base;

import com.ancely.netan.request.mvvm.bean.RequestErrBean;
import com.ancely.netan.request.mvvm.bean.ResponseBean;

public interface iBaesView<T> {
    void showloading(int flag);

    void hideLoading(int flag);

    void accessError(RequestErrBean errBean);

    void accessSuccess(ResponseBean<T> responseBean);

    void accessMoreSuccess(ResponseBean<T> responseBean);

    boolean isNeedCheckNetWork();//是否需要检查网络状态

    void resevierRequest();//重新请求数据

}
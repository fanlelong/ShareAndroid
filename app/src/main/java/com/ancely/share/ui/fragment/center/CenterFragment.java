package com.ancely.share.ui.fragment.center;

import com.ancely.netan.base.BaseModelFragment;
import com.ancely.netan.request.mvvm.bean.ResponseBean;
import com.ancely.share.viewmodel.CenterVM;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.ui.fragment.capital
 *  @文件名:   CenterFragment
 *  @创建者:   fanlelong
 *  @创建时间:  2019/7/25 2:20 PM
 *  @描述：    TODO
 */
public class CenterFragment extends BaseModelFragment<CenterVM, String> {

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected Class<CenterVM> initClazz() {
        return CenterVM.class;
    }

    @Override
    public boolean isNeedCheckNetWork() {
        return false;
    }

    @Override
    public void accessSuccess(ResponseBean<String> responseBean) {
    }
}

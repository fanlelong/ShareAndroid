package com.ancely.netan.base;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ancely.netan.event.NetworkChangeEvent;
import com.ancely.netan.request.mvvm.BaseViewModel;
import com.ancely.netan.request.mvvm.bean.RequestErrBean;
import com.ancely.netan.request.mvvm.bean.ResponseBean;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/*
 *  @项目名：  SharingTechnology
 *  @包名：    com.ancely.netan.base
 *  @文件名:   BaseModelFragment
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/31 11:01 AM
 *  @描述：    基类Fragment
 */
public abstract class BaseModelFragment<VM extends BaseViewModel<T>, T> extends Fragment implements View.OnClickListener, iBaesView<T> {

    private boolean isLazyLoad;
    private View mContentView;
    protected Context mContext;
    private VM mViewModel;
    public boolean currentNetStatus = false;//当前的网络连接状态
    public boolean isResevierrequest;//是否请求出现错误标记

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(getContentView(), container, false);
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            Type claz = pType.getActualTypeArguments()[0];
            if (claz instanceof Class) {
                Class<VM> clazz = (Class<VM>) claz;
                mViewModel = ViewModelProviders.of(this).get(clazz);
                initObserveDatas();
            }
        }

        return mContentView;

    }

    private void initObserveDatas() {
        mViewModel.getErrorLiveData().observe(this, this::accessError);
        mViewModel.getResultLiveData().observe(this, this::accessSuccess);
        mViewModel.getMoreLiveData().observe(this, this::accessMoreSuccess);
        mViewModel.getShowLoadingLiveData().observe(this, this::showloading);
        mViewModel.getShowLoadingLiveData().observe(this, this::hideLoading);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initEvent();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (!isLazyLoad) {
            loadData();
        }
        super.onActivityCreated(savedInstanceState);
    }

    public void setLazyLoad(boolean lazyLoad) {
        isLazyLoad = lazyLoad;
    }

    protected View findViewById(int id) {
        return mContentView.findViewById(id);
    }

    protected abstract void loadData();

    protected abstract void initView();

    protected abstract void initEvent();

    protected abstract int getContentView();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangeEvent(NetworkChangeEvent event) {
        if (isNeedCheckNetWork()) return;
        if ((currentNetStatus && event.isConnected) || (!currentNetStatus && !event.isConnected)) {
            return;
        }
        if (isResevierrequest && event.isConnected) {
            resevierRequest();
        }
        currentNetStatus = event.isConnected;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onClick(View v) {
    }

    @Override
    public void showloading(int flag) {

    }

    @Override
    public void hideLoading(int flag) {

    }

    @Override
    public void accessError(RequestErrBean errBean) {
        isResevierrequest = true;
    }

    /**
     * 请求数据成功
     */
    @Override
    public void accessSuccess(ResponseBean<T> responseBean) {
        isResevierrequest = false;
    }

    /**
     * 请求加载更多数据居功
     */
    @Override
    public void accessMoreSuccess(ResponseBean<T> responseBean) {
        isResevierrequest = false;
    }

    /**
     * 重新加载数据
     */
    @Override
    public void resevierRequest() {

    }
}

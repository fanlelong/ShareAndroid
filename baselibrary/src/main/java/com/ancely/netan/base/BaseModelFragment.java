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
import android.widget.Toast;

import com.ancely.netan.network.Net;
import com.ancely.netan.network.NetType;
import com.ancely.netan.receiver.NetWorkConnectReceiver;
import com.ancely.netan.request.mvvm.BaseViewModel;
import com.ancely.netan.request.mvvm.bean.RequestErrBean;
import com.ancely.netan.request.mvvm.bean.ResponseBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

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
    protected VM mViewModel;
    protected Map<String, Object> mParams = new HashMap<>();
    protected boolean mIsFirstInto;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mViewModel.getResultLiveData().observe(this, responseBean -> {
            mIsFirstInto = true;
            accessSuccess(responseBean);
        });
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

    protected <V extends View> V findViewById(int id) {
        return (V) mContentView.findViewById(id);
    }

    protected abstract void loadData();

    protected abstract void initView();

    protected abstract void initEvent();

    protected abstract int getContentView();

    @Override
    public void onDestroy() {
        super.onDestroy();
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
        Toast.makeText(mContext, errBean.msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 请求数据成功
     */
    @Override
    public void accessSuccess(ResponseBean<T> responseBean) {
    }

    /**
     * 请求加载更多数据居功
     */
    @Override
    public void accessMoreSuccess(ResponseBean<T> responseBean) {
    }
}

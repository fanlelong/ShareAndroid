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

import com.ancely.netan.request.mvvm.BaseViewModel;
import com.ancely.netan.request.mvvm.bean.RequestErrBean;
import com.ancely.netan.request.mvvm.bean.ResponseBean;

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
    protected View mContentView;
    protected Context mContext;
    protected VM mViewModel;
    protected Map<String, Object> mParams = new HashMap<>();//请求参数
    protected boolean mIsFirstInto;//是否是第一次进入视图

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

        Class<VM> clazz = initClazz();
        if (clazz != null) {
            mViewModel = ViewModelProviders.of(this).get(clazz);
            initObserveDatas();
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

    protected abstract void loadData();//加载数据

    protected abstract void initView();//初始化视图

    protected abstract void initEvent();//设置点击事件

    protected abstract int getContentView();//调车容器View

    protected abstract Class<VM> initClazz();

    @Override
    public void onClick(View v) {
    }

    @Override
    public void showloading(int flag) {

    }

    @Override
    public void hideLoading(int flag) {

    }

    /**
     * 请求错误
     *
     * @param errBean 错误信息bean类
     */
    @Override
    public void accessError(RequestErrBean errBean) {
        Toast.makeText(mContext, errBean.msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 请求数据成功
     *
     * @param responseBean 请求成功信息bean类
     */
    @Override
    public void accessSuccess(ResponseBean<T> responseBean) {
    }

    /**
     * 请求加载更多数据居功
     *
     * @param responseBean 请求加载更多成功信息bean类
     */
    @Override
    public void accessMoreSuccess(ResponseBean<T> responseBean) {
    }
}

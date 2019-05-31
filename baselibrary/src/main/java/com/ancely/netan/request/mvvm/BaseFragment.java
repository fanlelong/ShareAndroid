package com.ancely.netan.request.mvvm;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ancely.netan.request.mvvm.bean.RequestErrBean;
import com.ancely.netan.request.mvvm.bean.ResponseBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/*
 *  @项目名：  SharingTechnology
 *  @包名：    com.ancely.rxjava.mvvm
 *  @文件名:   ModelPFragment
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/17 3:15 PM
 */
public abstract class BaseFragment<VM extends BaseViewModel<T>, T> extends Fragment implements View.OnClickListener, iBaesView<T> {


    private boolean isLazyLoad;
    private View mContentView;
    //    private List<Presenter> mPresenters;
    private Context mContext;
    private VM mViewModel;
    private Class<VM> clazz;

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
        mContentView = inflater.inflate(setLayoutId(), container, false);
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            Type claz = pType.getActualTypeArguments()[0];
            if (claz instanceof Class) {
                this.clazz = (Class<VM>) claz;
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

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initListener();
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

    protected abstract void init();

    protected abstract void initListener();

    protected abstract int setLayoutId();

    public void startBActivity(Intent intent) {
        super.startActivity(intent);
        if (getActivity() != null) {
//            getActivity().overridePendingTransition(R.anim.anim_window_in_right, R.anim.anim_window_out_left);
        }
    }

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

    }

    @Override
    public void accessSuccess(ResponseBean<T> responseBean) {

    }

    @Override
    public void accessMoreSuccess(ResponseBean<T> responseBean) {

    }
}

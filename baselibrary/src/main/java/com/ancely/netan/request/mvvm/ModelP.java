package com.ancely.netan.request.mvvm;

/*
 *  @项目名：  NewCalendar
 *  @包名：    calendar.ancyel.com.newcalendar.retrofit.base
 *  @创建者:   fanlelong
 *  @创建时间:  2018/8/7 下午2:42
 *  @描述：    基类中介层
 */


import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.ancely.netan.NetWorkManager;
import com.ancely.netan.network.Net;
import com.ancely.netan.network.NetType;
import com.ancely.netan.request.exception.ApiException;
import com.ancely.netan.request.mvvm.bean.RequestErrBean;
import com.ancely.netan.request.mvvm.bean.ResponseBean;
import com.ancely.netan.utils.NetUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class ModelP<T, R> implements IBaseModelP<T> {
    public static final int IS_LOADING_MORE_DATA = 2;//加载更多数据
    private CompositeDisposable mDisposable;
    private R mRequest;
    private BaseViewModel<T> mBaseViewModel;
    private Map<String, Object> params;
    private int flag;//用来判断不同请求的标识
    private static final String TAG = "AncelyModelP";
    private boolean isShowLoading;
    private int retryCount = 1;//请求重试次数
    private int retryTime = 1000;//每隔多少请求重试一次,毫秒

    /**
     * 设置失败重连次数
     *
     * @param retryCount 重连次数
     * @param retryTime  每次重连后,再加这个时间然后再重连
     */
    public void setRetryCount(int retryCount, int retryTime) {
        this.retryCount = retryCount;
        if (retryTime >= 0) {
            this.retryTime = retryTime;
        }
    }


    public ModelP(@NonNull Fragment fragment, Class<? extends BaseViewModel<T>> clazz) {
        mBaseViewModel = ViewModelProviders.of(fragment).get(clazz);
        registerObserver(mBaseViewModel, fragment);
        NetWorkManager.getInstance().getRequestManagerRetriever().get(fragment, this);
        mRequest = NetWorkManager.getInstance().getRetrofit().create(getClazz());
    }

    public ModelP(FragmentActivity activity, Class<? extends BaseViewModel<T>> clazz) {

        mBaseViewModel = ViewModelProviders.of(activity).get(clazz);
        registerObserver(mBaseViewModel, activity);
        NetWorkManager.getInstance().getRequestManagerRetriever().get(activity, this);
        mRequest = NetWorkManager.getInstance().getRetrofit().create(getClazz());
    }

    private void registerObserver(BaseViewModel<T> baseViewModel, Fragment fragment) {
        baseViewModel.getShowLoadingLiveData().observe(fragment, this::showProgress);
        baseViewModel.getHideLoadingLiveData().observe(fragment, this::hideProgress);

    }

    private void registerObserver(BaseViewModel<T> baseViewModel, FragmentActivity activity) {
        baseViewModel.getShowLoadingLiveData().observe(activity, this::showProgress);
        baseViewModel.getHideLoadingLiveData().observe(activity, this::hideProgress);
    }

    protected abstract Class<R> getClazz();

    @Override
    public void disposable(Disposable s) {
        if (this.mDisposable == null) {
            this.mDisposable = new CompositeDisposable();
        }
        this.mDisposable.add(s);
    }


    @Override
    public void unDisposable() {
        if (this.mDisposable != null && mDisposable.isDisposed()) {
            this.mDisposable.dispose();
        }
    }


    /**
     * @param isAddRetry 无网-->有网  是否需要重新请求: 有些场景是不需要的 比如点击收藏
     */
    public void startRequestService(Map<String, Object> params, int flag, boolean isShowLoading, boolean isAddRetry) {

        if (params == null) {
            params = new HashMap<>();
        }
        start(params, flag, isShowLoading);
        Observable<T> netObservable = getObservable(mRequest, params, flag);
        if (netObservable == null) {
            throw new NullPointerException("the method of getObservable  can not return null");
        }


        if (isAddRetry) {
            this.params = params;
            this.flag = flag;
            this.isShowLoading = isShowLoading;
        }
        sendRequestToServer(mRequest, netObservable, flag, params, isShowLoading, isAddRetry);
    }

    /**
     * 重新请求当次失败的请求 比如请求失败后的一个点击请求事件
     */
    public void rerequest() {
        startRequestService(params, flag, isShowLoading);
    }

    @Net
    public void rerequest(NetType netType) {
        if (!netRequestFailed) return;
        switch (netType) {
            case WIFI:
            case CMWAP:
            case CMNET:
                rerequest();
                break;
        }
    }

    /**
     * 请求之前可以做一些操作
     */
    public void start(Map<String, Object> map, int flag, boolean isShowLoading) {
    }

    private void sendRequestToServer(R request, Observable<T> netObservable, int flag, Map<String, Object> params, boolean showLoading, boolean isRetry) {
        Observable<T> cacheObservable = Observable.create(emitter -> handlerFirstObservable(emitter, request, params, flag));

        Observable<T> concat = Observable.concat(cacheObservable, netObservable);

        ResponseBean<T> responseBean = new ResponseBean<>();
        responseBean.flag = flag;
        responseBean.isShowLoading = showLoading;
        responseBean.isRetry = isRetry;
        if (!NetUtils.isConnected(NetWorkManager.getInstance().getContext())) {
            accessError(404, "网络异常,请尝试切换其它网络..", responseBean);
            return;
        }
        if (showLoading) {
            mBaseViewModel.getShowLoadingLiveData().postValue(flag);
        }
        disposable(concat.retryWhen(new RetryWithDelay(retryCount, retryTime))
                .compose(getTransformer())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(t -> {
                    if (t == null) {
                        accessError(-1, "request data error ", responseBean);
                        return;
                    }
                    responseBean.body = t;
                    //判断是不是先要内部操作数据,默认不进行数据重组,如果自己重组了数据,则应该对dialog进行显示和隐藏,以级是否是加载更多数据
                    boolean handlerDataFlag = hanlerDataRequestSuccess(responseBean);
                    if (handlerDataFlag) {
                        return;
                    }

                    if (flag == ModelP.IS_LOADING_MORE_DATA) {
                        accessMoreSuccess(responseBean, flag, isShowLoading);
                    } else {
                        accessSucceed(responseBean, flag, isShowLoading);
                    }

                }, throwable -> {
                    if (throwable instanceof ApiException) {
                        ApiException exception = (ApiException) throwable;
                        accessError(exception.getCode(), (exception).getDisplayMessage(), responseBean);
                    }
                }));
    }

    public void startRequestService(Map<String, Object> map, int flag, boolean isAddRetry) {

        this.startRequestService(map, flag, true, isAddRetry);

    }

    public void startRequestService(Map<String, Object> map, int flag) {

        this.startRequestService(map, flag, true, true);

    }

    public void startRequestService(Map<String, Object> map, boolean isAddRetry) {
        this.startRequestService(map, 1, isAddRetry);
    }

    public void startRequestService(Map<String, Object> map) {
        this.startRequestService(map, 1);
    }

    public void startRequestService() {
        this.startRequestService(null, 1, true);
    }

    protected abstract Observable<T> getObservable(R request, Map<String, Object> map, int flag);


    @Override
    public void accessSucceed(ResponseBean<T> responseBean, int flag, boolean isShowLoading) {

        if (isShowLoading) {
            mBaseViewModel.getHideLoadingLiveData().setValue(flag);
        }
        netRequestFailed = false;
        mBaseViewModel.getResultLiveData().setValue(responseBean);
    }

    @Override
    public void accessMoreSuccess(ResponseBean<T> responseBean, int flag, boolean isShowLoading) {
        if (isShowLoading) {
            mBaseViewModel.getHideLoadingLiveData().setValue(flag);
        }
        netRequestFailed = false;
        mBaseViewModel.getMoreLiveData().setValue(responseBean);
    }

    private boolean netRequestFailed;//网络请求失败标志

    @Override
    public void accessError(int code, String errorMsg, ResponseBean<T> responseBean) {

        if (isShowLoading) {
            mBaseViewModel.getHideLoadingLiveData().setValue(flag);
        }
        if (responseBean.isRetry) netRequestFailed = true;
        mBaseViewModel.getErrorLiveData().setValue(new RequestErrBean(code, errorMsg, flag));
    }

    public abstract void showProgress(int flag);

    public abstract void hideProgress(int flag);

    /**
     * 请求前,可做一些相应的操作
     */
    public void handlerFirstObservable(ObservableEmitter<T> emitter, R request, Map<String, Object> params, int flag) {
        emitter.onComplete(); // 只有执行onComplete才会进入到另一个
    }

    /**
     * 请求成功后,可对请求回来的数据进行一些操作
     *
     * @param responseBean 组装的数据
     * @return true: 自己处理
     */
    public boolean hanlerDataRequestSuccess(ResponseBean<T> responseBean) {
        return false;
    }

    public BaseViewModel<T> getBaseViewModel() {
        return mBaseViewModel;
    }

    @Override
    public ObservableTransformer<T, T> getTransformer() {
        return ResultTransformer.handleResult();
    }

    /**
     * 是否开启网络改变监听
     *
     * @return 默认为true 开启
     */
    public boolean isOpenNetChanger() {
        return true;
    }
}

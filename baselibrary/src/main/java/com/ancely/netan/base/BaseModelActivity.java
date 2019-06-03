package com.ancely.netan.base;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.ancely.netan.event.NetworkChangeEvent;
import com.ancely.netan.receiver.NetWorkConnectReceiver;
import com.ancely.netan.request.mvvm.BaseViewModel;
import com.ancely.netan.request.mvvm.bean.RequestErrBean;
import com.ancely.netan.request.mvvm.bean.ResponseBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/*
 *  @项目名：  SharingTechnology
 *  @包名：    com.ancely.netan.base
 *  @文件名:   BaseActivity
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/31 10:26 AM
 *  @描述：    请求Activity基类
 */
public abstract class BaseModelActivity<VM extends BaseViewModel<T>, T> extends AppCompatActivity implements View.OnClickListener, iBaesView<T> {
    protected Context mContext;
    private boolean mIsFirstInto;//是否第一次进入界面
    private NetWorkConnectReceiver mNetWorkConnectReceiver;
    private boolean mCurrentNetStatus = true;//当前的网络连接状态
    public boolean isResevierrequest;//是否请求出现错误标记

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (isFullScreen()) {
            if (Build.VERSION.SDK_INT >= 21) {
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else {
                requestWindowFeature(Window.FEATURE_NO_TITLE);
            }
        }

        super.onCreate(savedInstanceState);
        mContext = this;
        EventBus.getDefault().register(this);
        registerReceiver();
        int layoutId = getContentView();
        if (layoutId >= 0) {
            setContentView(layoutId);
        }
        initView();
        initEvent();
        initDatas();
        Class<VM> clazz = initClazz();
        if (clazz != null) {
            mViewModel = ViewModelProviders.of(this).get(clazz);
            initObserver();
        }
    }

    protected VM mViewModel;

    /**
     * 初始化请求数据
     */
    private void initObserver() {
        mViewModel.getResultLiveData().observe(this, this::accessSuccess);
        mViewModel.getMoreLiveData().observe(this, this::accessMoreSuccess);
        mViewModel.getErrorLiveData().observe(this, this::accessError);
        mViewModel.getShowLoadingLiveData().observe(this, this::showloading);
        mViewModel.getShowLoadingLiveData().observe(this, this::hideLoading);
    }

    protected abstract int getContentView();//展示布局

    protected abstract Class<VM> initClazz();

    protected abstract void initDatas();

    protected abstract void initEvent();

    protected abstract void initView();

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mNetWorkConnectReceiver = new NetWorkConnectReceiver();
        registerReceiver(mNetWorkConnectReceiver, filter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangeEvent(NetworkChangeEvent event) {
        if (isNeedCheckNetWork()) return;

        if (!mIsFirstInto) {
            hasNetWork(event.isConnected);
            mIsFirstInto = true;
        }

        if ((mCurrentNetStatus && event.isConnected) || (!mCurrentNetStatus && !event.isConnected)) {
            return;
        }
        mCurrentNetStatus = event.isConnected;
        if (isResevierrequest && event.isConnected) {
            resevierRequest();
        }
        hasNetWork(event.isConnected);
    }

    /**
     * 每次网络变化都会走到这个就去
     *
     * @param isConnected true: 有网;
     */
    public void hasNetWork(boolean isConnected) {

    }

    /**
     * 重新请求数据
     */
    public void resevierRequest() {
    }


    //是否显示全屏
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mNetWorkConnectReceiver != null) {
            unregisterReceiver(mNetWorkConnectReceiver);
        }
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

    @Override
    public void accessSuccess(ResponseBean<T> responseBean) {
        isResevierrequest = false;
    }

    @Override
    public void accessMoreSuccess(ResponseBean<T> responseBean) {
        isResevierrequest = false;
    }
}

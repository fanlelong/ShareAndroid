package com.ancely.netan.base;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ancely.netan.R;
import com.ancely.netan.network.Net;
import com.ancely.netan.network.NetChangerManager;
import com.ancely.netan.network.NetType;
import com.ancely.netan.receiver.NetWorkConnectReceiver;
import com.ancely.netan.request.mvvm.BaseViewModel;
import com.ancely.netan.request.mvvm.bean.RequestErrBean;
import com.ancely.netan.request.mvvm.bean.ResponseBean;
import com.ancely.netan.utils.NetUtils;

/*
 *  @项目名：  SharingTechnology
 *  @包名：    com.ancely.netan.base
 *  @文件名:   BaseActivity
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/31 10:26 AM
 *  @描述：    请求Activity基类
 */
public abstract class BaseModelActivity<VM extends BaseViewModel<T>, T> extends AppCompatActivity implements View.OnClickListener, iBaesView<T> {
    public Context mContext;
    protected boolean mIsFirstInto;//是否第一次进入界面
    private NetWorkConnectReceiver mNetWorkConnectReceiver;
    private boolean mCurrentNetStatus = true;//当前的网络连接状态
    public boolean isResevierrequest;//是否请求出现错误标记
    private View mTipView;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager.LayoutParams mToastLayoutParams;
    private WindowManager mWindowManager;

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
            if (isFullScreen() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        super.onCreate(savedInstanceState);
        mContext = this;
//        registerReceiver();
        NetChangerManager.getDefault().registerObserver(this);
        initTipView();
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
        onNetworkChangeEvent(NetUtils.getNetType());
    }

    protected VM mViewModel;

    /**
     * 初始化请求数据
     */
    private void initObserver() {
        mViewModel.getErrorLiveData().observe(this, errBean -> {
            isResevierrequest = true;
            accessError(errBean);
        });
        mViewModel.getResultLiveData().observe(this, responseBean -> {
            isResevierrequest = false;
            mIsFirstInto = true;
            accessSuccess(responseBean);
        });
        mViewModel.getMoreLiveData().observe(this, responseBean -> {
            isResevierrequest = false;
            accessMoreSuccess(responseBean);
        });
        mViewModel.getShowLoadingLiveData().observe(this, this::showloading);
        mViewModel.getShowLoadingLiveData().observe(this, this::hideLoading);
    }

    protected abstract int getContentView();//展示布局

    protected abstract Class<VM> initClazz();

    protected abstract void initDatas();

    protected abstract void initEvent();

    protected abstract void initView();

    private void initTipView() {
        LayoutInflater inflater = getLayoutInflater();
        mTipView = inflater.inflate(R.layout.error_work, null);
//        TextView viewById = mTipView.findViewById(R.id.error);
//        viewById.setText(getClass().getSimpleName());
        //提示View布局
        mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, PixelFormat.TRANSLUCENT);
        mToastLayoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, PixelFormat.TRANSLUCENT);
        //使用非CENTER时，可以通过设置XY的值来改变View的位置
        mLayoutParams.gravity = Gravity.TOP;
        mLayoutParams.x = 0;
        mLayoutParams.y = 0;

        mToastLayoutParams.gravity = Gravity.CENTER | Gravity.BOTTOM;
        mToastLayoutParams.y = px2dpH(200);
    }

    @Net(isManThread = true)
    public void onNetworkChangeEvent(NetType netType) {
        if (!isNeedCheckNetWork()) return;
        boolean isConnected = false;
        switch (netType) {
            case WIFI:
            case CMWAP:
            case CMNET:
                isConnected = true;
                break;
            default:
                break;
        }
        hasNetWork(isConnected);
    }

    /**
     * 每次网络变化都会走到这个就去
     *
     * @param connected true: 有网;
     */
    public void hasNetWork(boolean connected) {
        if (isNeedCheckNetWork()) {
            if (connected) {
                if (mTipView != null && mTipView.getParent() != null) {
                    mWindowManager.removeView(mTipView);
                }
            } else {
                if (mTipView.getParent() == null) {
                    mWindowManager.addView(mTipView, mLayoutParams);
                }
            }
        }
    }

    //是否显示全屏
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetChangerManager.getDefault().registerObserver(this);
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
    }

    @Override
    public void accessSuccess(ResponseBean<T> responseBean) {
    }

    @Override
    public void accessMoreSuccess(ResponseBean<T> responseBean) {
    }


    public int px2dpH(int px) {
        float scale = getResources().getDisplayMetrics().heightPixels / 720.0f;
        return (int) (px * scale + 0.5f);
    }

    @Override
    public void finish() {
        super.finish();
        if (mTipView != null && mTipView.getParent() != null) {
            mWindowManager.removeView(mTipView);
        }
    }

}

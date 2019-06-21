package com.ancely.share.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ancely.netan.base.BaseModelFragment;
import com.ancely.share.R;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.base
 *  @文件名:   BaseFragment
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/6 11:19 AM
 *  @描述：    TODO
 */
public abstract class BaseFragment<VM extends BaseResultVM<T>, T> extends BaseModelFragment<VM, HttpResult<T>> {
    protected FrameLayout progressView;
    private ProgressBar mProgressBar;
    private TextView mErrorText;

    public static final int LOADING_FLAG = 1;
    public static final int NO_LOADING_MORE_FLAG = 2;
    public static final int NETWROK_ERROR = 502;

    public boolean isResevierrequest;//是否请求出现错误标记


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        progressView = (FrameLayout) View.inflate(mContext, R.layout.progress_bar, null);
        LinearLayout linearLayout = (LinearLayout) progressView.getChildAt(0);
        mProgressBar = (ProgressBar) linearLayout.getChildAt(0);
        mErrorText = (TextView) linearLayout.getChildAt(1);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    /**
     * 改变recycelview底部view的显示
     */
    public void setProgressStatues(int loadFlag) {
        isResevierrequest = true;
        if (loadFlag == LOADING_FLAG) {//显示加载更多
            setFootViewStatus(true, "上拉加载更多..");
        } else if (loadFlag == NO_LOADING_MORE_FLAG) {//显示无更多数据
            setFootViewStatus(false, "-------------- 没有更多了 ------------");
        } else if (loadFlag == NETWROK_ERROR) {
            isResevierrequest = false;
            setFootViewStatus(false, "网络出小差了,点击重新加载...");
        }
    }

    private void setFootViewStatus(boolean flag, String s) {
        if (flag) {//显示加载更多
            mProgressBar.setVisibility(View.VISIBLE);
        } else {//显示无更多数据
            mProgressBar.setVisibility(View.GONE);
        }
        mErrorText.setText(s);
    }
    public void scrollTop(){

    }

    @Override
    protected <V extends View> V findViewById(int id) {
        return (V) mContentView.findViewById(id);
    }
}

package com.ancely.share.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ancely.netan.recycle.RViewHelper;
import com.ancely.netan.recycle.SwipeRefreshHelper;
import com.ancely.netan.recycle.listener.RViewCreate;
import com.ancely.netan.recycle.listener.RViewScrollListener;
import com.ancely.netan.request.mvvm.bean.RequestErrBean;
import com.ancely.share.R;
import com.ancely.share.utils.SizeUtils;
import com.ancely.share.views.CustomRecyclerViewDivider;

import java.util.List;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.base
 *  @文件名:   RViewFragment
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/2 3:40 PM
 */
public abstract class RViewFragment<VM extends BaseResultVM<T>, T, B> extends BaseFragment<VM, T> implements RViewCreate<B>, SwipeRefreshHelper.SwipeRefreshListener, RViewScrollListener {
    private RViewHelper<B> mHelper;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHelper = new RViewHelper.Builder<>(this, this, this).build();
        progressView.setVisibility(View.GONE);
        progressView.setOnClickListener(v -> {
            if (!isResevierrequest) {
                setProgressStatues(LOADING_FLAG);
                resevierRequest();
            }
        });
    }

    @Override
    public SwipeRefreshLayout createSwipeRefresh() {
        return null;
    }

    public RViewHelper<B> getHelper() {
        return mHelper;
    }

    @Override
    public int[] colorResIds() {
        return new int[0];
    }

    @Override
    public RecyclerView.ItemDecoration createItemDecoration() {
        return new CustomRecyclerViewDivider(mContext, LinearLayoutManager.HORIZONTAL,
                2,
                ContextCompat.getColor(mContext, R.color.color_e0e0e0),
                SizeUtils.px2dp(15),
                SizeUtils.px2dp(15));
    }

    @Override
    public int startPageNum() {
        return 0;
    }

    @Override
    public int pageSize() {
        return 20;
    }

    @Override
    public boolean isSupportPaging() {
        return true;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

    }

    @Override
    public void onLoadMoreDatas() {

    }

    public void notifyAdapterDataSetChanged(List<B> datas) {

        mHelper.notifyAdapterDataSetChanged(datas);
    }

    public void notifyLocalDataSetChanged(List<B> datas) {

        mHelper.notifyLocalDataSetChanged(datas);
    }

    protected void notifyItemChangedReomeHeader(List<B> datas) {
        mHelper.notifyItemChangedReomeHeader(datas);
    }

    protected void notifyItemChangedReomeHeaderToLocal(List<B> datas) {
        mHelper.notifyItemChangedReomeHeaderToLocal(datas);
    }

    @Override
    public void accessError(RequestErrBean errBean) {
        super.accessError(errBean);
        if (createSwipeRefresh() != null) {
            createSwipeRefresh().setRefreshing(false);
        }
        setProgressStatues(NETWROK_ERROR);

    }

    @Override
    public void showloading(int flag) {
        setProgressStatues(LOADING_FLAG);
    }

    protected void resevierRequest() {
    }
}

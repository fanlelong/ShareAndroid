package com.ancely.share.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ancely.netan.base.BaseModelFragment;
import com.ancely.netan.recycle.RViewHelper;
import com.ancely.netan.recycle.SwipeRefreshHelper;
import com.ancely.netan.recycle.listener.RViewCreate;
import com.ancely.netan.recycle.listener.RViewScrollListener;
import com.ancely.netan.request.mvvm.BaseViewModel;
import com.ancely.netan.request.mvvm.bean.RequestErrBean;

import java.util.List;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.base
 *  @文件名:   RViewFragment
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/2 3:40 PM
 */
public abstract class RViewFragment<VM extends BaseViewModel<HttpResult<T>>, T, R> extends BaseModelFragment<VM, HttpResult<T>> implements RViewCreate<R>, SwipeRefreshHelper.SwipeRefreshListener, RViewScrollListener {
    private RViewHelper<R> mHelper;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHelper = new RViewHelper.Builder<>(this, this, this).build();
    }

    @Override
    public SwipeRefreshLayout createSwipeRefresh() {
        return null;
    }

    public RViewHelper<R> getHelper() {
        return mHelper;
    }

    @Override
    public int[] colorResIds() {
        return new int[0];
    }

    @Override
    public RecyclerView.ItemDecoration createItemDecoration() {
        return null;
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

    public void notifyAdapterDataSetChanged(List<R> datas) {

        mHelper.notifyAdapterDataSetChanged(datas);
    }

    public void notifyLocalDataSetChanged(List<R> datas) {

        mHelper.notifyLocalDataSetChanged(datas);
    }

    protected void notifyItemChangedReomeHeader(List<R> article) {
        mHelper.notifyItemChangedReomeHeader(article);
    }

    protected void notifyItemChangedReomeHeaderToLocal(List<R> article) {
        mHelper.notifyItemChangedReomeHeaderToLocal(article);
    }

    @Override
    public void accessError(RequestErrBean errBean) {
        super.accessError(errBean);
        if (createSwipeRefresh() != null) {
            createSwipeRefresh().setRefreshing(false);
        }
    }
}

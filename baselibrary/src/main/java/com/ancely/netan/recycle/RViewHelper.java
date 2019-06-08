package com.ancely.netan.recycle;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ancely.netan.recycle.base.RViewAdapter;
import com.ancely.netan.recycle.listener.RViewCreate;
import com.ancely.netan.recycle.listener.RViewScrollListener;

import java.util.List;

/*
 *  @项目名：  ModlerApp
 *  @包名：    luckly.ancely.com.commoentmodule.recycle
 *  @文件名:   RViewHelper
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/21 1:48 PM
 */
public class RViewHelper<T> extends RecyclerView.OnScrollListener {

    private final SwipeRefreshLayout mSwipeRefresh;
    private final RecyclerView mRecycleView;
    private final int mStartPageNum;
    private final int mPageSize;
    private boolean mSupportPaging;
    private final RecyclerView.LayoutManager mLayoutManager;
    private final RViewAdapter<T> mRecycleViewAdapter;
    private final SwipeRefreshHelper.SwipeRefreshListener listener;
    private final RecyclerView.ItemDecoration mItemDecoration;
    private SwipeRefreshHelper mSwipRefreshHelper;
    private int mCurrentPageNum;
    private RViewScrollListener scrollListener;

    public int getmCurrentPageNum() {
        return mCurrentPageNum;
    }

    public void initPageNum() {
        mCurrentPageNum = mStartPageNum;
    }

    private RViewHelper(Builder<T> builder) {
        mSwipeRefresh = builder.create.createSwipeRefresh();
        mRecycleView = builder.create.createRecycleView();
        mStartPageNum = builder.create.startPageNum();
        mPageSize = builder.create.pageSize();
        mSupportPaging = builder.create.isSupportPaging();
        mLayoutManager = builder.create.createLayoutManager();
        mRecycleViewAdapter = builder.create.createRecycleViewAdapter();
        mItemDecoration = builder.create.createItemDecoration();
        this.scrollListener = builder.scrollListener;
        this.listener = builder.listener;
        int[] colorRes = builder.create.colorResIds();
        if (mSwipeRefresh != null) {
            if (colorRes == null) {
                mSwipRefreshHelper = SwipeRefreshHelper.createSwipRefreshHelper(mSwipeRefresh);
            } else {
                mSwipRefreshHelper = SwipeRefreshHelper.createSwipRefreshHelper(mSwipeRefresh, colorRes);
            }
        }
        init();

    }

    private void init() {
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        if (mItemDecoration != null) mRecycleView.addItemDecoration(mItemDecoration);
        if (mSwipRefreshHelper != null) {
            mSwipRefreshHelper.setSwipeRefreshListener(() -> {
                dismissSwipeRefresh(); //刷新完隐藏控件
                mCurrentPageNum = mStartPageNum;//重置页码
                if (listener != null) listener.onRefresh();
            });
        }
        mRecycleView.setAdapter(mRecycleViewAdapter);
        mRecycleView.addOnScrollListener(this);
    }

    private void dismissSwipeRefresh() {
        if (mSwipeRefresh != null && mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    public RViewAdapter<T> getAdapter() {
        return mRecycleViewAdapter;
    }

    private boolean isLoadMoreFlag;//加载更多标志

    public boolean isLoadMoreFlag() {
        return isLoadMoreFlag;
    }

    /**
     * 更新全部 包括headers and foots
     */
    public void notifyAdapterDataSetChanged(List<T> datas) {
        if (datas == null) return;
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setRefreshing(false);
        }
        isLoadMoreFlag = datas.size() >= mPageSize && mSupportPaging;
        if (mCurrentPageNum == mStartPageNum) {
            mRecycleViewAdapter.updataDatas(datas);
        } else {
            mRecycleViewAdapter.addDatas(datas);
        }
    }

    public void notifyLocalDataSetChanged(List<T> datas) {
        if (datas == null) return;
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setRefreshing(false);
        }
        isLoadMoreFlag = false;
        if (mCurrentPageNum == mStartPageNum) {
            mRecycleViewAdapter.updataDatas(datas);
        } else {
            mRecycleViewAdapter.addDatas(datas);
        }
    }

    /**
     * 不包括headers and foots
     */
    public void notifyItemChangedReomeHeader(List<T> datas) {
        if (datas == null) return;
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setRefreshing(false);
        }
        isLoadMoreFlag = datas.size() >= mPageSize && mSupportPaging;
        if (mCurrentPageNum == mStartPageNum) {
            mRecycleViewAdapter.updataRangeDatas(datas);
        } else {
            mRecycleViewAdapter.addRangeDatas(datas);
        }
    }

    public void notifyItemChangedReomeHeaderToLocal(List<T> datas) {
        if (datas == null) return;
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setRefreshing(false);
        }
        isLoadMoreFlag = false;
        if (mCurrentPageNum == mStartPageNum) {
            mRecycleViewAdapter.updataRangeDatas(datas);
        } else {
            mRecycleViewAdapter.addRangeDatas(datas);
        }
    }


    public static class Builder<T> {
        private final RViewCreate<T> create;
        private final SwipeRefreshHelper.SwipeRefreshListener listener;
        private final RViewScrollListener scrollListener;

        public Builder(RViewCreate<T> create, SwipeRefreshHelper.SwipeRefreshListener listener, RViewScrollListener scrollListener) {
            this.create = create;
            this.listener = listener;
            this.scrollListener = scrollListener;
        }

        public RViewHelper<T> build() {
            return new RViewHelper<>(this);
        }
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (scrollListener != null) scrollListener.onScrollStateChanged(recyclerView, newState);
        if (!mSupportPaging || !isLoadMoreFlag || scrollListener == null) {
            return;
        }
        boolean isBottom;
        isBottom = !recyclerView.canScrollVertically(1);
        if (isLoadMoreFlag && isBottom) {
            isLoadMoreFlag = false;
            mCurrentPageNum++;
            scrollListener.onLoadMoreDatas();
        }
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (scrollListener != null) scrollListener.onScrolled(recyclerView, dx, dy);
    }
}

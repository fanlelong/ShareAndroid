package com.ancely.share.ui.fragment.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ancely.netan.recycle.base.RViewAdapter;
import com.ancely.netan.request.mvvm.ModelP;
import com.ancely.netan.request.mvvm.bean.ResponseBean;
import com.ancely.share.R;
import com.ancely.share.adapter.ArticleAdatper;
import com.ancely.share.base.HttpResult;
import com.ancely.share.base.RViewFragment;
import com.ancely.share.bean.Article;
import com.ancely.share.bean.HomeBean;
import com.ancely.share.model.HomeModelP;
import com.ancely.share.utils.RequestCode;
import com.ancely.share.viewmodel.HomeVM;

import java.util.List;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.ui.fragment.home
 *  @文件名:   SearchListFragment
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/9 1:50 PM
 *  @描述：    TODO
 */
public class SearchListFragment extends RViewFragment<HomeVM, HomeBean, Article> {

    private SwipeRefreshLayout mFragSearchRefresh;
    private RecyclerView mFragSearchRv;
    private LinearLayoutManager mLayoutManager;
    private ArticleAdatper mAdatper;
    private HomeModelP mModelP;


    @Override
    protected void loadData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            String searchName = arguments.getString("searchName");
            if (searchName != null) {
                mParams.put("k", searchName);
            }
        }
        requestDatasFromServer(false);

    }


    /**
     * 请求服务器数据
     */
    private void requestDatasFromServer(boolean isLoadMore) {
        mFragSearchRefresh.setRefreshing(true);
        mParams.put("pageNum", getHelper().getmCurrentPageNum());
        if (isLoadMore) {
            mModelP.startRequestService(mParams, ModelP.IS_LOADING_MORE_DATA);
            return;
        }
        mModelP.startRequestService(mParams, RequestCode.SEARCH_LIST_CODE);
    }

    @Override
    protected void initView() {
        mFragSearchRefresh = findViewById(R.id.frag_search_refresh);
        mFragSearchRv = findViewById(R.id.frag_search_rv);
        mLayoutManager = new LinearLayoutManager(mContext);
        HomeBean datas = new HomeBean();
        mAdatper = new ArticleAdatper(datas);
        mAdatper.addFooterView(progressView);
        mModelP = new HomeModelP(this, HomeVM.class);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected int getContentView() {
        return R.layout.frag_search_list;
    }

    @Override
    public boolean isNeedCheckNetWork() {
        return false;
    }

    @Override
    public void onRefresh() {
        requestDatasFromServer(false);
    }

    @Override
    public RecyclerView createRecycleView() {
        return mFragSearchRv;
    }

    @Override
    public RViewAdapter createRecycleViewAdapter() {
        return mAdatper;
    }

    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return mLayoutManager;
    }

    @Override
    public SwipeRefreshLayout createSwipeRefresh() {
        return mFragSearchRefresh;
    }

    @Override
    public void onLoadMoreDatas() {
        requestDatasFromServer(true);
    }

    @Override
    public void scrollTop() {
        if (mLayoutManager.findFirstVisibleItemPosition() > pageSize()) {
            mFragSearchRv.scrollToPosition(0);
        } else {
            mFragSearchRv.smoothScrollToPosition(0);
        }
    }

    @Override
    public void accessSuccess(ResponseBean<HttpResult<HomeBean>> responseBean) {
        progressView.setVisibility(View.VISIBLE);
        List<Article> datas = responseBean.body.getData().getDatas();
        if (datas == null || datas.size() == 0 || datas.size() < pageSize()) {
            setProgressStatues(NO_LOADING_MORE_FLAG);
        }
        notifyItemChangedReomeHeader(datas);
    }

    @Override
    public void accessMoreSuccess(ResponseBean<HttpResult<HomeBean>> responseBean) {
        notifyItemChangedReomeHeader(responseBean.body.getData().getDatas());
    }
}

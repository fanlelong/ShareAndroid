package com.ancely.share.ui.fragment.home;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ancely.netan.recycle.base.RViewAdapter;
import com.ancely.netan.recycle.listener.ItemListener;
import com.ancely.netan.request.mvvm.ModelP;
import com.ancely.netan.request.mvvm.bean.ResponseBean;
import com.ancely.share.R;
import com.ancely.share.adapter.HomeAdatper;
import com.ancely.share.base.HttpResult;
import com.ancely.share.base.RViewFragment;
import com.ancely.share.bean.Article;
import com.ancely.share.bean.HomeBanner;
import com.ancely.share.bean.HomeBean;
import com.ancely.share.model.HomeModelP;
import com.ancely.share.viewmodel.HomeVM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.ui.fragment.home
 *  @文件名:   HomeFragment
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/2 3:38 PM
 *  @描述：    首页
 */
public class HomeFragment extends RViewFragment<HomeVM, HomeBean, Article> {
    private RecyclerView mFragHomeRv;
    private HomeAdatper mAdatper;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mFragHomeRefresh;
    private HomeModelP mModelP;
    private Map<String, Object> params = new HashMap<>();
    private List<HomeBanner> bannerDatas = new ArrayList<>();

    @Override
    protected void loadData() {
        requestDatasFromServer(false);
    }

    @Override
    protected void initView() {
        mFragHomeRv = findViewById(R.id.frag_home_rv);
        mFragHomeRefresh = findViewById(R.id.frag_home_refresh);
        mLayoutManager = new LinearLayoutManager(getContext());
        HomeBean datas = new HomeBean();
        mAdatper = new HomeAdatper(datas);
        mModelP = new HomeModelP(this, HomeVM.class);
        mAdatper.addHeaderView(R.layout.item_banner, bannerDatas);
        mViewModel.getHomeBanner().observe(this, new Observer<List<HomeBanner>>() {
            @Override
            public void onChanged(@Nullable List<HomeBanner> homeBanners) {
                if (homeBanners != null && homeBanners.size() > 0) {
                    bannerDatas.clear();
                    bannerDatas.addAll(homeBanners);
                    mAdatper.notifyItemChanged(0);
                }
            }
        });
    }

    @Override
    protected void initEvent() {
        mAdatper.setItemListener(new ItemListener<Article>() {
            @Override
            public void onItemClick(View view, Article entity, int position) {
                Toast.makeText(mContext, entity.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, Article entry, int position) {
                return false;
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public boolean isNeedCheckNetWork() {
        return true;
    }

    @Override
    public void onRefresh() {
        requestDatasFromServer(false);
    }

    @Override
    public RecyclerView createRecycleView() {
        return mFragHomeRv;
    }

    @Override
    public SwipeRefreshLayout createSwipeRefresh() {
        return mFragHomeRefresh;
    }

    @Override
    public RViewAdapter<Article> createRecycleViewAdapter() {
        return mAdatper;
    }

    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return mLayoutManager;
    }

    @Override
    public void accessSuccess(ResponseBean<HttpResult<HomeBean>> responseBean) {
        super.accessSuccess(responseBean);
        notifyAdapterDataSetChanged(responseBean.body.getData().getDatas());
    }

    @Override
    public void accessMoreSuccess(ResponseBean<HttpResult<HomeBean>> responseBean) {
        super.accessMoreSuccess(responseBean);
        notifyAdapterDataSetChanged(responseBean.body.getData().getDatas());
    }

    @Override
    public void onLoadMoreDatas() {
        requestDatasFromServer(true);
    }

    public void scrollToTop() {
        if (mLayoutManager.findFirstVisibleItemPosition() > 20) {
            mFragHomeRv.scrollToPosition(0);
        } else {
            mFragHomeRv.smoothScrollToPosition(0);
        }
    }

    /**
     * 请求服务器数据
     */
    private void requestDatasFromServer(boolean isLoadMore) {
        mFragHomeRefresh.setRefreshing(true);
        params.put("pageNum", getHelper().getmCurrentPageNum());
        if (isLoadMore) {
            mModelP.startRequestService(params, ModelP.IS_LOADING_MORE_DATA);
            return;
        }
        mModelP.startRequestService(params);
    }
}

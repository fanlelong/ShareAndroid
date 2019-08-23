package com.ancely.share.ui.fragment.home;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ancely.netan.recycle.base.RViewAdapter;
import com.ancely.netan.recycle.listener.ItemListener;
import com.ancely.netan.request.mvvm.ModelP;
import com.ancely.netan.request.mvvm.bean.RequestErrBean;
import com.ancely.netan.request.mvvm.bean.ResponseBean;
import com.ancely.share.R;
import com.ancely.share.adapter.ArticleAdatper;
import com.ancely.share.base.HttpResult;
import com.ancely.share.base.RViewFragment;
import com.ancely.share.bean.Article;
import com.ancely.share.bean.HomeBanner;
import com.ancely.share.bean.HomeBean;
import com.ancely.share.database.AppDatabase;
import com.ancely.share.model.HomeModelP;
import com.ancely.share.ui.activity.LoginActivity;
import com.ancely.share.utils.PreferenceUtils;
import com.ancely.share.utils.RequestCode;
import com.ancely.share.viewmodel.HomeVM;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    private ArticleAdatper mAdatper;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mFragHomeRefresh;
    private HomeModelP mModelP;
    private List<HomeBanner> bannerDatas = new ArrayList<>();

    @Override
    protected void loadData() {
        Single<List<Article>> articleAll = AppDatabase.getInstance(getContext()).articleDao().getArticleAll();
        Single<List<HomeBanner>> banners = AppDatabase.getInstance(getContext()).getBannerDao().getBanners();

        mModelP.disposable(articleAll.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::notifyItemChangedReomeHeaderToLocal));
        mModelP.disposable(banners.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(homeBanners -> {
            if (homeBanners != null && homeBanners.size() > 0) {
                bannerDatas.clear();
                bannerDatas.addAll(homeBanners);
                mAdatper.notifyItemChanged(0);
            }
        }));
        requestDatasFromServer(false);
    }

    @Override
    protected void initView() {
        mFragHomeRv = findViewById(R.id.frag_home_rv);
        mFragHomeRefresh = findViewById(R.id.frag_home_refresh);
        mLayoutManager = new LinearLayoutManager(getContext());
        HomeBean datas = new HomeBean();
        mAdatper = new ArticleAdatper(datas);
        mModelP = new HomeModelP(this, HomeVM.class);
        mAdatper.addHeaderView(R.layout.item_banner, bannerDatas);
        mAdatper.addFooterView(progressView);
        mViewModel.getHomeBanner().observe(this, homeBanners -> {
            if (homeBanners != null && homeBanners.size() > 0) {
                bannerDatas.clear();
                bannerDatas.addAll(homeBanners);
                mAdatper.notifyItemChanged(0);
            }
        });
        mViewModel.getColleclLiveData().observe(this, collectBean -> {
            if (collectBean == null) return;
            if (collectBean.errorCode == -1001) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            } else if (collectBean.errorCode != 0) {
                Toast.makeText(getContext(), collectBean.isCollect ? R.string.cancel_collecl_failed : R.string.collecl_failed, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), collectBean.isCollect ? R.string.cancel_collecl_success : R.string.collecl_success, Toast.LENGTH_SHORT).show();
                List<Article> datas1 = mAdatper.getDatas();
                datas1.get(collectBean.position - mAdatper.getHeaderViewCount()).setCollect(!collectBean.isCollect);
                mAdatper.notifyItemChanged(collectBean.position);
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
        mAdatper.setColleclClickListener((article, colleckId, position) -> {
            boolean userNmae = PreferenceUtils.getBoolean("userName");
            if (!userNmae) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            } else {
                mParams.put("id", colleckId);
                mParams.put("position", position);
                mParams.put("isCollect", article.isCollect());
                mModelP.startRequestService(mParams, RequestCode.HOME_COLLECT_CODE, false);
            }
        });
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected Class<HomeVM> initClazz() {
        return HomeVM.class;
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
        notifyItemChangedReomeHeader(responseBean.body.getData().getDatas());
    }

    @Override
    public void accessMoreSuccess(ResponseBean<HttpResult<HomeBean>> responseBean) {
        notifyItemChangedReomeHeader(responseBean.body.getData().getDatas());
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
        mParams.put("pageNum", getHelper().getmCurrentPageNum());
        if (isLoadMore) {
            mModelP.startRequestService(mParams, ModelP.IS_LOADING_MORE_DATA);
            return;
        }
        mModelP.startRequestService(mParams);
    }

    @Override
    public void accessError(RequestErrBean errBean) {
        super.accessError(errBean);
    }


//    @Net
//    public void netWork(NetType netType) {
//        switch (netType) {
//            case WIFI:
//                Log.e(getClass().getSimpleName(), "WIFI ");
//                break;
//            case CMNET:
//                Log.e(getClass().getSimpleName(), "CMNET ");
//                break;
//            case CMWAP:
//                Log.e(getClass().getSimpleName(), "CMWAP ");
//                break;
//            case NONE:
//                Log.e(getClass().getSimpleName(), "NONE ");
//                break;
//            default:
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        mAdatper.startTruning();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdatper.stopTurning();
    }

    @Override
    protected void resevierRequest() {
        mModelP.rerequest();
    }
}

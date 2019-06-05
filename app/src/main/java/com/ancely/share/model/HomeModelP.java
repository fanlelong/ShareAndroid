package com.ancely.share.model;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.ancely.netan.request.exception.ApiException;
import com.ancely.netan.request.mvvm.BaseViewModel;
import com.ancely.netan.request.mvvm.ResultTransformer;
import com.ancely.share.ShareApi;
import com.ancely.share.base.BaseModelP;
import com.ancely.share.base.HttpResult;
import com.ancely.share.bean.HomeBanner;
import com.ancely.share.bean.HomeBean;
import com.ancely.share.bean.HomeCollectBean;
import com.ancely.share.viewmodel.HomeVM;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.model
 *  @文件名:   HomeModelP
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/2 4:07 PM
 *  @描述：    TODO
 */
public class HomeModelP extends BaseModelP<HomeBean> {

    private Fragment mFragment;

    public HomeModelP(@NotNull Fragment fragment, Class<? extends BaseViewModel<HttpResult<HomeBean>>> clazz) {
        super(fragment, clazz);
        this.mFragment = fragment;
    }

    public HomeModelP(FragmentActivity activity, Class<? extends BaseViewModel<HttpResult<HomeBean>>> clazz) {
        super(activity, clazz);
    }

    @Override
    protected Observable<HttpResult<HomeBean>> getObservable(ShareApi request, Map<String, Object> map, int flag) {
        int pageNum = (int) map.get("pageNum");
        if (flag == IS_LOADING_MORE_DATA) {
            return request.getArticles((pageNum));
        }
        return Observable.zip(request.getTopArticles(), request.getArticles(pageNum), (topArticle, homeBeanHttpResult) -> {
            topArticle.getData().addAll(homeBeanHttpResult.getData().getDatas());
            homeBeanHttpResult.getData().setDatas(topArticle.getData());
            return homeBeanHttpResult;
        });
    }

    @Override
    public void handlerFirstObservable(ObservableEmitter<HttpResult<HomeBean>> emitter, ShareApi request, Map<String, Object> params, int flag) {
        if (flag == IS_LOADING_MORE_DATA) {
            super.handlerFirstObservable(emitter, request, params, flag);
            return;
        }
        if (flag == 4) {
            int id = (int) params.get("id");
            int position = (int) params.get("position");
            boolean isCollect = (boolean) params.get("isCollect");
            Observable<HttpResult<String>> collect;
            if (!isCollect) {
                collect = request.collect(id);
            } else {
                collect = request.cancelCollect(id);
            }
            HomeVM homeVM = ViewModelProviders.of(mFragment).get(HomeVM.class);
            disposable(collect.compose(ResultTransformer.handleResult()).subscribe(t -> {
                if (t != null) {

                    int errorCode = t.getErrorCode();
                    HomeCollectBean collectBean = new HomeCollectBean(errorCode, position, isCollect);
                    homeVM.getColleclLiveData().postValue(collectBean);
                }
            }, throwable -> {
                homeVM.getColleclLiveData().postValue(new HomeCollectBean(-1, position, isCollect));
            }));
            return;
        }
        Observable<HttpResult<List<HomeBanner>>> banner = request.getBanner();
        disposable(banner.compose(ResultTransformer.handleResult()).subscribe(t -> {
            if (t != null) {
                HomeVM homeVM = ViewModelProviders.of(mFragment).get(HomeVM.class);
                homeVM.getHomeBanner().postValue(t.getData());
            }
            emitter.onComplete();
        }, throwable -> {
            if (throwable instanceof ApiException) {
                emitter.onComplete();
            }
        }));
    }
}

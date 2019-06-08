package com.ancely.share.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ancely.netan.request.mvvm.bean.RequestErrBean;
import com.ancely.netan.request.mvvm.bean.ResponseBean;
import com.ancely.netan.request.utils.LogUtils;
import com.ancely.share.R;
import com.ancely.share.base.BaseActivity;
import com.ancely.share.base.HttpResult;
import com.ancely.share.bean.HotTipsBean;
import com.ancely.share.database.AppDatabase;
import com.ancely.share.model.HotTipsModelP;
import com.ancely.share.utils.DrawableUtils;
import com.ancely.share.utils.SizeUtils;
import com.ancely.share.viewmodel.HotTipsVM;
import com.ancely.share.views.FlowLayout;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.ui.activity
 *  @文件名:   SearchActivity
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/4 6:00 PM
 *  @描述：    搜索界面
 */
public class SearchActivity extends BaseActivity<HotTipsVM, List<HotTipsBean>> {
    private Toolbar mActMainToolbar;
    private SearchView mSearchView;
    private HotTipsModelP mModelP;
    private FlowLayout mActSearchHotFll;
    private FlowLayout mActSearchHistoryFll;
    private TextView mActSearchDeleteTv;

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected Class<HotTipsVM> initClazz() {
        return HotTipsVM.class;
    }


    @Override
    protected void initDatas() {
        mModelP.startRequestService();
    }

    @Override
    protected void initEvent() {
        mActMainToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void initView() {
        mActSearchHotFll = findViewById(R.id.act_search_hot_fll);
        mActSearchHistoryFll = findViewById(R.id.act_search_history_fll);
        mActSearchDeleteTv = findViewById(R.id.act_search_delete_tv);
        mActMainToolbar = findViewById(R.id.act_main_toolbar);
        setSupportActionBar(mActMainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mModelP = new HotTipsModelP(this, HotTipsVM.class);
    }

    @Override
    public boolean isNeedCheckNetWork() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.searchView);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //通过MenuItem得到SearchView
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //设置输入框提示语
        mSearchView.setQueryHint(getResources().getString(R.string.search_key));
//        mSearchView.setIconifiedByDefault(false);
        mSearchView.onActionViewExpanded();
//        //设置是否显示搜索框展开时的提交按钮
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryRefinementEnabled(false);
        mSearchView.requestFocusFromTouch();
//        mSearchView.setIconified(false);
        ImageView goButton = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_go_btn);
        goButton.setImageResource(R.drawable.ic_search_white_24dp);
        //SearchView设置监听
        setMenuListener();
        return true;
    }

    private void setMenuListener() {
        //搜索框展开时后面叉叉按钮的点击事件
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(SearchActivity.this, "Close", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //搜索图标按钮(打开搜索框的按钮)的点击事件
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchActivity.this, "Open", Toast.LENGTH_SHORT).show();
            }
        });
        //搜索框文字变化监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {//键盘搜索点击事件
                Toast.makeText(SearchActivity.this, s, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {//时时输入点击事件
                LogUtils.e(SearchActivity.class.getSimpleName(), "TextChange --> " + s);
                return false;
            }
        });
    }

    @Override
    public void accessSuccess(ResponseBean<HttpResult<List<HotTipsBean>>> responseBean) {
        super.accessSuccess(responseBean);
        List<HotTipsBean> hotTips = responseBean.body.getData();
        addTextView(hotTips);
        mActSearchHotFll.setOnChildClickListener(new FlowLayout.OnChildClickListener() {
            @Override
            public void onClidlClick(View view) {
                int index = mActSearchHotFll.indexOfChild(view);
                String name = hotTips.get(index).getName();
                Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addTextView(List<HotTipsBean> hotTips) {
        mActSearchHotFll.removeAllViews();
        for (HotTipsBean s : hotTips) {
            if (TextUtils.isEmpty(s.getName())) {
                continue;
            }
            GradientDrawable normolDrawable = DrawableUtils.creatDrable(this, R.color.color_ffffff, 2, 0.5f, R.color.color_e62e3d);
            TextView textView = new TextView(this);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, SizeUtils.px2dp(12));
            textView.setBackgroundDrawable(normolDrawable);
            textView.setPadding(SizeUtils.px2dp(8), SizeUtils.px2dp(3), SizeUtils.px2dp(8), SizeUtils.px2dp(3));
            textView.setTextColor(getResources().getColor(R.color.color_99ffe62e3d));
            textView.setText(s.getName());
            mActSearchHotFll.addView(textView);
        }
    }

    @Override
    public void accessError(RequestErrBean errBean) {
        super.accessError(errBean);
        Single<List<HotTipsBean>> hotTips = AppDatabase.getInstance(this).getHotTipsDao().getAll();
        mModelP.disposable(hotTips.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(hotTip -> {
            Collections.reverse(hotTip);
            addTextView(hotTip);
            mActSearchHotFll.setOnChildClickListener(view -> {
                int index = mActSearchHotFll.indexOfChild(view);
                String name = hotTip.get(index).getName();
                Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
            });
        }));
    }
}

package com.ancely.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.ancely.netan.base.BaseModelActivity;
import com.ancely.share.adapter.ViewPagerAdapter;
import com.ancely.share.ui.activity.SearchActivity;
import com.ancely.share.ui.fragment.capital.CapitalFragment;
import com.ancely.share.ui.fragment.home.HomeFragment;
import com.ancely.share.views.BottomView;
import com.ancely.share.views.banner.CustomViewpager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseModelActivity {
    private Toolbar mActMainToolbar;
    private CustomViewpager mActMainVp;
    private List<Fragment> mFragments;
    private FloatingActionButton mActMainFloatingActionBtn;
    private LinearLayout mActMainContainer;

    private String[] mainString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareApplication.destoryFlag = 1;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected Class initClazz() {
        return null;
    }

    @Override
    protected void initDatas() {
        mainString = getResources().getStringArray(R.array.main);
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new CapitalFragment());
        mActMainVp.setPagingEnabled(false);
        mActMainVp.setOffscreenPageLimit(4);
        mActMainVp.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));
    }

    @Override
    protected void initEvent() {
        mActMainToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mActMainToolbar);
        mActMainFloatingActionBtn.setOnClickListener(v -> {
            if (mFragments.get(0) instanceof HomeFragment) {
                ((HomeFragment) mFragments.get(0)).scrollToTop();
            }
        });
        int childCount = mActMainContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            mActMainContainer.getChildAt(i).setOnClickListener(this);
        }

    }

    @Override
    protected void initView() {
        mActMainToolbar = findViewById(R.id.act_main_toolbar);
        mActMainVp = findViewById(R.id.act_main_vp);
        mActMainFloatingActionBtn = findViewById(R.id.act_main_floating_action_btn);
        mActMainContainer = findViewById(R.id.act_main_container);
        changerUi(0);
    }

    private int mBottomSelectPos = 1;

    private void changerUi(int position) {
        if (mBottomSelectPos == position) return;
        changeBottomColor(mActMainContainer.getChildAt(position), true);
        changeBottomColor(mActMainContainer.getChildAt(mBottomSelectPos), false);
        mBottomSelectPos = position;
        mActMainVp.setCurrentItem(mBottomSelectPos, false);
    }


    /**
     * 改变底部导航栏颜色
     */
    private void changeBottomColor(View childView, boolean selectFlag) {
        BottomView view = (BottomView) childView;
        view.changerViewColor(selectFlag);
    }

    @Override
    public boolean isNeedCheckNetWork() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_main_home:
                changerUi(0);
                break;
            case R.id.act_main_categories:
                changerUi(1);
                break;
            case R.id.act_main_pinpai:
                changerUi(2);
                break;
            case R.id.act_main_community:
                changerUi(3);
                break;
            case R.id.act_main_center:
                changerUi(4);
                break;
            default:
                changerUi(0);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("mSelectedView", mBottomSelectPos);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mBottomSelectPos = savedInstanceState.getInt("mSelectedView");
        if (mBottomSelectPos != 0) {
            changeBottomColor(mActMainContainer.getChildAt(mBottomSelectPos), true);
            changeBottomColor(mActMainContainer.getChildAt(0), false);
            mActMainToolbar.setTitle(mainString[mBottomSelectPos]);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

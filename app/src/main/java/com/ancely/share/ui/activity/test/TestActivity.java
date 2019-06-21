package com.ancely.share.ui.activity.test;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.ancely.share.R;
import com.ancely.share.base.BaseActivity;
import com.ancely.share.ui.fragment.test.TabFragment;
import com.ancely.share.views.test.TabView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.ui.activity
 *  @文件名:   TestActivity
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/17 10:20 AM
 *  @描述：    TODO
 */
public class TestActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private List<String> mTitels = new ArrayList<>(Arrays.asList("微信", "通讯录", "发现", "我"));
    private TabView mBtnWechat;
    private TabView mBtnFriend;
    private TabView mBtnFind;
    private TabView mBtnMine;
    private SparseArray<TabFragment> mFragments = new SparseArray<>();
    private List<TabView> mTabViews = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_test;
    }

    @Override
    protected Class initClazz() {
        return null;
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initEvent() {//FragmentPageAdapter 会执行onDestoryView但Fragment不会销毁 FragmentStatePagerAdapter  会把整个Fragment都销毁
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return TabFragment.newInstance(mTitels.get(i));
            }

            @Override
            public int getCount() {
                return mTitels.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                TabFragment tabFragment = (TabFragment) super.instantiateItem(container, position);
                mFragments.put(position, tabFragment);
                return tabFragment;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                mFragments.remove(position);
                super.destroyItem(container, position, object);

            }
        });
        for (int i = 0; i < mTabViews.size(); i++) {
            TabView tabView = mTabViews.get(i);
            int finalI = i;
            tabView.setOnClickListener(v -> {
                mViewPager.setCurrentItem(finalI);
                setCurrentTab(finalI);
            });
        }
        setCurrentTab(0);
        mViewPager.addOnPageChangeListener(this);
    }

    public void setCurrentTab(int position) {
        for (int i = 0; i < mTabViews.size(); i++) {
            TabView tabView = mTabViews.get(i);
            if (i == position) {
                tabView.setProcess(1);
            } else {
                tabView.setProcess(0);
            }
        }
    }

    @Override
    protected void initView() {
        mViewPager = findViewById(R.id.vp_name);
        mViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float positionOffset) {

            }
        });
        mBtnWechat = findViewById(R.id.tab_view_wechat);
        mBtnFriend = findViewById(R.id.tab_view_friend);
        mBtnFind = findViewById(R.id.tab_view_find);
        mBtnMine = findViewById(R.id.tab_view_mine);
        mViewPager.setOffscreenPageLimit(4);
        mTabViews.add(mBtnWechat);
        mTabViews.add(mBtnFriend);
        mTabViews.add(mBtnFind);
        mTabViews.add(mBtnMine);
        mBtnWechat.setTest(mTitels.get(0));
        mBtnFriend.setTest(mTitels.get(1));
        mBtnFind.setTest(mTitels.get(2));
        mBtnMine.setTest(mTitels.get(3));
    }

    @Override
    public boolean isNeedCheckNetWork() {
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPx) {
        if (positionOffset > 0) {
            TabView left = mTabViews.get(position);
            TabView rigth = mTabViews.get(position + 1);
            left.setProcess(1 - positionOffset);
            Log.e("onPageScrolled", positionOffset + "");
            rigth.setProcess(positionOffset);

        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

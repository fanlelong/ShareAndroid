package com.ancely.share.ui.fragment.test;

import android.os.Bundle;
import android.widget.TextView;

import com.ancely.share.R;
import com.ancely.share.base.BaseFragment;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.ui.fragment
 *  @文件名:   TabFragment
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/17 10:22 AM
 *  @描述：    fragment和activity通信要用接口回调,fragment里定义接口,activity里面设置监听
 */
public class TabFragment extends BaseFragment {
    TextView mTextView;
    public static final String TAB_FRAGMENT_TITLE = "tab_title";

    @Override
    protected void loadData() {
        if (getArguments() != null) {
            String title = getArguments().getString(TAB_FRAGMENT_TITLE, "");
            mTextView.setText(title);
        }
    }

    public static TabFragment newInstance(String title) {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAB_FRAGMENT_TITLE, title);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    protected void initView() {
        mTextView = (TextView) findViewById(R.id.tab_tv);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_tab;
    }

    @Override
    public boolean isNeedCheckNetWork() {
        return false;
    }

    public void changerTitle(String title) {
        if (!isAdded()) {
            return;
        }
        mTextView.setText(title);
    }
}

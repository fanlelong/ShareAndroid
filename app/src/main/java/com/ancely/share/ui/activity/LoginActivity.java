package com.ancely.share.ui.activity;

import android.view.View;

import com.ancely.netan.base.BaseModelActivity;
import com.ancely.share.R;
import com.ancely.share.views.BaseTitle;

import androidx.navigation.Navigation;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.ui.activity
 *  @文件名:   LoginActivity
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/31 3:05 PM
 *  @描述：    TODO
 */
public class LoginActivity extends BaseModelActivity implements BaseTitle.OnLeftBackListener {
    private boolean mPopBackStack;
    private BaseTitle mActLoginTitle;

    @Override
    protected void initView() {
        mActLoginTitle = findViewById(R.id.act_login_title);
        mActLoginTitle.setBackVisbility(false);
    }

    @Override
    protected void initEvent() {
        mActLoginTitle.setOnLeftBackListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected Class initClazz() {
        return null;
    }

    @Override
    protected void initDatas() {

    }

    public void setBarTitle(int resId) {
        mActLoginTitle.setTitle(resId);
    }

    public void setTitle(String title) {
        mActLoginTitle.setTitle(title);
    }

    public void setLeftIconIsShow(boolean isShow) {
        mActLoginTitle.setBackVisbility(isShow);
    }

    @Override
    public boolean isNeedCheckNetWork() {
        return false;
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.fragment_navigation_login).navigateUp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_login_title:

                break;
            default:

                break;
        }
    }

    @Override
    public void leftBack() {
        mPopBackStack = Navigation.findNavController(this, R.id.fragment_navigation_login).popBackStack();
        if (!mPopBackStack) {
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_window_in_left, R.anim.anim_window_out_right);
    }
}

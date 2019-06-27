package com.ancely.share.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.ancely.share.R;
import com.ancely.share.base.BaseActivity;
import com.ancely.share.base.BaseFragment;

import java.lang.ref.WeakReference;
import java.util.Stack;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.ui.activity
 *  @文件名:   SinglerFragActivity
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/10 10:33 AM
 *  @描述：    TODO
 */
public class SinglerFragActivity extends BaseActivity {
    private static WeakReference<Class> sWeakReference;
    private Stack<BaseFragment> mFrgStack;
    private Toolbar mActMainToolbar;
    private static String title;
    private FloatingActionButton mActMainFloatingActionBtn;

    @Override
    protected int getContentView() {
        return R.layout.activity_singler;
    }

    @Override
    protected Class initClazz() {
        return null;
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initEvent() {

        mActMainToolbar.setTitle(title);
        setSupportActionBar(mActMainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActMainToolbar.setNavigationOnClickListener(v -> finish());
        mActMainFloatingActionBtn.setOnClickListener(v -> currentfrg().scrollTop());
    }

    @Override
    protected void initView() {
        mActMainToolbar = findViewById(R.id.act_main_toolbar);

        mActMainFloatingActionBtn = findViewById(R.id.act_main_floating_action_btn);

        loadfragment();
    }

    private void loadfragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        BaseFragment fragment = null;
        try {
            if (sWeakReference == null || sWeakReference.get() == null) {
                return;
            }
            fragment = (BaseFragment) sWeakReference.get().newInstance();
            pushActivity(fragment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment == null) {
            return;
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentTransaction.replace(R.id.act_content, fragment);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commitAllowingStateLoss();
    }

    // 将当前Fragment推入栈中
    private void pushActivity(BaseFragment frg) {
        if (mFrgStack == null) {
            mFrgStack = new Stack<>();
        }
        mFrgStack.add(frg);
    }

    // 退出栈顶Fragment
    private void popActivity(Fragment frg) {
        if (frg != null) {
            // 在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
            if (mFrgStack != null)
                mFrgStack.remove(frg);
            frg = null;
        }
    }

    // 获得当前栈顶Fragment
    private BaseFragment currentfrg() {
        BaseFragment frg = null;
        if (mFrgStack != null && !mFrgStack.empty())
            frg = mFrgStack.lastElement();
        return frg;
    }

    @Override
    public void finish() {
        popActivity(currentfrg());
        sWeakReference.clear();
        super.finish();
    }

    @Override
    public boolean isNeedCheckNetWork() {
        return false;
    }

    public static void LaunchAct(Context context, Class c, Bundle bundle) {
        sWeakReference = new WeakReference<>(c);
        Intent intent = new Intent(context, SinglerFragActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(R.anim.anim_window_in_right, R.anim.anim_window_out_left);
        }
    }

    public static void LaunchAct(Context context, Class c, Bundle bundle, String t) {
        sWeakReference = new WeakReference<>(c);
        title = t;
        Intent intent = new Intent(context, SinglerFragActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(R.anim.anim_window_in_right, R.anim.anim_window_out_left);
        }
    }
}

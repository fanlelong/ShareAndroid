package com.ancely.mvp.text;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.ancely.mvp.BasePresenter;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.mvp.text
 *  @文件名:   TextPresenter
 *  @创建者:   fanlelong
 *  @创建时间:  2019/7/26 10:24 AM
 *  @描述：    TODO
 */
public class TextPresenter extends BasePresenter<TextModel, ITestIml, TestContract.Persenter> {
    public TextPresenter(@NonNull Fragment fragment, ITestIml baseView) {
        super(fragment, baseView);
    }

    public TextPresenter(@NonNull FragmentActivity fragment, ITestIml baseView) {
        super(fragment, baseView);
    }

    @Override
    protected void initObserable(TextModel baseModel) {

        baseModel.getResultLiveData().observe(getView(), new Observer<TestInfo>() {
            @Override
            public void onChanged(@Nullable TestInfo testInfo) {
                mBaseView.getContract().handlerResult(testInfo);
            }
        });

        baseModel.getResultLiveData1().observe(getView(), new Observer<TestOneInfo>() {
            @Override
            public void onChanged(@Nullable TestOneInfo testOneInfo) {
                mBaseView.getContract().handlerResult1(testOneInfo);
            }
        });
    }


    @Override
    protected Class<TextModel> getModelClass() {
        return TextModel.class;
    }

    @Override
    protected TestContract.Persenter getContract() {
        return new TestContract.Persenter() {
            @Override
            public void requestTest(String accound, String psw) {
                try {
                    getBaseModel().getContract().execudeTest(accound, psw);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestTest1(String accound, String psw) {
                if (getView() == null) {
                    return;
                }
                mBaseView.getContract().handlerResult1(new TestOneInfo());
            }
        };
    }
}

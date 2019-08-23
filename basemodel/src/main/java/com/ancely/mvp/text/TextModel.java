package com.ancely.mvp.text;

import android.arch.lifecycle.MediatorLiveData;

import com.ancely.mvp.BaseModel;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.mvp.text
 *  @文件名:   TextModel
 *  @创建者:   fanlelong
 *  @创建时间:  2019/7/26 10:24 AM
 *  @描述：    TODO
 */
public class TextModel extends BaseModel<TextPresenter, TestContract.Model> {
    private MediatorLiveData<TestInfo> resultLiveData = new MediatorLiveData<>();
    private MediatorLiveData<TestOneInfo> resultLiveData1 = new MediatorLiveData<>();

    @Override
    public TestContract.Model getContract() {
        return new TestContract.Model() {
            @Override
            public void execudeTest(String accound, String psw) throws Exception {
                //进行网络请求
                if (true) {
                    TestInfo textInfo = new TestInfo();
                    resultLiveData.setValue(textInfo);

                } else {
                    getResultLiveData1().setValue(new TestOneInfo());

                    mPresenter.getContract().requestTest1("dsf", "fsdfs");
                }
            }

            @Override
            public void execudeTest1(String accound, String psw) throws Exception {
            }
        };
    }

    public MediatorLiveData<TestInfo> getResultLiveData() {
        return resultLiveData;
    }

    public MediatorLiveData<TestOneInfo> getResultLiveData1() {
        return resultLiveData1;
    }
}

package com.ancely.mvp.text;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.textservice.TextInfo;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.mvp.text
 *  @文件名:   TextActivity
 *  @创建者:   fanlelong
 *  @创建时间:  2019/7/26 10:24 AM
 *  @描述：    TODO
 */
public class TextActivity extends AppCompatActivity implements ITestIml {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextPresenter presenter = new TextPresenter(this, this);
        presenter.getContract().requestTest("123","456");
    }




    @Override
    public TestContract.View getContract() {
        return new TestContract.View() {


            @Override
            public void handlerResult(TestInfo t) {

            }

            @Override
            public void handlerResult1(TestOneInfo oneInfo) {

            }
        };

    }
}

package com.ancely.mvp.text;


/*
 *  @项目名：  ModlerApp
 *  @包名：    com.ancely.modlerapp.login
 *  @文件名:   LoginContract
 *  @创建者:   fanlelong
 *  @创建时间:  2019/7/24 1:46 PM
 *  @描述：    登陆
 */
public interface TestContract {
    interface Model {
        void execudeTest(String accound, String psw) throws Exception;

        void execudeTest1(String accound, String psw) throws Exception;
    }

    interface View {
        void handlerResult(TestInfo t);

        void handlerResult1(TestOneInfo oneInfo);
    }

    interface Persenter {
        void requestTest(String accound, String psw);
        void requestTest1(String accound, String psw);

    }
}

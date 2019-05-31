package com.ancely.netan.request.mvvm.bean;

/*
 *  @项目名：  BaseMvp
 *  @包名：    com.ancely.rxjava.mvp
 *  @文件名:   RequestErrBean
 *  @创建者:   fanlelong
 *  @创建时间:  2018/9/3 上午11:29
 *  @描述：    TODO
 */

public class RequestErrBean {
    public String msg;
    public int code;
    public int flag;

    public RequestErrBean(int code, String msg, int flag) {
        this.msg = msg;
        this.code = code;
        this.flag = flag;
    }
}

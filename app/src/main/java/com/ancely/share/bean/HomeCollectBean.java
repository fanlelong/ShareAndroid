package com.ancely.share.bean;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.bean
 *  @文件名:   HomeCollectBean
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/4 4:42 PM
 *  @描述：    TODO
 */
public class HomeCollectBean {
    public int errorCode;
    public int position;
    public boolean isCollect;

    public HomeCollectBean(int errorCode, int position, boolean isCollect) {
        this.errorCode = errorCode;
        this.position = position;
        this.isCollect = isCollect;
    }
}

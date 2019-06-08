package com.ancely.netan.network.bean;

import com.ancely.netan.network.NetType;

import java.lang.reflect.Method;


/*
 *  @项目名：  ModlerApp
 *  @包名：    luckly.ancely.com.network.bean
 *  @文件名:   MethodManager
 *  @创建者:   fanlelong
 *  @创建时间:  2019/2/27 4:06 PM
 *  @描述：    保存符合要求的网络监听方法  封装类
 */
public class MethodManager {
    private Class<?> type;//参数类型
    private NetType netType;//网络模式
    private Method method;//需要执行的方法
    private boolean isManThread;//是否需要要主线程

    public boolean isManThread() {
        return isManThread;
    }

    public MethodManager(Class<?> type, NetType netType, Method method, boolean isManThread) {
        this.type = type;
        this.netType = netType;
        this.method = method;
        this.isManThread = isManThread;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public NetType getNetType() {
        return netType;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}

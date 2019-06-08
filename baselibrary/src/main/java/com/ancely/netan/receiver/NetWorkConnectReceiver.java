package com.ancely.netan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ancely.netan.event.NetworkChangeEvent;
import com.ancely.netan.network.NetChangeImpl;
import com.ancely.netan.network.NetType;
import com.ancely.netan.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;

/*
 *  @项目名：  ShareAndroid
 *  @文件名:   NetWorkConnectReceiver
 *  @创建者:   fanlelong
 *  @创建时间:  2019/2/27 1:41 PM
 *  @描述：    网络监听广播
 */
public class NetWorkConnectReceiver extends BroadcastReceiver {
    private NetType mNetType;
    private NetChangeImpl mNetChange;

    @Override
    public void onReceive(Context context, Intent intent) {

        //通知所有注册的方法,网络发生了变化
        if (mNetType == NetUtils.getNetType()) return;
        mNetType = NetUtils.getNetType();
        mNetChange.post(mNetType);
    }

    public NetWorkConnectReceiver(NetChangeImpl netChange) {
        mNetType = NetType.NONE;
        mNetChange = netChange;
    }

}
package com.ancely.netan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.ancely.netan.event.NetworkChangeEvent;
import com.ancely.netan.utils.NetUtils;
import org.greenrobot.eventbus.EventBus;

public class NetWorkConnectReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkConnectChanged";

    @Override
    public void onReceive(Context context, Intent intent) {

        //**判断当前的网络连接状态是否可用*/
        boolean isConnected = NetUtils.isConnected(context);
        Log.i(TAG, "onReceive: 当前网络 " + isConnected);
        EventBus.getDefault().post(new NetworkChangeEvent(isConnected));
    }
}
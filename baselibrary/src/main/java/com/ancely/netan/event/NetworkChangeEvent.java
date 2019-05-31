package com.ancely.netan.event;

public class NetworkChangeEvent {
    public boolean isConnected;//是否存在网络

    public NetworkChangeEvent(boolean isConnected) {
        this.isConnected = isConnected;
    }
}

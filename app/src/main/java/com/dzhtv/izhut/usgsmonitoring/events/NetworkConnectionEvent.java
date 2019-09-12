package com.dzhtv.izhut.usgsmonitoring.events;

import android.content.Context;

public class NetworkConnectionEvent {
    private Context _context;
    private boolean isNetworkConnected;

    public NetworkConnectionEvent(Context context, boolean isConnect){
        this._context = context;
        this.isNetworkConnected = isConnect;
    }

    public boolean getConnected() {
        return isNetworkConnected;
    }

    public Context getEventContext() {
        return _context;
    }
}

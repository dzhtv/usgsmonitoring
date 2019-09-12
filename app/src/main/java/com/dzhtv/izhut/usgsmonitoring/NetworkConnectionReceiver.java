package com.dzhtv.izhut.usgsmonitoring;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.dzhtv.izhut.usgsmonitoring.events.NetworkConnectionEvent;

import org.greenrobot.eventbus.EventBus;


public class NetworkConnectionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(App.TAG, "onReceive");

        if (isInitialStickyBroadcast()){
            //ignore
            Log.d(App.TAG, "onReceive, sticky broadcast");
        }else {
            if (isOnline(context))
                EventBus.getDefault().post(new NetworkConnectionEvent(context, true));
            else
                EventBus.getDefault().post(new NetworkConnectionEvent(context, false));
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}

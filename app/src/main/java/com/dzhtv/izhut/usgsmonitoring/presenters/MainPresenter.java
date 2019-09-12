package com.dzhtv.izhut.usgsmonitoring.presenters;


import android.content.pm.PackageManager;
import android.util.Log;

import com.dzhtv.izhut.usgsmonitoring.App;
import com.dzhtv.izhut.usgsmonitoring.Config;
import com.dzhtv.izhut.usgsmonitoring.contracts.MainMvpPresenter;
import com.dzhtv.izhut.usgsmonitoring.events.PermissionsAccess;
import com.dzhtv.izhut.usgsmonitoring.ui.base.BasePresenter;
import com.dzhtv.izhut.usgsmonitoring.views.MainMvpView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {


    @Inject
    public MainPresenter(){
        super();
    }

    @Override
    public void requestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Config.REQUEST_PERMISSION_CODE) {
            HashMap<String, Integer> permissionsResults = new HashMap<>();
            int deniedCount = 0;

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionsResults.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }

            if (deniedCount == 0) {
                //пользователь дал все разрешения
                Log.d(App.TAG, "MainActivity, onRequestPermissionsResult, deniedCount == 0");
                getMvpView().onPushEventPermissionsAccess(new PermissionsAccess(true)); //push event
            } else {
                for (Map.Entry<String, Integer> entry : permissionsResults.entrySet()) {
                    String permName = entry.getKey();
                    int permRes = entry.getValue();

                    getMvpView().onCheckPermissionsRationale(permName);
                }
            }
        }
    }


}



package com.dzhtv.izhut.usgsmonitoring;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;


public class BaseActivity extends AppCompatActivity {
    protected List<String> neededPermissions;
    //protected ConnectivityManager connMgr;
   // protected NetworkInfo networkInfo;

    protected boolean checkAndRequestPermissions() {
        neededPermissions = new ArrayList<>();
        for (String per : Config.appPermissions) {
            if (ContextCompat.checkSelfPermission(this, per) != PackageManager.PERMISSION_GRANTED) {
                neededPermissions.add(per);
            }
        }

        if (!neededPermissions.isEmpty()) {
            //presenter.setNeededPermissions(neededPermissions);
            return false;
        }
        return true;
    }

    @Nullable
    protected List<String> getNeededPermissions() {
        return neededPermissions;
    }

    /*

    protected boolean checkConnection(){
        connMgr = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    */

}

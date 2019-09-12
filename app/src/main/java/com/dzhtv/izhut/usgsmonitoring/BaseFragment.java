package com.dzhtv.izhut.usgsmonitoring;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.dzhtv.izhut.usgsmonitoring.callbacks.PermissionsCallback;

import java.util.ArrayList;
import java.util.List;



public class BaseFragment extends Fragment {
    protected List<String> neededPermissions;
    protected PermissionsCallback callback;
    private ConnectivityManager connMgr;
    private NetworkInfo networkInfo;

    protected boolean checkAndRequestPermissions(){
        neededPermissions = new ArrayList<>();
        for (String per: Config.appPermissions){
            if (ContextCompat.checkSelfPermission(getActivity(), per) != PackageManager.PERMISSION_GRANTED){
                neededPermissions.add(per);
            }
        }

        if (!neededPermissions.isEmpty() && callback != null){
            callback.getNeededPermissions(neededPermissions);
            return false;
        }
        return true;
    }

    protected void setPermissionsCallback(PermissionsCallback pCallback){
        this.callback = pCallback;
    }

    /**
     * Проверка наличия интернета
     * @return
     */
    protected boolean checkInternetConnection(){
        connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}

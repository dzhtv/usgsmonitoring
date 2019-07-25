package com.dzhtv.izhut.usgsmonitoring;

import android.content.pm.PackageManager;

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



}

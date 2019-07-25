package com.dzhtv.izhut.usgsmonitoring.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.dzhtv.izhut.usgsmonitoring.Config;
import com.dzhtv.izhut.usgsmonitoring.R;

public class ActivateGPSDialog extends DialogFragment {
    private Context _context;

    public ActivateGPSDialog(Context context){
        this._context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
        builder.setMessage(getString(R.string.warning_on_gps))
                .setPositiveButton(getString(R.string.settings), (dialog, which) -> {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), Config.REQUEST_ON_GPS_IN_SETTINGS);
                })
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dismiss());
        return builder.create();
    }
}

package com.dzhtv.izhut.usgsmonitoring.ui.main;

import com.dzhtv.izhut.usgsmonitoring.events.PermissionsAccess;
import com.dzhtv.izhut.usgsmonitoring.ui.base.MvpView;

public interface MainMvpView extends MvpView {
    void onPushEventPermissionsAccess(PermissionsAccess event);
    void onShowWarningDialog();
    void onGoToSettingsDialog();
    void onCheckPermissionsRationale(String namePermission);
    String callString(int id);
}

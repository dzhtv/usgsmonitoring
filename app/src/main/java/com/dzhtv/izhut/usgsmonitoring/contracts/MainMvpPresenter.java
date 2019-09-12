package com.dzhtv.izhut.usgsmonitoring.contracts;


import com.dzhtv.izhut.usgsmonitoring.ui.base.MvpPresenter;
import com.dzhtv.izhut.usgsmonitoring.views.MainMvpView;

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    void requestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
}

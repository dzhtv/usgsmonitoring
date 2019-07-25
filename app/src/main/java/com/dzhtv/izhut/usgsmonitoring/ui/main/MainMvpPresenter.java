package com.dzhtv.izhut.usgsmonitoring.ui.main;


import com.dzhtv.izhut.usgsmonitoring.ui.base.MvpPresenter;

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    void requestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
}

package com.dzhtv.izhut.usgsmonitoring.ui.earthquake;

import com.dzhtv.izhut.usgsmonitoring.ui.base.MvpPresenter;

public interface EarthquakeMvpPresenter<V extends EarthquakeMvpView> extends MvpPresenter<V> {
    void init();
    void getEarthquakesData(boolean isUpdate);
}

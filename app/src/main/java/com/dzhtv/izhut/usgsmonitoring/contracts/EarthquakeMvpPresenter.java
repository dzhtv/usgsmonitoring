package com.dzhtv.izhut.usgsmonitoring.contracts;

import com.dzhtv.izhut.usgsmonitoring.ui.base.MvpPresenter;
import com.dzhtv.izhut.usgsmonitoring.views.EarthquakeMvpView;

public interface EarthquakeMvpPresenter<V extends EarthquakeMvpView> extends MvpPresenter<V> {
    void init();
    void getEarthquakesData(boolean isUpdate);
}

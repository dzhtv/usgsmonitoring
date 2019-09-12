package com.dzhtv.izhut.usgsmonitoring.contracts;

import com.dzhtv.izhut.usgsmonitoring.ui.base.MvpPresenter;
import com.dzhtv.izhut.usgsmonitoring.views.MarsRoverView;

public interface MarsRoverMvpPresenter<V extends MarsRoverView> extends MvpPresenter<V> {

    void init();
    void clickSearchBtn();

}

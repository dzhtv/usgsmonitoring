package com.dzhtv.izhut.usgsmonitoring.ui.other;

import com.dzhtv.izhut.usgsmonitoring.ui.base.MvpPresenter;

public interface OtherMvpPresenter<V extends OtherView> extends MvpPresenter<V> {

    void init();
    void clickSearchBtn();

}

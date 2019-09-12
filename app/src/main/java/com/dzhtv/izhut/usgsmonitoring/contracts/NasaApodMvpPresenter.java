package com.dzhtv.izhut.usgsmonitoring.contracts;

import com.dzhtv.izhut.usgsmonitoring.views.NasaApodView;
import com.dzhtv.izhut.usgsmonitoring.ui.base.MvpPresenter;

public interface NasaApodMvpPresenter<V extends NasaApodView> extends MvpPresenter<V> {
    void init();
    void loadApod(boolean isUpdate);
    void clickOnPhoto();
}

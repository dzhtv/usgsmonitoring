package com.dzhtv.izhut.usgsmonitoring.views;

import com.dzhtv.izhut.usgsmonitoring.ui.base.MvpView;

public interface NasaApodView extends MvpView {
    void showLoadingIndicator();
    void hideLoadingIndicator();
    void showNoConnectingMessage();
    void onSetImage(String url);
    void onSetTitle(String title);
    void onSetDescription(String desc);
    void onSetDate(String date);
    void showErrorMsg(String errorMsg);
    boolean checkConnection();
    void openHDPicture(String hdUrl);
}

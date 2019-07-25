package com.dzhtv.izhut.usgsmonitoring.ui.weather;

import com.dzhtv.izhut.usgsmonitoring.ui.base.MvpView;

import java.util.List;

public interface WeatherMvpView extends MvpView {
    void onShowErrorMessage(String errorMsg);
    void hideLoadingIndicator();
    void setTemp(int temp);
    void setMinTemp(int minTemp);
    void setMaxTemp(int maxTemp);
    void setHumidity(int hum);
    void setPressure(int pressure);
    void setDescription(String desc);
    void onRequestPermissions(List<String> permissions);
}

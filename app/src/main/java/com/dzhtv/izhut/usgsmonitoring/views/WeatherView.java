package com.dzhtv.izhut.usgsmonitoring.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

public interface WeatherView extends MvpView {
    void onShowErrorMessage(String errorMsg);
    void hideLoadingIndicator();
    void setTemp(int temp);
    void setMinTemp(int minTemp);
    void setMaxTemp(int maxTemp);
    void setHumidity(int hum);
    void setPressure(int pressure);
    void setDescription(String desc);
    @StateStrategyType(SkipStrategy.class)
    void onRequestPermissions(List<String> permissions);
}

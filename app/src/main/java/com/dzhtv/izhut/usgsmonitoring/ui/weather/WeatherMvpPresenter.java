package com.dzhtv.izhut.usgsmonitoring.ui.weather;

import com.dzhtv.izhut.usgsmonitoring.ui.base.MvpPresenter;

import java.util.List;

public interface WeatherMvpPresenter<V extends WeatherMvpView> extends MvpPresenter<V> {
    void setNeededPermissions(List<String> items);
    void loadWeatherByName(String cityName);
    void clickRequestPermissions();
    void loadByCoords(double lat, double lng);

}

package com.dzhtv.izhut.usgsmonitoring.callbacks;

import com.dzhtv.izhut.usgsmonitoring.models.weather.WeatherData;

public interface WeatherCallback {
    void onError(String errorMsg);
    void onSuccess(WeatherData data);
}

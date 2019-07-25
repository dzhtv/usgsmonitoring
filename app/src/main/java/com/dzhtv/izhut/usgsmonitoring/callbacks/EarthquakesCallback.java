package com.dzhtv.izhut.usgsmonitoring.callbacks;

import com.dzhtv.izhut.usgsmonitoring.models.earthquake.EarthquakeData;

public interface EarthquakesCallback {
    void onError(String errorMsg);
    void onSuccess(EarthquakeData data);
}

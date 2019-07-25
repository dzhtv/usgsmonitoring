package com.dzhtv.izhut.usgsmonitoring.callbacks;

import com.dzhtv.izhut.usgsmonitoring.models.nasa.NasaRoverData;

public interface NasaDataRoverCallback {
    void onError(String errorMsg);
    void onSuccessRoverData(NasaRoverData data);
    void onCompleted();
}

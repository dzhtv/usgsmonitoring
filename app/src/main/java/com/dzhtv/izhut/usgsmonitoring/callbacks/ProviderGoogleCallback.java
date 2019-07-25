package com.dzhtv.izhut.usgsmonitoring.callbacks;

import android.location.Location;

public interface ProviderGoogleCallback {
    void onLocationChanged(Location geoPosition);
    void onProviderConnected();
}

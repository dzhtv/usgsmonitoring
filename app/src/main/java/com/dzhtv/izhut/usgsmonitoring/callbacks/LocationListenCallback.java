package com.dzhtv.izhut.usgsmonitoring.callbacks;

import android.location.Location;

public interface LocationListenCallback {
    void onRefreshLocation(Location _location);
}

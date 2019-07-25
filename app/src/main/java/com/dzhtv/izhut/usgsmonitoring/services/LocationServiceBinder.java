package com.dzhtv.izhut.usgsmonitoring.services;

import android.os.Binder;

public class LocationServiceBinder extends Binder {
    private LocationService _service;

    public LocationServiceBinder(LocationService locService){
        this._service = locService;
    }

    public LocationService getService(){
        return _service;
    }
}

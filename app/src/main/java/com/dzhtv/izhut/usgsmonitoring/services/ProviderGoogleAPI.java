package com.dzhtv.izhut.usgsmonitoring.services;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dzhtv.izhut.usgsmonitoring.App;
import com.dzhtv.izhut.usgsmonitoring.callbacks.ProviderGoogleCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

public class ProviderGoogleAPI implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleApiClient googleAPI;
    private ProviderGoogleCallback callback;

    public ProviderGoogleAPI(Context context){
        googleAPI = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (callback != null)
            callback.onProviderConnected();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(App.TAG, "ProviderGoogle, onLocationChange " + location.getLatitude() + " " + location.getLongitude());
        if (callback != null)
            callback.onLocationChanged(location);
    }

    /**
     * Получение последней известной локации
     * @return
     */
    public Location getLastLocation() {
        Location _loc = new Location("last_loc");
        _loc.setLatitude(55.859468);
        _loc.setLongitude(49.089321);
        try {
            return LocationServices.FusedLocationApi.getLastLocation(googleAPI);
        }catch (SecurityException ex){

        }catch (Exception e){

        }
        return _loc;
    }

    public void setProviderCallback(ProviderGoogleCallback callback){
        this.callback = callback;

        if (googleAPI.isConnected()){
            googleAPI.disconnect();
            googleAPI.connect();
        }else {
            googleAPI.connect();
        }
    }

    /**
     * Clear all callback at on destroy view
     */
    public void clearCallbacks(){
        callback = null;
    }
}

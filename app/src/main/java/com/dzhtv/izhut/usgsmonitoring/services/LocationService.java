package com.dzhtv.izhut.usgsmonitoring.services;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.dzhtv.izhut.usgsmonitoring.App;
import com.dzhtv.izhut.usgsmonitoring.Utils;
import com.dzhtv.izhut.usgsmonitoring.callbacks.LocationListenCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = LocationService.class.getSimpleName();
    private static final long POLLING_FREQ = 1000 * 20;
    private static final long FASTEST_UPDATE_FREQ = 1000 * 10;
    private LocationServiceBinder _binder;
    private LocationListenCallback listenCallback;
    private LocationRequest _locationRequest;
    private GoogleApiClient _googleApi;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return _binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        _binder = new LocationServiceBinder(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        _googleApi = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        if (_locationRequest == null){
            _locationRequest =  LocationRequest.create();
            _locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            _locationRequest.setInterval(POLLING_FREQ);
            _locationRequest.setFastestInterval(FASTEST_UPDATE_FREQ);
        }

        _googleApi.connect();

        return START_NOT_STICKY;
    }

    public void runForeground(){}

    public void disableForeground(){
        stopForeground(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    @Override
    public void onConnected(@androidx.annotation.Nullable Bundle bundle) {
        Log.i(TAG, "onConnected");
        try {
            Location _loc = getLastLocation();
            Log.i(TAG, "Last location: " + _loc.getLatitude() + " " + _loc.getLongitude());
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        startListenLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void setLocationListenerCallback(LocationListenCallback callback){
        this.listenCallback = callback;
    }

    /**
     * Получение последней известной локации
     * @return
     */
    public Location getLastLocation() {
        Location _loc = new Location("test");
        _loc.setLatitude(55.859468);
        _loc.setLongitude(49.089321);
        try {
            return LocationServices.FusedLocationApi.getLastLocation(_googleApi);
        }catch (SecurityException ex){

        }catch (Exception e){

        }
        return _loc;
    }

    public void startListenLocation() {
        //todo: раскоментировать при использовании сервиса
        /*
        try {
            if ( Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission( App.getCurrentContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission( App.getCurrentContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return ;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    _googleApi, _locationRequest, this);
            Log.i(TAG,"startListenLocation");
        } catch (IllegalStateException e){
            Log.e(TAG,"startListenLocation, " + e.getMessage());
        }
        */
    }

    @Override
    public void onLocationChanged(Location location) {
        //todo: раскоментировать при использовании сервиса
        /*
        Log.i(TAG, "onLocationChanged, " + location.getLatitude() + " " + location.getLongitude());
        Utils.saveCurrentLocation(App.getCurrentContext(), location);
        if (listenCallback != null){
            listenCallback.onRefreshLocation(location);
        }
        */
    }
}

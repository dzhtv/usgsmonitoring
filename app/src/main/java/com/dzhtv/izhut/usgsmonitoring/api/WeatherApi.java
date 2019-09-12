package com.dzhtv.izhut.usgsmonitoring.api;

import android.util.Log;

import com.dzhtv.izhut.usgsmonitoring.App;
import com.dzhtv.izhut.usgsmonitoring.Config;
import com.dzhtv.izhut.usgsmonitoring.callbacks.WeatherCallback;
import com.dzhtv.izhut.usgsmonitoring.models.weather.WeatherData;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherApi {
    private static WeatherApi instance;
    private IWeatherApi wApi = App.getWeatherApi();

    private WeatherApi(){

    }

    public static WeatherApi getInstance(){
        if (instance == null)
            instance = new WeatherApi();
        return instance;
    }

    public void loadByCityName(String city_name, WeatherCallback callback){
        Observable<WeatherData> observableWeather = wApi.getByCityName(city_name, Config.WEATHER_KEY_APPID, Config.WEATHER_UNITS);
        observableWeather.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(WeatherData weatherData) {
                        Gson gson = new Gson();
                        Log.d(App.TAG, "Load weather onNext " + gson.toJson(weatherData, WeatherData.class));
                        if (weatherData != null)
                            callback.onSuccess(weatherData);
                    }
                });
    }

    public void loadByCoords(double lat, double lng, WeatherCallback callback){
        Log.d(App.TAG, "WeatherApi, loadByCoords");
        if (wApi == null)
            Log.d(App.TAG, "WeatherApi, wApi == null!!");
        Observable<WeatherData> dataObservable = wApi.getByCoords(lat, lng, Config.WEATHER_KEY_APPID, Config.WEATHER_UNITS);
        dataObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback != null)
                            callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(WeatherData data) {
                        Gson gson = new Gson();
                        Log.d(App.TAG, "Load weather onNext " + gson.toJson(data, WeatherData.class));
                        if (callback != null && data != null)
                            callback.onSuccess(data);
                    }
                });
    }
}

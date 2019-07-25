package com.dzhtv.izhut.usgsmonitoring;

import android.app.Application;
import android.content.Context;

import com.dzhtv.izhut.usgsmonitoring.api.IEarthquakeApi;
import com.dzhtv.izhut.usgsmonitoring.api.INasaApi;
import com.dzhtv.izhut.usgsmonitoring.api.IWeatherApi;
import com.dzhtv.izhut.usgsmonitoring.components.ApplicationComponent;
import com.dzhtv.izhut.usgsmonitoring.components.DaggerApplicationComponent;
import com.dzhtv.izhut.usgsmonitoring.modules.AppModule;
import com.dzhtv.izhut.usgsmonitoring.services.ProviderGoogleAPI;
import com.squareup.picasso.Picasso;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

public class App extends Application {
    public static final String TAG = "Monitoring";
    private static Retrofit earthquakeApi, weatherApi, nasaApi;
    private static IEarthquakeApi earthquakeService;
    private static IWeatherApi weatherService;
    private static INasaApi nasaService;

    private ApplicationComponent _component;
    ProviderGoogleAPI googleProvider;

    @Override
    public void onCreate() {
        super.onCreate();

        initDI();
        initRetrofitAPI();
    }

    private void initDI(){
        _component = DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .build();
        _component.inject(this);
        googleProvider = _component.getProviderGoogleApi();
    }
    /**
     * init retrofit api
     */
    private void initRetrofitAPI(){
        earthquakeApi = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        earthquakeService = earthquakeApi.create(IEarthquakeApi.class);

        weatherApi = new Retrofit.Builder()
                .baseUrl(Config.WEATHER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        weatherService = weatherApi.create(IWeatherApi.class);

        nasaApi = new Retrofit.Builder()
                .baseUrl(Config.NASA_URL_MARS_PHOTOS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        nasaService = nasaApi.create(INasaApi.class);
    }

    public static IEarthquakeApi getEarthquakeApi(){
        return earthquakeService;
    }

    public static IWeatherApi getWeatherApi(){
        return weatherService;
    }

    public static INasaApi getNasaApi(){
        return nasaService;
    }

    public Picasso getPicasso(){
        return _component.getPicasso();
    }

    public ProviderGoogleAPI getProviderGoogleApi(){
        return googleProvider;
    }
}

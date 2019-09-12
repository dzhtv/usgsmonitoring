package com.dzhtv.izhut.usgsmonitoring.components;

import android.content.Context;

import com.dzhtv.izhut.usgsmonitoring.presenters.MarsRoverPresenter;
import com.dzhtv.izhut.usgsmonitoring.presenters.NasaApodPresenter;
import com.dzhtv.izhut.usgsmonitoring.views.MarsRoverView;
import com.dzhtv.izhut.usgsmonitoring.views.NasaApodView;
import com.dzhtv.izhut.usgsmonitoring.modules.AppModule;
import com.dzhtv.izhut.usgsmonitoring.services.ProviderGoogleAPI;
import com.dzhtv.izhut.usgsmonitoring.views.EarthquakeMvpView;
import com.dzhtv.izhut.usgsmonitoring.presenters.EarthquakePresenter;
import com.dzhtv.izhut.usgsmonitoring.views.WeatherMvpView;
import com.dzhtv.izhut.usgsmonitoring.presenters.WeatherPresenter;
import com.squareup.picasso.Picasso;

import dagger.Component;

@USGSMonitoringApplicationScope
@Component(modules = {AppModule.class})
public interface ApplicationComponent {
    void inject(Context context);
    Picasso getPicasso();
    ProviderGoogleAPI getProviderGoogleApi();
    EarthquakePresenter<EarthquakeMvpView> provideEarthquakePresenter();
    WeatherPresenter<WeatherMvpView> provideWeatherPresenter();
    MarsRoverPresenter<MarsRoverView> provideOtherPresenter();
    NasaApodPresenter<NasaApodView> provideNasaApodPresenter();
}

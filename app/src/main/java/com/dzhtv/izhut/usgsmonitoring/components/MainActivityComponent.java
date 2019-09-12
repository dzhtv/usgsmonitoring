package com.dzhtv.izhut.usgsmonitoring.components;


import com.dzhtv.izhut.usgsmonitoring.MainActivity;
import com.dzhtv.izhut.usgsmonitoring.modules.MainActivityModule;

import dagger.Component;

@USGSMonitoringApplicationScope
@Component (modules = {MainActivityModule.class})
public interface MainActivityComponent {
    void injectMainActivity(MainActivity mActivity);
    //MainPresenter<MainMvpView> getMainPresenter();
    /*
    EarthquakePresenter<EarthquakeMvpView> provideEarthquakePresenter();
    WeatherPresenter<WeatherMvpView> provideWeatherPresenter();
    MarsRoverPresenter<MarsRoverView> provideOtherPresenter();
    */
}

package com.dzhtv.izhut.usgsmonitoring.components;


import com.dzhtv.izhut.usgsmonitoring.MainActivity;
import com.dzhtv.izhut.usgsmonitoring.modules.MainActivityModule;
import com.dzhtv.izhut.usgsmonitoring.ui.earthquake.EarthquakeMvpView;
import com.dzhtv.izhut.usgsmonitoring.ui.earthquake.EarthquakePresenter;
import com.dzhtv.izhut.usgsmonitoring.ui.other.OtherPresenter;
import com.dzhtv.izhut.usgsmonitoring.ui.other.OtherView;
import com.dzhtv.izhut.usgsmonitoring.ui.weather.WeatherMvpView;
import com.dzhtv.izhut.usgsmonitoring.ui.weather.WeatherPresenter;

import dagger.Component;

@USGSMonitoringApplicationScope
@Component (modules = {MainActivityModule.class})
public interface MainActivityComponent {
    void injectMainActivity(MainActivity mActivity);
    //MainPresenter<MainMvpView> getMainPresenter();
    EarthquakePresenter<EarthquakeMvpView> provideEarthquakePresenter();
    WeatherPresenter<WeatherMvpView> provideWeatherPresenter();
    OtherPresenter<OtherView> provideOtherPresenter();
}

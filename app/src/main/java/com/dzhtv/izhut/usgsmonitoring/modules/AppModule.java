package com.dzhtv.izhut.usgsmonitoring.modules;

import android.content.Context;

import com.dzhtv.izhut.usgsmonitoring.presenters.NasaApodPresenter;
import com.dzhtv.izhut.usgsmonitoring.views.MarsRoverView;
import com.dzhtv.izhut.usgsmonitoring.views.NasaApodView;
import com.dzhtv.izhut.usgsmonitoring.components.USGSMonitoringApplicationScope;
import com.dzhtv.izhut.usgsmonitoring.services.ProviderGoogleAPI;
import com.dzhtv.izhut.usgsmonitoring.views.EarthquakeMvpView;
import com.dzhtv.izhut.usgsmonitoring.presenters.EarthquakePresenter;
import com.dzhtv.izhut.usgsmonitoring.presenters.MarsRoverPresenter;
import com.dzhtv.izhut.usgsmonitoring.views.WeatherMvpView;
import com.dzhtv.izhut.usgsmonitoring.presenters.WeatherPresenter;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context _context;

    public AppModule(Context context){
        this._context = context;
    }

    @USGSMonitoringApplicationScope
    @Provides
    public ProviderGoogleAPI provideGoogleApi(){
        return new ProviderGoogleAPI(_context);
    }

    @USGSMonitoringApplicationScope
    @Provides
    public Picasso providePicasso(){
        return Picasso.get();
    }

    @USGSMonitoringApplicationScope
    @Provides
    public EarthquakePresenter<EarthquakeMvpView> getEarthquakePresenter(){
        return new EarthquakePresenter<EarthquakeMvpView>();
    }

    @USGSMonitoringApplicationScope
    @Provides
    public WeatherPresenter<WeatherMvpView> getWeatherPresenter(){
        return new WeatherPresenter<WeatherMvpView>();
    }

    @USGSMonitoringApplicationScope
    @Provides
    public MarsRoverPresenter<MarsRoverView> getOtherPresenter(){
        return new MarsRoverPresenter<MarsRoverView>();
    }

    @USGSMonitoringApplicationScope
    @Provides
    public NasaApodPresenter<NasaApodView> getNasaApodPresenter() {
        return new NasaApodPresenter<NasaApodView>();
    }
}

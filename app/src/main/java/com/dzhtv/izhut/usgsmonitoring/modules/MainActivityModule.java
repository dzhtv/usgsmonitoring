package com.dzhtv.izhut.usgsmonitoring.modules;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.dzhtv.izhut.usgsmonitoring.OtherFragment;
import com.dzhtv.izhut.usgsmonitoring.Utils;
import com.dzhtv.izhut.usgsmonitoring.dialog.ActivateGPSDialog;
import com.dzhtv.izhut.usgsmonitoring.ui.earthquake.EarthquakeFragment;
import com.dzhtv.izhut.usgsmonitoring.MainActivity;
import com.dzhtv.izhut.usgsmonitoring.WeatherFragment;
import com.dzhtv.izhut.usgsmonitoring.components.USGSMonitoringApplicationScope;
import com.dzhtv.izhut.usgsmonitoring.ui.earthquake.EarthquakeMvpView;
import com.dzhtv.izhut.usgsmonitoring.ui.earthquake.EarthquakePresenter;
import com.dzhtv.izhut.usgsmonitoring.ui.main.MainMvpPresenter;
import com.dzhtv.izhut.usgsmonitoring.ui.main.MainMvpView;
import com.dzhtv.izhut.usgsmonitoring.ui.main.MainPresenter;
import com.dzhtv.izhut.usgsmonitoring.ui.other.OtherPresenter;
import com.dzhtv.izhut.usgsmonitoring.ui.other.OtherView;
import com.dzhtv.izhut.usgsmonitoring.ui.weather.WeatherMvpView;
import com.dzhtv.izhut.usgsmonitoring.ui.weather.WeatherPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {
    private Context _context;

    public MainActivityModule(MainActivity activity){
        this._context = activity;
    }

    @Provides
    public Utils provideUtils(){
        return new Utils(_context);
    }
    @Provides
    @USGSMonitoringApplicationScope
    public MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> presenter){
        return presenter;
    }

    @USGSMonitoringApplicationScope
    @Provides
    public EarthquakeFragment earthquakeFragment(){
        return new EarthquakeFragment();
    }

    @USGSMonitoringApplicationScope
    @Provides
    public WeatherFragment weatherFragment(){
        return new WeatherFragment();
    }

    @USGSMonitoringApplicationScope
    @Provides
    public OtherFragment otherFragment(){
        return new OtherFragment();
    }

    @Provides
    public AlertDialog.Builder builderAlertDialog(){
        return new AlertDialog.Builder(_context);
    }

    @Named("activity_context")
    @USGSMonitoringApplicationScope
    @Provides
    public Context context(){
        return _context;
    }

    @Provides
    public EarthquakePresenter<EarthquakeMvpView> getEarthquakePresenter(){
        return new EarthquakePresenter<EarthquakeMvpView>();
    }

    @Provides
    public WeatherPresenter<WeatherMvpView> getWeatherPresenter(){
        return new WeatherPresenter<WeatherMvpView>();
    }

    @USGSMonitoringApplicationScope
    @Provides
    public OtherPresenter<OtherView> getOtherPresenter(){
        return new OtherPresenter<OtherView>();
    }

    @Provides
    public ActivateGPSDialog provideActivateGPSDialog(){
        ActivateGPSDialog dialog =  new ActivateGPSDialog(_context);
        return dialog;
    }

}

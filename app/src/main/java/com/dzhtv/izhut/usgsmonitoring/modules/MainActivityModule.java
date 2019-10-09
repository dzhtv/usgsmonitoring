package com.dzhtv.izhut.usgsmonitoring.modules;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.dzhtv.izhut.usgsmonitoring.NasaFragment;
import com.dzhtv.izhut.usgsmonitoring.MarsRoverFragment;
import com.dzhtv.izhut.usgsmonitoring.Utils;
import com.dzhtv.izhut.usgsmonitoring.dialog.ActivateGPSDialog;
import com.dzhtv.izhut.usgsmonitoring.EarthquakeFragment;
import com.dzhtv.izhut.usgsmonitoring.MainActivity;
import com.dzhtv.izhut.usgsmonitoring.WeatherFragment;
import com.dzhtv.izhut.usgsmonitoring.components.USGSMonitoringApplicationScope;
import com.dzhtv.izhut.usgsmonitoring.contracts.MainMvpPresenter;
import com.dzhtv.izhut.usgsmonitoring.views.MainMvpView;
import com.dzhtv.izhut.usgsmonitoring.presenters.MainPresenter;

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
        return EarthquakeFragment.getInstance();
    }

    @USGSMonitoringApplicationScope
    @Provides
    public WeatherFragment weatherFragment(){
        return WeatherFragment.getInstance();
    }

    @USGSMonitoringApplicationScope
    @Provides
    public MarsRoverFragment otherFragment(){
        return MarsRoverFragment.getInstance();
    }

    @USGSMonitoringApplicationScope
    @Provides
    public NasaFragment nasaFragment(){
        return NasaFragment.newInstance();
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
    public ActivateGPSDialog provideActivateGPSDialog(){
        ActivateGPSDialog dialog =  new ActivateGPSDialog(_context);
        return dialog;
    }

}

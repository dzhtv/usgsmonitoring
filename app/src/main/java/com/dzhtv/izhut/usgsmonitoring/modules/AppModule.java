package com.dzhtv.izhut.usgsmonitoring.modules;

import android.content.Context;

import com.dzhtv.izhut.usgsmonitoring.components.USGSMonitoringApplicationScope;
import com.dzhtv.izhut.usgsmonitoring.services.ProviderGoogleAPI;
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
}

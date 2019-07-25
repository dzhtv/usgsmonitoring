package com.dzhtv.izhut.usgsmonitoring.components;

import android.content.Context;

import com.dzhtv.izhut.usgsmonitoring.modules.AppModule;
import com.dzhtv.izhut.usgsmonitoring.services.ProviderGoogleAPI;
import com.squareup.picasso.Picasso;

import dagger.Component;

@USGSMonitoringApplicationScope
@Component(modules = {AppModule.class})
public interface ApplicationComponent {
    void inject(Context context);
    Picasso getPicasso();
    ProviderGoogleAPI getProviderGoogleApi();
}

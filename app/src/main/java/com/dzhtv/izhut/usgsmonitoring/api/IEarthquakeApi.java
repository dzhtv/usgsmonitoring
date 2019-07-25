package com.dzhtv.izhut.usgsmonitoring.api;

import com.dzhtv.izhut.usgsmonitoring.models.earthquake.EarthquakeData;

import retrofit2.http.GET;
import rx.Observable;


public interface IEarthquakeApi {
    @GET("query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10")
    Observable<EarthquakeData> getEarthquakeData();
}

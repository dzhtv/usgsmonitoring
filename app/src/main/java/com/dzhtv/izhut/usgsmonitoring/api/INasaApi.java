package com.dzhtv.izhut.usgsmonitoring.api;

import com.dzhtv.izhut.usgsmonitoring.models.nasa.NasaRoverData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface INasaApi {
    @GET("rovers/curiosity/photos")
    Observable<NasaRoverData> loadPhotosFromRover(@Query("sol") int sol, @Query("api_key") String api_key, @Query("page") int page);
}

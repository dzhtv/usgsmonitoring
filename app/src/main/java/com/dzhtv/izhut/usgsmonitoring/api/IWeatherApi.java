package com.dzhtv.izhut.usgsmonitoring.api;

import com.dzhtv.izhut.usgsmonitoring.models.weather.WeatherData;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface IWeatherApi {
    @GET("weather")
    Observable<WeatherData> getByCityName(@Query("q") String city_name, @Query("appid") String weather_key, @Query("units") String units);

    @GET("weather")
    Observable<WeatherData> getByCoords(@Query("lat") double lat, @Query("lon") double lon, @Query("appid") String weather_key, @Query("units") String units);
}

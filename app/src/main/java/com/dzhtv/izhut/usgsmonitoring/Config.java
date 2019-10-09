package com.dzhtv.izhut.usgsmonitoring;

import android.Manifest;

public class Config {
    public static final String EARTHQUAKE_URL = "http://earthquake.usgs.gov/fdsnws/event/1/";
    public static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String NASA_URL_MARS_PHOTOS = "https://api.nasa.gov/";
    public static final String NASA_KEY_API = "16bEOFmBcWojLoZCTbaLm1dJeEn3bU90Lq1qYm3p";
    public static final String WEATHER_UNITS = "metric";
    public static final String WEATHER_KEY_APPID = "95318b6199264ebf2a3cca174852d1de";
    public static final String preferencesLocation = "last_location";
    public static String[] appPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};
    public static final int REQUEST_PERMISSION_CODE = 1717;
    public static final int REQUEST_ON_GPS_IN_SETTINGS = 3033;
    public static final String SUNSET_VISIBLE_FLAG = "sunset_flag";


}

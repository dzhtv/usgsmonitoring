package com.dzhtv.izhut.usgsmonitoring;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.location.LocationServices;

import java.util.List;

public class Utils {
    private Context _context;

    public Utils(Context context){
        this._context = context;
    }
    /**
     * Сохранение локально объекта Gson
     * @param mContext
     * @param nameSettings - имя файла настроек
     * @param body - строка тела Gson
     */
    public static void saveGsonToPreferences(Context mContext, String nameSettings, String body){
        SharedPreferences pref  = mContext.getSharedPreferences(nameSettings, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(nameSettings, body);
        editor.apply();
    }

    /**
     * Возвращает сохраненный объект в строке
     * @param mContext
     * @param nameSettings
     * @return
     */
    public static String getSavedGsonString(Context mContext, String nameSettings){
        String body = "";
        SharedPreferences pref  = mContext.getSharedPreferences(nameSettings, Context.MODE_PRIVATE);
        if (pref.contains(nameSettings)){
            return body = pref.getString(nameSettings, "");
        }
        return body;
    }

    /**
     * Сохранение последней локации локально
     * @param context
     * @param location
     */
    public static void saveCurrentLocation(Context context, Location location){
        SharedPreferences.Editor preferences = context.getSharedPreferences(Config.preferencesLocation, Context.MODE_PRIVATE).edit();
        preferences.putString("lat", String.valueOf(location.getLatitude()));
        preferences.putString("lng", String.valueOf(location.getLongitude()));
        preferences.apply();
    }

    /**
     * Получение последней известной локации приложению
     * @param context
     * @return
     */
    public static Location getSavedLocation(Context context){
        Location _loc = new Location("start_location");
        _loc.setLatitude(55.753271);
        _loc.setLongitude(37.623628); //custom loc
        SharedPreferences editor = context.getSharedPreferences(Config.preferencesLocation, Context.MODE_PRIVATE);
        if (editor.contains("lat") && editor.contains("lng")){
            _loc.setLatitude(Double.parseDouble(editor.getString("lat", "")));
            _loc.setLongitude(Double.parseDouble(editor.getString("lng", "")));
        }
        return _loc;
    }

    /**
     * Проверка наличия GPS на устройстве
     * @param context
     * @return
     */
    public static boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        if ( mgr == null )
            return false;
        final List<String> providers = mgr.getAllProviders();
        if ( providers == null )
            return false;

        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    /**
     * Проверка активности GPS
     * @param context
     * @return
     */
    public static boolean checkGPS(Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public boolean checkActivateGPS(){
        if (hasGPSDevice(_context)){
            if (checkGPS(_context))
                return true;
            else
                return false;
        }else {
            return false;
        }
    }

}

package com.dzhtv.izhut.usgsmonitoring.api;


import android.util.Log;

import com.dzhtv.izhut.usgsmonitoring.App;
import com.dzhtv.izhut.usgsmonitoring.callbacks.EarthquakesCallback;
import com.dzhtv.izhut.usgsmonitoring.models.earthquake.EarthquakeData;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EarthquakeApi  {
    private static EarthquakeApi instance;
    private IEarthquakeApi earthAPI = App.getEarthquakeApi();

    private EarthquakeApi(){}

    public static EarthquakeApi getInstance(){
        if (instance == null)
            instance = new EarthquakeApi();
        return instance;
    }


    public void loadEarthquakes(EarthquakesCallback callback){
        Observable<EarthquakeData> dataObservable = earthAPI.getEarthquakeData();
        dataObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EarthquakeData>() {
                    @Override
                    public void onCompleted() {
                        Log.d(App.TAG, "Earthquakes load onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(App.TAG, "Earthquakes load onError");
                        if (e instanceof HttpException){
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            Log.d(App.TAG, makeJSONString(body));
                        }
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(EarthquakeData earthquakeData) {
                        callback.onSuccess(earthquakeData);
                        Log.d(App.TAG, "Earthquakes load onNext");
                    }
                });
    }

    private String makeJSONString(ResponseBody body){
        try {
            JSONObject obj = new JSONObject(body.string());
            return obj.getString("message");
        }catch (Exception e){
            return e.getMessage();
        }
    }
}

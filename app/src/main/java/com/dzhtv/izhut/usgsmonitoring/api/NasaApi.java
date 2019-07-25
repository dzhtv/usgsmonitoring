package com.dzhtv.izhut.usgsmonitoring.api;

import android.util.Log;

import com.dzhtv.izhut.usgsmonitoring.App;
import com.dzhtv.izhut.usgsmonitoring.callbacks.NasaDataRoverCallback;
import com.dzhtv.izhut.usgsmonitoring.models.nasa.NasaRoverData;
import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NasaApi {
    private static NasaApi instance;
    private INasaApi service;

    private NasaApi(){
        service = App.getNasaApi();
    }

    public static NasaApi getInstance(){
        if (instance == null)
            instance = new NasaApi();
        return instance;
    }

    public void loadPhotosFromRover(int sol, String apiKey, NasaDataRoverCallback callback){
        if (callback == null)
            return;
        Observable<NasaRoverData> _observable = service.loadPhotosFromRover(sol, apiKey, 1);
        _observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NasaRoverData>() {
                    @Override
                    public void onCompleted() {
                        Log.d(App.TAG, "Load photos onCompleted");
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException){
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            Log.d(App.TAG, makeJSONString(body));
                        }
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(NasaRoverData nasaRoverData) {
                        Gson gson = new Gson();
                        Log.d(App.TAG, "Load photos from rover onNext " + gson.toJson(nasaRoverData, NasaRoverData.class));
                        callback.onSuccessRoverData(nasaRoverData);
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

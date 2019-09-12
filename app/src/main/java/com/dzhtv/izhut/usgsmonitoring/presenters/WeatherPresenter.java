package com.dzhtv.izhut.usgsmonitoring.presenters;

import android.util.Log;

import com.dzhtv.izhut.usgsmonitoring.App;
import com.dzhtv.izhut.usgsmonitoring.api.WeatherApi;
import com.dzhtv.izhut.usgsmonitoring.callbacks.WeatherCallback;
import com.dzhtv.izhut.usgsmonitoring.contracts.WeatherMvpPresenter;
import com.dzhtv.izhut.usgsmonitoring.models.weather.WeatherData;
import com.dzhtv.izhut.usgsmonitoring.ui.base.BasePresenter;
import com.dzhtv.izhut.usgsmonitoring.views.WeatherMvpView;

import java.util.List;


public class WeatherPresenter<V extends WeatherMvpView> extends BasePresenter<V> implements WeatherMvpPresenter<V> {
    private WeatherApi _api = WeatherApi.getInstance();
    private List<String> neededPermissions;
    private WeatherData currentData;

    @Override
    public void setNeededPermissions(List<String> items) {
        neededPermissions = items;
    }


    @Override
    public void loadWeatherByName(String cityName) {
        _api.loadByCityName(cityName, new WeatherCallback() {
            @Override
            public void onError(String errorMsg) {
                getMvpView().hideLoadingIndicator();
                getMvpView().onShowErrorMessage(errorMsg);
            }

            @Override
            public void onSuccess(WeatherData data) {
                if (data == null)
                    return;
                getMvpView().hideLoadingIndicator();
                getMvpView().setTemp(data.getMain().getTemp().intValue());
                getMvpView().setMinTemp(data.getMain().getTempMin().intValue());
                getMvpView().setMaxTemp(data.getMain().getTempMax().intValue());
                getMvpView().setHumidity(data.getMain().getHumidity());
                getMvpView().setPressure(data.getMain().getPressure());
                getMvpView().setDescription(data.getWeather().get(0).getDescription());
            }
        });
    }

    @Override
    public void clickRequestPermissions() {
        if (!neededPermissions.isEmpty()){
            getMvpView().onRequestPermissions(neededPermissions);
        }
    }

    @Override
    public void loadByCoords(double lat, double lng, boolean isRefresh) {
        if (currentData != null && !isRefresh){
            Log.d(App.TAG, "WeatherPresenter, get current data");
            initResponseToViews(currentData);
        }else{
            Log.d(App.TAG, "WeatherPresenter, load new data");
            _api.loadByCoords(lat, lng, new WeatherCallback() {
                @Override
                public void onError(String errorMsg) {
                    getMvpView().onShowErrorMessage(errorMsg);
                }

                @Override
                public void onSuccess(WeatherData data) {
                    if (data == null)
                        return;
                    currentData = data;
                    getMvpView().hideLoadingIndicator();
                    initResponseToViews(data);
                }
            });
        }
    }

    private void initResponseToViews(WeatherData data){
        getMvpView().hideLoadingIndicator();
        getMvpView().setTemp(data.getMain().getTemp().intValue());
        getMvpView().setMinTemp(data.getMain().getTempMin().intValue());
        getMvpView().setMaxTemp(data.getMain().getTempMax().intValue());
        getMvpView().setHumidity(data.getMain().getHumidity());
        getMvpView().setPressure(data.getMain().getPressure());
        getMvpView().setDescription(data.getWeather().get(0).getDescription());
    }
}

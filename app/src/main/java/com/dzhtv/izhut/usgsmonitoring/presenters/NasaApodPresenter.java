package com.dzhtv.izhut.usgsmonitoring.presenters;

import android.util.Log;

import com.dzhtv.izhut.usgsmonitoring.App;
import com.dzhtv.izhut.usgsmonitoring.Config;
import com.dzhtv.izhut.usgsmonitoring.contracts.NasaApodMvpPresenter;
import com.dzhtv.izhut.usgsmonitoring.views.NasaApodView;
import com.dzhtv.izhut.usgsmonitoring.api.NasaApi;
import com.dzhtv.izhut.usgsmonitoring.callbacks.SimpleCallback;
import com.dzhtv.izhut.usgsmonitoring.models.nasa.NasaApodResponse;
import com.dzhtv.izhut.usgsmonitoring.ui.base.BasePresenter;

public class NasaApodPresenter<V extends NasaApodView> extends BasePresenter<V> implements NasaApodMvpPresenter<V> {
    private NasaApi nApi;
    private NasaApodResponse currentData;


    @Override
    public void init() {
        if (nApi == null)
            nApi = NasaApi.getInstance();
    }

    @Override
    public void loadApod(boolean isUpdate) {
        if (currentData != null){
            Log.d(App.TAG, "APOD, have saved data!");
            initResponse(currentData);
        }else {
            boolean isConnect = getMvpView().checkConnection();
            if (!isConnect){
                getMvpView().showNoConnectingMessage();
            }else {
                getMvpView().showLoadingIndicator();
                nApi.loadApod(Config.NASA_KEY_API, new SimpleCallback<NasaApodResponse>() {
                    @Override
                    public void onError(String errorMsg) {
                        getMvpView().hideLoadingIndicator();
                        getMvpView().showErrorMsg(errorMsg);
                    }

                    @Override
                    public void onSuccess(NasaApodResponse data) {
                        initResponse(data);
                        currentData = data;
                    }

                    @Override
                    public void onCompleted() {
                        getMvpView().hideLoadingIndicator();
                    }
                });
            }
        }
    }

    private void initResponse(NasaApodResponse data){
        if (data != null){
            getMvpView().hideLoadingIndicator();
            getMvpView().onSetImage(data.getUrl());
            getMvpView().onSetTitle(data.getTitle());
            getMvpView().onSetDate(data.getDate());
            getMvpView().onSetDescription(data.getExplanation());
        }
    }

    @Override
    public void clickOnPhoto() {
        if (currentData != null){
            getMvpView().openHDPicture(currentData.getHdurl());
        }
    }
}

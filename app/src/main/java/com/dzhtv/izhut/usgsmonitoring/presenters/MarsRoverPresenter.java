package com.dzhtv.izhut.usgsmonitoring.presenters;

import android.util.Log;

import com.dzhtv.izhut.usgsmonitoring.App;
import com.dzhtv.izhut.usgsmonitoring.Config;
import com.dzhtv.izhut.usgsmonitoring.api.NasaApi;
import com.dzhtv.izhut.usgsmonitoring.callbacks.NasaDataRoverCallback;
import com.dzhtv.izhut.usgsmonitoring.contracts.MarsRoverMvpPresenter;
import com.dzhtv.izhut.usgsmonitoring.models.nasa.NasaRoverData;
import com.dzhtv.izhut.usgsmonitoring.models.nasa.Photo;
import com.dzhtv.izhut.usgsmonitoring.ui.base.BasePresenter;
import com.dzhtv.izhut.usgsmonitoring.views.MarsRoverView;

import java.util.ArrayList;
import java.util.List;

public class MarsRoverPresenter<V extends MarsRoverView> extends BasePresenter<V> implements MarsRoverMvpPresenter<V> {
    private NasaApi _nasaApi;
    private List<Photo> _photos;

    @Override
    public void init() {
        _nasaApi = NasaApi.getInstance();
        if (_photos != null && _photos.size() > 0){
            Log.d(App.TAG, "MarsRoverPresenter, уже есть данные");
            getMvpView().setPhotosList(_photos);
        }
    }

    @Override
    public void clickSearchBtn() {
        _photos = new ArrayList<>();
        int sol = Integer.parseInt(getMvpView().getSolField().trim());
        if (getMvpView().getSolField().trim().length()<=4){
            getMvpView().showProgress();
            _nasaApi.loadPhotosFromRover(sol, Config.NASA_KEY_API, new NasaDataRoverCallback() {
                @Override
                public void onError(String errorMsg) {
                    getMvpView().hideProgress();
                    getMvpView().showToastMessage(errorMsg);
                }

                @Override
                public void onSuccessRoverData(NasaRoverData data) {
                    getMvpView().hideProgress();

                    if (data != null){
                        _photos = data.getPhotos();
                        if (_photos.size() == 0)
                            getMvpView().showEmptyResult();
                        else
                            getMvpView().setPhotosList(data.getPhotos());
                    }
                }

                @Override
                public void onCompleted() {
                    getMvpView().hideProgress();
                }
            });
        }
    }
}

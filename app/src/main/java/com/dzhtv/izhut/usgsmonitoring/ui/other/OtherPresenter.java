package com.dzhtv.izhut.usgsmonitoring.ui.other;

import com.dzhtv.izhut.usgsmonitoring.App;
import com.dzhtv.izhut.usgsmonitoring.Config;
import com.dzhtv.izhut.usgsmonitoring.api.NasaApi;
import com.dzhtv.izhut.usgsmonitoring.callbacks.NasaDataRoverCallback;
import com.dzhtv.izhut.usgsmonitoring.models.nasa.NasaRoverData;
import com.dzhtv.izhut.usgsmonitoring.models.nasa.Photo;
import com.dzhtv.izhut.usgsmonitoring.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class OtherPresenter<V extends OtherView> extends BasePresenter<V> implements OtherMvpPresenter<V>{
    private NasaApi _nasaApi;
    private List<Photo> _photos;

    @Override
    public void init() {
        _nasaApi = NasaApi.getInstance();
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

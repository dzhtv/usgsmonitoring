package com.dzhtv.izhut.usgsmonitoring.presenters;

import android.util.Log;

import com.dzhtv.izhut.usgsmonitoring.App;
import com.dzhtv.izhut.usgsmonitoring.contracts.EarthquakeMvpPresenter;
import com.dzhtv.izhut.usgsmonitoring.ui.base.BasePresenter;
import com.dzhtv.izhut.usgsmonitoring.callbacks.EarthquakesCallback;
import com.dzhtv.izhut.usgsmonitoring.api.EarthquakeApi;
import com.dzhtv.izhut.usgsmonitoring.models.earthquake.EarthquakeData;
import com.dzhtv.izhut.usgsmonitoring.models.earthquake.Feature;
import com.dzhtv.izhut.usgsmonitoring.views.EarthquakeMvpView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakePresenter<V extends EarthquakeMvpView> extends BasePresenter<V> implements EarthquakeMvpPresenter<V> {

    private EarthquakeApi _earthquakeApi;
    List<Feature> earthquakes = new ArrayList<>();

    @Override
    public void init() {
        _earthquakeApi = EarthquakeApi.getInstance();

    }

    @Override
    public void getEarthquakesData(boolean isUpdate) {
        if (!isUpdate && earthquakes != null && earthquakes.size() > 0){
            Log.d(App.TAG, "EarthquakePresenter, get current data");
            getMvpView().hideLoadingIndicator();
            getMvpView().setEarthquakesListViewData(earthquakes);
        }else {
            Log.d(App.TAG, "EarthquakePresenter, load new data");
            _earthquakeApi.loadEarthquakes(new EarthquakesCallback() {
                @Override
                public void onError(String errorMsg) {
                    getMvpView().hideLoadingIndicator();
                    getMvpView().errorMessage(errorMsg);
                    Log.e(App.TAG, errorMsg);
                }
                @Override
                public void onSuccess(EarthquakeData data) {
                    earthquakes = null;
                    earthquakes = new ArrayList<>();
                    earthquakes.addAll(data.getFeatures());

                    getMvpView().hideLoadingIndicator();
                    if (earthquakes.isEmpty())
                        getMvpView().setEmptyResponseText();
                    else if(isUpdate){
                        getMvpView().updateEarthquakesListView(earthquakes);
                    }
                    else
                        getMvpView().setEarthquakesListViewData(earthquakes);
                }
            });
        }
    }
}

package com.dzhtv.izhut.usgsmonitoring.ui.earthquake;

import com.dzhtv.izhut.usgsmonitoring.App;
import com.dzhtv.izhut.usgsmonitoring.R;
import com.dzhtv.izhut.usgsmonitoring.ui.base.BasePresenter;
import com.dzhtv.izhut.usgsmonitoring.ui.earthquake.EarthquakeMvpPresenter;
import com.dzhtv.izhut.usgsmonitoring.ui.earthquake.EarthquakeMvpView;
import com.dzhtv.izhut.usgsmonitoring.callbacks.EarthquakesCallback;
import com.dzhtv.izhut.usgsmonitoring.api.EarthquakeApi;
import com.dzhtv.izhut.usgsmonitoring.models.earthquake.EarthquakeData;
import com.dzhtv.izhut.usgsmonitoring.models.earthquake.Feature;

import java.util.ArrayList;
import java.util.List;


public class EarthquakePresenter<V extends EarthquakeMvpView> extends BasePresenter<V> implements EarthquakeMvpPresenter<V> {
    private EarthquakeApi _earthquakeApi;

    @Override
    public void init() {
        _earthquakeApi = EarthquakeApi.getInstance();
    }

    @Override
    public void getEarthquakesData(boolean isUpdate) {
        _earthquakeApi.loadEarthquakes(new EarthquakesCallback() {
            @Override
            public void onError(String errorMsg) {

            }
            @Override
            public void onSuccess(EarthquakeData data) {
                List<Feature> earthquakes = new ArrayList<>();
                earthquakes.addAll(data.getFeatures());
                getMvpView().hideLoadingIndicator();
                if (earthquakes.isEmpty())
                    getMvpView().setEmptyResponseText();
                else if(isUpdate)
                    getMvpView().updateEarthquakesListView(earthquakes);
                else
                    getMvpView().setEarthquakesListViewData(earthquakes);
            }
        });
    }
}

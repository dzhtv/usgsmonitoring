package com.dzhtv.izhut.usgsmonitoring.ui.earthquake;

import com.dzhtv.izhut.usgsmonitoring.models.earthquake.Feature;
import com.dzhtv.izhut.usgsmonitoring.ui.base.MvpView;

import java.util.List;

public interface EarthquakeMvpView extends MvpView {
    void setEarthquakesListViewData(List<Feature> earthquakes);
    void updateEarthquakesListView(List<Feature> earthquakes);
    void setEmptyResponseText();
    void hideLoadingIndicator();
    void showNoConnectionMessage();
}

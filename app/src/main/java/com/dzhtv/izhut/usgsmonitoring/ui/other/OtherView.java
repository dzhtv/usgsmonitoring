package com.dzhtv.izhut.usgsmonitoring.ui.other;

import com.dzhtv.izhut.usgsmonitoring.models.nasa.Photo;
import com.dzhtv.izhut.usgsmonitoring.ui.base.MvpView;

import java.util.List;

public interface OtherView extends MvpView {
    String getSolField();
    void showToastMessage(String message);
    void setPhotosList(List<Photo> items);
    void showEmptyResult();
    void showProgress();
    void hideProgress();
}

package com.dzhtv.izhut.usgsmonitoring.ui.base;

import javax.inject.Inject;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {
    private V _view;

    @Inject
    public BasePresenter(){}

    @Override
    public void onAttach(V mvpView) {
        _view = mvpView;
    }

    @Override
    public void onDetach() {
        _view = null;
    }

    public boolean isViewAttached(){
        return _view != null;
    }

    public V getMvpView(){
        return _view;
    }
}

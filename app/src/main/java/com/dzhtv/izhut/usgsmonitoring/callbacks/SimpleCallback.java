package com.dzhtv.izhut.usgsmonitoring.callbacks;

public interface SimpleCallback<T> {
    void onError(String errorMsg);
    void onSuccess(T data);
    void onCompleted();
}

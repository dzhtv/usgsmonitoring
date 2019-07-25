package com.dzhtv.izhut.usgsmonitoring.models.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain {
    @SerializedName("1h")
    @Expose
    private Integer _1h;

    @SerializedName("3h")
    @Expose
    private Integer _3h;

    public Integer get_1h() {
        return _1h;
    }

    public void set_1h(Integer _1h) {
        this._1h = _1h;
    }

    public Integer get_3h() {
        return _3h;
    }

    public void set_3h(Integer _3h) {
        this._3h = _3h;
    }
}

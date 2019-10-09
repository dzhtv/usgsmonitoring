package com.dzhtv.izhut.usgsmonitoring.models;

import android.content.Context;

import com.dzhtv.izhut.usgsmonitoring.R;

import java.util.ArrayList;
import java.util.List;

public class NasaCardView {
    private String title,  description;
    private int photo;
    private List<NasaCardView> items;

    public NasaCardView(String title,  String desc, int picture){
        this.title = title;
        this.description = desc;
        this.photo = picture;
    }


    /**
     * Hard init items for Nasa card view
     * @return
     */
    public static List<NasaCardView> initItems(Context ctx){
        List<NasaCardView> _items = new ArrayList<>();
        _items.add(new NasaCardView(ctx.getString(R.string.nasa_apod),  ctx.getString(R.string.apod_description), R.drawable.apod_2));
        _items.add(new NasaCardView(ctx.getString(R.string.mars_rovers_photos),  ctx.getString(R.string.mars_rover_description), R.drawable.rover));
        return _items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}

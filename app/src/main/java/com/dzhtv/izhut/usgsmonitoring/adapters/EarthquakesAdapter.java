package com.dzhtv.izhut.usgsmonitoring.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dzhtv.izhut.usgsmonitoring.R;
import com.dzhtv.izhut.usgsmonitoring.models.earthquake.Feature;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakesAdapter extends ArrayAdapter<Feature>{
    private final int placeStep = 2;
    private ViewHolder holder;
    private LayoutInflater _inflater;
    private int resource;
    private List<Feature> _items;

    public EarthquakesAdapter(Context context, int resource, List<Feature> earthquakes) {
        super(context, 0, earthquakes);
        this.resource = resource;
        this._items = earthquakes;
        this._inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = _inflater.inflate(this.resource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Feature earthquake = _items.get(position);
        String magnitude = earthquake.getProperties().getMag().toString();

        GradientDrawable magnitudeCircle = (GradientDrawable) holder.magnitude.getBackground();
        //int magnitudeColor = getMagnitudeColor(magnitude);
        magnitudeCircle.setColor(getMagnitudeColor(magnitude));

        holder.magnitude.setText(magnitude);

        String location = earthquake.getProperties().getPlace();
        int splitIndex = location.indexOf("of");
        if (splitIndex != -1 ){
            holder.locationKm.setText(location.substring(0, splitIndex + placeStep).trim());
            holder.locationPlace.setText(location.substring(splitIndex + placeStep).trim());
        } else {
            holder.locationKm.setText(getContext().getText(R.string.nearThe));
            holder.locationPlace.setText(location);
        }

        Date dateObject = new Date(earthquake.getProperties().getTime());
        holder.date.setText(formatDate(dateObject));
        holder.time.setText(formatTime(dateObject));

        return convertView;
    }

    private int getMagnitudeColor(String magnitude) {
        int colorResource = ContextCompat.getColor(getContext(), R.color.magnitude1);
        int magnitudeInt = (int) Math.floor(Double.parseDouble(magnitude));

        switch (magnitudeInt) {
            case 1:
                break;
            case 2:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;
            case 3:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case 4:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case 5:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case 6:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case 7:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case 8:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case 9:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;
            default:
                colorResource = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
        }

        return colorResource;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private class ViewHolder{
        private TextView magnitude, locationKm, locationPlace, date, time;

        public ViewHolder(View view){
            magnitude = view.findViewById(R.id.magnitude);
            locationKm =  view.findViewById(R.id.location_offset);
            locationPlace =  view.findViewById(R.id.primary_location);
            date =  view.findViewById(R.id.date);
            time =  view.findViewById(R.id.time);
        }
    }
}

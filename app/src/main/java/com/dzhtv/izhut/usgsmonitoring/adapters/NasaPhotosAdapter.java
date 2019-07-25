package com.dzhtv.izhut.usgsmonitoring.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dzhtv.izhut.usgsmonitoring.R;
import com.dzhtv.izhut.usgsmonitoring.models.nasa.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;



public class NasaPhotosAdapter extends RecyclerView.Adapter<NasaPhotosAdapter.ViewHolder> {
    private Context _context;
    private List<Photo> resultItems;


    public NasaPhotosAdapter(Context context, List<Photo> dataItems){
        this._context = context;
        this.resultItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nasa_rover_photo, parent, false);
        ViewHolder holder = new ViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo _photo = resultItems.get(position);
        holder.rover_name.setText(_photo.getRover().getName());
        holder.date_earth.setText(_photo.getEarthDate());
        Picasso.get().load(_photo.getImgSrc()).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return resultItems.size();
    }

    public Photo getItem(int position){
        return resultItems.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView rover_name, date_earth;
        ImageView photo;


        public ViewHolder(View view){
            super(view);
            rover_name = (TextView)view.findViewById(R.id.rover_name);
            date_earth = (TextView)view.findViewById(R.id.date_earth);
            photo = (ImageView)view.findViewById(R.id.photo);
        }
    }
}

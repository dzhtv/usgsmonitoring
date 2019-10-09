package com.dzhtv.izhut.usgsmonitoring.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dzhtv.izhut.usgsmonitoring.R;
import com.dzhtv.izhut.usgsmonitoring.models.NasaCardView;

import java.util.List;

public class NasaCardAdapter extends RecyclerView.Adapter<NasaCardAdapter.CardHolder> {
    private List<NasaCardView> _cards;

    public NasaCardAdapter( List<NasaCardView> items){
        this._cards = items;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nasa_cardview, parent, false);
        return new CardHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        NasaCardView data = _cards.get(position);
        holder.thumbnail.setImageResource(data.getPhoto());
        holder.title.setText(data.getTitle());
        holder.description.setText(data.getDescription());
    }

    @Override
    public int getItemCount() {
        return _cards.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class CardHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView thumbnail;
        private TextView title, description;


        public CardHolder(View view){
            super(view);

            cardView = view.findViewById(R.id.card_view);
            thumbnail = view.findViewById(R.id.thumbnail);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
        }
    }
}

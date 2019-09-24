package com.dzhtv.izhut.usgsmonitoring;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class NasaFragment extends Fragment {
    private View rootView;
    private ViewHolder holder;
    private FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_nasa, container, false);
        holder = new ViewHolder(rootView);
        fm = getActivity().getSupportFragmentManager();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(App.TAG, "NasaFragment, onStart");
    }

    public class ViewHolder {
        private LinearLayout list;
        private TextView apod_item, rover_item;
        private FrameLayout container;

        public ViewHolder(View view){
            list = (LinearLayout)view.findViewById(R.id.list_linear);
            apod_item = (TextView)view.findViewById(R.id.apod_item);
            rover_item = (TextView)view.findViewById(R.id.rover_item);
            container = (FrameLayout)view.findViewById(R.id.nasa_fragment_container);


            apod_item.setOnClickListener(v -> {
                Log.d(App.TAG, "NasaFragment, apod click");
                hideListItems();
                openFragment(new NasaApodFragment());
            });

            rover_item.setOnClickListener(v -> {
                Log.d(App.TAG, "NasaFragment, rover click");
                hideListItems();
                openFragment(new MarsRoverFragment());
            });
        }

        public void hideListItems(){
            list.setVisibility(View.GONE);
        }

        public void showListItems(){
            list.setVisibility(View.VISIBLE);
        }

        public void openFragment(Fragment _frag){
            //add back stack
            fm.beginTransaction()
                    .replace(R.id.frame_container, _frag)
                    //.addToBackStack("1")
                    .commit();
        }
    }
}

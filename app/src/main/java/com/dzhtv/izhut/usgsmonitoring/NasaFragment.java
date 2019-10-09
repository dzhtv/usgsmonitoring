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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dzhtv.izhut.usgsmonitoring.adapters.NasaCardAdapter;
import com.dzhtv.izhut.usgsmonitoring.adapters.RecyclerItemClickListener;
import com.dzhtv.izhut.usgsmonitoring.models.NasaCardView;

import java.util.ArrayList;
import java.util.List;

public class NasaFragment extends Fragment {
    private static NasaFragment instance;

    private View rootView;
    private ViewHolder holder;
    private FragmentManager fm;
    private NasaCardAdapter cardAdapter;


    public static NasaFragment newInstance(){
        if (instance == null)
            instance = new NasaFragment();
        return instance;
    }

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
    }

    public class ViewHolder {
        private FrameLayout container;
        private RecyclerView recyclerView;

        public ViewHolder(View view){

            container = view.findViewById(R.id.nasa_fragment_container);
            recyclerView = view.findViewById(R.id.recycler_view_nasa);
            LinearLayoutManager lmng = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(lmng);
            cardAdapter = new NasaCardAdapter(NasaCardView.initItems(getActivity()));
            recyclerView.setAdapter(cardAdapter);
            cardAdapter.notifyDataSetChanged();

            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (cardAdapter.getItemCount() > 0){
                        hideListItems();
                        if (position == 0){
                            openFragment(NasaApodFragment.getInstance());
                        }else if(position == 1){
                            openFragment(MarsRoverFragment.getInstance());
                        }
                    }
                }

                @Override
                public void onLongItemClick(View view, int position) {

                }
            }));

        }

        public void hideListItems(){
            recyclerView.setVisibility(View.GONE);
        }

        public void showListItems(){
            recyclerView.setVisibility(View.VISIBLE);
        }

        public void openFragment(Fragment _frag){
            fm.beginTransaction()
                    .replace(R.id.frame_container, _frag)
                    .addToBackStack("2")
                    .commit();
        }
    }
}

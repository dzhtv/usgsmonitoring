package com.dzhtv.izhut.usgsmonitoring;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dzhtv.izhut.usgsmonitoring.adapters.NasaPhotosAdapter;
import com.dzhtv.izhut.usgsmonitoring.models.nasa.Photo;
import com.dzhtv.izhut.usgsmonitoring.ui.other.OtherPresenter;
import com.dzhtv.izhut.usgsmonitoring.ui.other.OtherView;

import java.util.List;


public class OtherFragment extends BaseFragment implements OtherView {
    private View rootView;
    private ViewHolder holder;
    private List<Photo> photoData;
    private NasaPhotosAdapter _adapter;
    private RecyclerView.LayoutManager lManager;

    OtherPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_other, container, false);
        presenter = ((MainActivity)getActivity()).getOtherPresenter();
        holder = new ViewHolder(rootView);

        presenter.onAttach(this);
        holder.photosList.setHasFixedSize(true);
        lManager = new LinearLayoutManager(getActivity());
        holder.photosList.setLayoutManager(lManager);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    public String getSolField() {
        return holder.solField.getText().toString();
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPhotosList(List<Photo> items) {
        Log.d(App.TAG, "setPhotosList");
        photoData = items;
        holder.emptyContainer.setVisibility(View.GONE);
        if (_adapter == null)
            _adapter = new NasaPhotosAdapter(getActivity(), items);
        holder.photosList.setAdapter(_adapter);
        holder.photosList.setVisibility(View.VISIBLE);

    }

    @Override
    public void showEmptyResult() {
        holder.emptyContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress() {
        holder.emptyContainer.setVisibility(View.GONE);
        holder.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        holder.progressBar.setVisibility(View.GONE);
    }

    private void hideKeyboard(){
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private class ViewHolder {
        private EditText solField;
        private Button searchBtn;
        private RecyclerView photosList;
        private ProgressBar progressBar;
        private LinearLayout emptyContainer;

        public ViewHolder(View view){
            solField = (EditText)view.findViewById(R.id.sol_field);
            searchBtn = (Button)view.findViewById(R.id.search_btn);
            photosList = (RecyclerView) view.findViewById(R.id.listViewRoverPhotos);
            progressBar = (ProgressBar)view.findViewById(R.id.loading_spinner);
            emptyContainer = (LinearLayout) view.findViewById(R.id.empty_container);

            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(App.TAG, "Search click");
                    hideKeyboard();
                    presenter.clickSearchBtn();
                }
            });
        }
    }
}

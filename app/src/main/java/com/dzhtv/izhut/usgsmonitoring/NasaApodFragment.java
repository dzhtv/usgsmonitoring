package com.dzhtv.izhut.usgsmonitoring;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dzhtv.izhut.usgsmonitoring.presenters.NasaApodPresenter;
import com.dzhtv.izhut.usgsmonitoring.views.NasaApodView;

public class NasaApodFragment extends BaseFragment implements NasaApodView {
    private View rootView;
    private ViewHolder holder;
    private NasaApodPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = ((App)getActivity().getApplication()).getNasaApodPresenter();
        rootView = inflater.inflate(R.layout.fragment_apod, container, false);
        holder = new ViewHolder(rootView);

        presenter.onAttach(this);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.init();
        presenter.loadApod(false);
    }

    @Override
    public boolean checkConnection() {
        if (checkInternetConnection())
            return true;
        else
            return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    public void hideLoadingIndicator() {
        holder.loadingBox.setVisibility(View.GONE);
        holder.apodContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingIndicator() {
        holder.loadingBox.setVisibility(View.VISIBLE);
        holder.apodContainer.setVisibility(View.GONE);
    }

    @Override
    public void showNoConnectingMessage() {
        holder.apodContainer.setVisibility(View.GONE);
        holder.errorConnectionContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSetImage(String url) {
        ((App)getActivity().getApplication()).getPicasso().load(url).into(holder.photo);
    }

    @Override
    public void onSetTitle(String title) {
        holder.title.setText("");
        holder.title.setText(title);
    }

    @Override
    public void onSetDescription(String desc) {
        holder.descriptions.setText("");
        holder.descriptions.setText(desc);
    }

    @Override
    public void onSetDate(String date) {
        holder.date.setText("");
        holder.date.setText(date);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        holder.apodContainer.setVisibility(View.GONE);
        holder.errorConnectionContainer.setVisibility(View.VISIBLE);
        holder.errorLabel.setText("");//по умолчанию сообщение об отсутствии интернета
        holder.errorLabel.setText(errorMsg);
    }

    @Override
    public void openHDPicture(String hdUrl) {
        Intent picture = new Intent().setAction(Intent.ACTION_VIEW);
        picture.setDataAndType(Uri.parse(hdUrl), "image/*");
        startActivity(picture);
    }

    public class ViewHolder{
        private TextView title, descriptions, date, errorLabel;
        private ImageView photo;
        private LinearLayout apodContainer, errorConnectionContainer;
        private ProgressBar progressBar;
        private RelativeLayout loadingBox;

        public ViewHolder(View view){
            photo = view.findViewById(R.id.photo);
            title = view.findViewById(R.id.title);
            descriptions = view.findViewById(R.id.descriptions);
            date = view.findViewById(R.id.date);
            apodContainer = view.findViewById(R.id.apod_content_container);
            errorConnectionContainer = view.findViewById(R.id.connection_container);
            errorLabel = view.findViewById(R.id.error_label);
            progressBar = view.findViewById(R.id.progress_bar);
            loadingBox = view.findViewById(R.id.loading_box);

            photo.setOnClickListener(v -> {
                presenter.clickOnPhoto();
            });
        }
    }
}

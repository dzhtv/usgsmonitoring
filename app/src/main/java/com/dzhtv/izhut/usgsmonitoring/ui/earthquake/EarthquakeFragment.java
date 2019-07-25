package com.dzhtv.izhut.usgsmonitoring.ui.earthquake;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.dzhtv.izhut.usgsmonitoring.BaseFragment;
import com.dzhtv.izhut.usgsmonitoring.MainActivity;
import com.dzhtv.izhut.usgsmonitoring.R;
import com.dzhtv.izhut.usgsmonitoring.adapters.EarthquakesAdapter;
import com.dzhtv.izhut.usgsmonitoring.models.earthquake.Feature;
import com.dzhtv.izhut.usgsmonitoring.ui.earthquake.EarthquakeMvpView;
import com.dzhtv.izhut.usgsmonitoring.ui.earthquake.EarthquakePresenter;

import java.util.List;



public class EarthquakeFragment extends BaseFragment implements EarthquakeMvpView {

    EarthquakePresenter presenter;
    private ConnectivityManager connMgr;
    private NetworkInfo networkInfo;
    private ViewHolder holder;
    private View rootView;
    private List<Feature> _earthquakes;
    private EarthquakesAdapter _adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_earthquakes, container, false);
        holder = new ViewHolder(rootView);

        presenter = ((MainActivity)getActivity()).getEarthquakePresenter();
        presenter.onAttach(this);

        presenter.init();

        holder._loadingIndicator.getIndeterminateDrawable().setColorFilter(ContextCompat
                .getColor(getActivity(), R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
        connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        //

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadEarthquakes();
    }

    private void loadEarthquakes(){
        presenter.getEarthquakesData(false);
        if (networkInfo != null && networkInfo.isConnected()) {
            holder._listView.setOnItemClickListener((adapterView, view, i, l) -> {
                String url = _earthquakes.get(i).getProperties().getUrl();
                Intent browseInterner = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
                startActivity(browseInterner);
            });
        }else {
            showNoConnectionMessage();
        }
    }

    @Override
    public void setEarthquakesListViewData(List<Feature> earthquakes) {
        _earthquakes = earthquakes;
        _adapter = new EarthquakesAdapter(getActivity(), _earthquakes);
        holder._listView.setAdapter(_adapter);
    }

    @Override
    public void updateEarthquakesListView(List<Feature> earthquakes) {
        _earthquakes = earthquakes;
        _adapter.notifyDataSetChanged();
    }

    @Override
    public void setEmptyResponseText() {
        holder._errorTextView.setText(getString(R.string.empty_earthquakes));
    }

    @Override
    public void hideLoadingIndicator() {
        holder._loadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showNoConnectionMessage() {
        holder._loadingIndicator.setVisibility(View.GONE);
        holder._errorTextView.setText(R.string.no_internet_connection);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    private class ViewHolder{
        private ListView _listView;
        private TextView _errorTextView;
        private ProgressBar _loadingIndicator;

        public ViewHolder(View view){
            _listView = (ListView) view.findViewById(R.id.earthquakes_list_view);
            _errorTextView = (TextView) view.findViewById(R.id.error_txt);
            _loadingIndicator = (ProgressBar) view.findViewById(R.id.loading_spinner);
            _listView.setEmptyView(_errorTextView);
        }
    }
}

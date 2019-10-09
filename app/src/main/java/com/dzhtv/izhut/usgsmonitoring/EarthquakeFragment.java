package com.dzhtv.izhut.usgsmonitoring;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dzhtv.izhut.usgsmonitoring.R;
import com.dzhtv.izhut.usgsmonitoring.adapters.EarthquakesAdapter;
import com.dzhtv.izhut.usgsmonitoring.models.earthquake.Feature;
import com.dzhtv.izhut.usgsmonitoring.presenters.EarthquakePresenter;
import com.dzhtv.izhut.usgsmonitoring.views.EarthquakeMvpView;

import java.util.List;


public class EarthquakeFragment extends BaseFragment implements EarthquakeMvpView {

    private static EarthquakeFragment instance;
    EarthquakePresenter presenter;
    private ViewHolder holder;
    private View rootView;
    private List<Feature> _earthquakes;
    private EarthquakesAdapter _adapter;


    public static EarthquakeFragment getInstance(){
        if (instance == null)
            instance = new EarthquakeFragment();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_earthquakes, container, false);
        holder = new ViewHolder(rootView);

        presenter = ((App)getActivity().getApplication()).getEarthquakePresenter();
        presenter.onAttach(this);
        presenter.init();
        holder._loadingIndicator.getIndeterminateDrawable().setColorFilter(ContextCompat
                .getColor(getActivity(), R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadEarthquakes(false);
    }

    private void loadEarthquakes(boolean isUpdate){
        if (checkInternetConnection())
            presenter.getEarthquakesData(isUpdate);// загрузка данных
        else
            showNoConnectionMessage();
    }


    @Override
    public void setEarthquakesListViewData(List<Feature> earthquakes) {
        _earthquakes = earthquakes;
        _adapter = new EarthquakesAdapter(getActivity(), R.layout.earthquake_item, _earthquakes);
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
    public void errorMessage(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    private class ViewHolder{
        private SwipeRefreshLayout _swipe;
        private ListView _listView;
        private TextView _errorTextView;
        private ProgressBar _loadingIndicator;

        public ViewHolder(View view){
            _swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
            _listView = (ListView) view.findViewById(R.id.earthquakes_list_view);
            _errorTextView = (TextView) view.findViewById(R.id.error_txt);
            _loadingIndicator = (ProgressBar) view.findViewById(R.id.loading_spinner);
            _listView.setEmptyView(_errorTextView);

            _swipe.setOnRefreshListener(() -> {
                _swipe.setRefreshing(false);
                loadEarthquakes(true);
            });
            _listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    /*
                    * Решение проблемы размещения ListView внутри SwipeRefreshLayout
                    * */
                    if (_listView.getChildAt(0) != null)
                        _swipe.setEnabled(_listView.getFirstVisiblePosition() == 0 && _listView.getChildAt(0).getTop() == 0);
                }
            });

            _listView.setOnItemClickListener((parent, view1, position, id) -> {
                if (checkInternetConnection()){
                    String url = _earthquakes.get(position).getProperties().getUrl();
                    Intent browseInterner = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
                    startActivity(browseInterner);
                }else {
                    showNoConnectionMessage();
                }
            });
        }
    }
}

package com.dzhtv.izhut.usgsmonitoring;

import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dzhtv.izhut.usgsmonitoring.callbacks.PermissionsCallback;
import com.dzhtv.izhut.usgsmonitoring.callbacks.ProviderGoogleCallback;
import com.dzhtv.izhut.usgsmonitoring.events.PermissionsAccess;
import com.dzhtv.izhut.usgsmonitoring.models.weather.Weather;
import com.dzhtv.izhut.usgsmonitoring.views.WeatherMvpView;
import com.dzhtv.izhut.usgsmonitoring.presenters.WeatherPresenter;
import com.dzhtv.izhut.usgsmonitoring.services.ProviderGoogleAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class WeatherFragment extends BaseFragment implements WeatherMvpView, PermissionsCallback {
    private static WeatherFragment instance;
    private View rootView;
    private ViewHolder holder;

    WeatherPresenter presenter;
    ProviderGoogleAPI _provider;


    public static WeatherFragment getInstance(){
        if (instance == null)
            instance = new WeatherFragment();
        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        presenter = ((App)getActivity().getApplication()).getWeatherPresenter();
        holder = new ViewHolder(rootView);
        holder.progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat
                .getColor(getActivity(), R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);

        setPermissionsCallback(this);
        presenter.onAttach(this);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
        if (checkAndRequestPermissions()){
            if (checkInternetConnection()){

                assembleLoadingWeather(false);

            }else {
                holder.location_container.setVisibility(View.GONE);
                holder.weather_container.setVisibility(View.GONE);
                holder.errorTxt.setVisibility(View.VISIBLE);
                holder.errorTxt.setText(getString(R.string.no_internet_connection));
                holder.progressBar.setVisibility(View.GONE);
            }
        }else {
            holder.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    private void assembleLoadingWeather(boolean isRefresh){
        holder.location_container.setVisibility(View.GONE);
        holder.weather_container.setVisibility(View.VISIBLE);

        if (!((MainActivity)getActivity()).getAppUtils().checkActivateGPS()){
            ((MainActivity)getActivity()).getActivateGPSDialog().show(getFragmentManager().beginTransaction(), "gps");
            hideLoadingIndicator();
            return;
        }else {
            Log.d(App.TAG, "Init Google provider");
            _provider = ((MainActivity)getActivity()).getProviderGoogleApi();
            //ProviderGoogleAPI _provider = new ProviderGoogleAPI(getActivity());
            _provider.setProviderCallback(new ProviderGoogleCallback() {
                @Override
                public void onLocationChanged(Location geoPosition) {
                    Log.d(App.TAG, "onLocationChanged");
                }

                @Override
                public void onProviderConnected() {
                    Log.d(App.TAG, "onProviderConnected");
                    Location lastLoc = _provider.getLastLocation();
                    if (lastLoc != null)
                        presenter.loadByCoords(lastLoc.getLatitude(), lastLoc.getLongitude(), isRefresh);
                    else {
                        onShowErrorMessage(getString(R.string.error_getting_last_location));
                        hideLoadingIndicator();
                    }
                }
            });
        }
    }

    @Override
    public void getNeededPermissions(List<String> permissions) {
        presenter.setNeededPermissions(neededPermissions);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PermissionsAccess obj){
        Log.d(App.TAG, "WeatherFragment, onMessageEvent");
        holder.progressBar.setVisibility(View.VISIBLE);
        assembleLoadingWeather(false);
    }


    @Override
    public void onRequestPermissions(List<String> permissions) {
        ActivityCompat.requestPermissions(getActivity(), permissions.toArray(new String[permissions.size()]), Config.REQUEST_PERMISSION_CODE);
    }


    @Override
    public void onShowErrorMessage(String errorMsg) {
        Log.d(App.TAG, "Weather error msg: " + errorMsg);
        holder.errorTxt.setVisibility(View.VISIBLE);
        holder.errorTxt.setText(errorMsg);
    }


    @Override
    public void hideLoadingIndicator() {
        holder.progressBar.setVisibility(View.GONE);
    }


    @Override
    public void setTemp(int temp) {
        holder.temp.setText("");
        holder.temp.setText(temp + "");
    }

    @Override
    public void setMinTemp(int minTemp) {
        holder.temp_min.setText("");
        holder.temp_min.setText(minTemp + "");
    }

    @Override
    public void setMaxTemp(int maxTemp) {
        holder.temp_max.setText("");
        holder.temp_max.setText(maxTemp + "");
    }

    @Override
    public void setHumidity(int hum) {
        holder.humidity.setText("");
        holder.humidity.setText(hum + "");
    }

    @Override
    public void setPressure(int pressure) {
        holder.pressure.setText("");
        holder.pressure.setText(pressure + "");
    }

    @Override
    public void setDescription(String desc) {
        holder.description.setText("");
        holder.description.setText(desc);
    }

    private class ViewHolder{
        private TextView errorTxt, temp, temp_min, temp_max, pressure, humidity, description;
        private ProgressBar progressBar;
        private SwipeRefreshLayout weather_container;
        private LinearLayout location_container;
        private Button requestLocationBtn;

        public ViewHolder(View view){
            requestLocationBtn = (Button)view.findViewById(R.id.request_location_btn);
            errorTxt = (TextView)view.findViewById(R.id.error_txt);
            progressBar = (ProgressBar)view.findViewById(R.id.loading_spinner);
            temp = (TextView) view.findViewById(R.id.temp);
            temp_min = (TextView) view.findViewById(R.id.temp_min);
            temp_max = (TextView) view.findViewById(R.id.temp_max);
            pressure = (TextView) view.findViewById(R.id.pressure);
            description = (TextView)view.findViewById(R.id.description);
            humidity = (TextView)view.findViewById(R.id.humidity);
            location_container = (LinearLayout)view.findViewById(R.id.info_location_container);
            weather_container = (SwipeRefreshLayout) view.findViewById(R.id.container_weather_item);
            weather_container.setOnRefreshListener(() -> {
                weather_container.setRefreshing(false);
                Log.d(App.TAG, "WeatherFragment, onRefresh");
                assembleLoadingWeather(true);
            });

            requestLocationBtn.setOnClickListener(v -> {
                Log.d(App.TAG, "WeatherFragment, onClickRequestLocation");
                presenter.clickRequestPermissions();
            });
        }

    }
}

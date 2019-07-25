package com.dzhtv.izhut.usgsmonitoring;

import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.dzhtv.izhut.usgsmonitoring.callbacks.PermissionsCallback;
import com.dzhtv.izhut.usgsmonitoring.callbacks.ProviderGoogleCallback;
import com.dzhtv.izhut.usgsmonitoring.events.PermissionsAccess;
import com.dzhtv.izhut.usgsmonitoring.ui.weather.WeatherMvpView;
import com.dzhtv.izhut.usgsmonitoring.ui.weather.WeatherPresenter;
import com.dzhtv.izhut.usgsmonitoring.services.ProviderGoogleAPI;
import com.dzhtv.izhut.usgsmonitoring.views.WeatherView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.time.temporal.TemporalAccessor;
import java.util.List;

public class WeatherFragment extends BaseFragment implements WeatherMvpView, PermissionsCallback {
    private View rootView;
    private ViewHolder holder;

    WeatherPresenter presenter;
    ProviderGoogleAPI _provider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        presenter = ((MainActivity)getActivity()).getWeatherPresenter();
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
            Log.d(App.TAG, "Check Permissions true");
            assembleLoadingWeather();
        }else {
            Log.d(App.TAG, "Check Permissions false");
            //hide progressBar
            holder.progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(App.TAG, "WeatherFragment, onStop");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(App.TAG, "WeatherFragment, onDestroy");
        presenter.onDetach();
    }

    private void assembleLoadingWeather(){
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
                        presenter.loadByCoords(lastLoc.getLatitude(), lastLoc.getLongitude());
                    else {
                        Log.d(App.TAG, "onProviderConnected, last location null");
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
        assembleLoadingWeather();
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
        holder.temp.setText(temp + "");
    }

    @Override
    public void setMinTemp(int minTemp) {
        holder.temp_min.setText(minTemp + "");
    }

    @Override
    public void setMaxTemp(int maxTemp) {
        holder.temp_max.setText(maxTemp + "");
    }

    @Override
    public void setHumidity(int hum) {
        holder.humidity.setText(hum + "");
    }

    @Override
    public void setPressure(int pressure) {
        holder.pressure.setText(pressure + "");
    }

    @Override
    public void setDescription(String desc) {
        holder.description.setText(desc);
    }

    private class ViewHolder{
        private TextView errorTxt, temp, temp_min, temp_max, pressure, humidity, description;
        private ProgressBar progressBar;
        private FrameLayout weather_container;
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
            weather_container = (FrameLayout)view.findViewById(R.id.container_weather_item);

            requestLocationBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(App.TAG, "WeatherFragment, onClickRequestLocation");
                    presenter.clickRequestPermissions();
                }
            });
        }

    }
}

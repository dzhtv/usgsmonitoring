package com.dzhtv.izhut.usgsmonitoring;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;


import com.dzhtv.izhut.usgsmonitoring.components.DaggerMainActivityComponent;
import com.dzhtv.izhut.usgsmonitoring.components.MainActivityComponent;
import com.dzhtv.izhut.usgsmonitoring.dialog.ActivateGPSDialog;
import com.dzhtv.izhut.usgsmonitoring.events.PermissionsAccess;
import com.dzhtv.izhut.usgsmonitoring.modules.MainActivityModule;
import com.dzhtv.izhut.usgsmonitoring.services.ProviderGoogleAPI;
import com.dzhtv.izhut.usgsmonitoring.ui.earthquake.EarthquakeFragment;
import com.dzhtv.izhut.usgsmonitoring.ui.earthquake.EarthquakePresenter;
import com.dzhtv.izhut.usgsmonitoring.ui.main.MainMvpView;
import com.dzhtv.izhut.usgsmonitoring.ui.main.MainPresenter;
import com.dzhtv.izhut.usgsmonitoring.ui.other.OtherPresenter;
import com.dzhtv.izhut.usgsmonitoring.ui.weather.WeatherPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainMvpView {
    private BottomNavigationView navView;
    MainActivityComponent _component;
    @Inject
    MainPresenter<MainMvpView> _presenter;
    @Inject
    EarthquakeFragment _earthquakeFragment;
    @Inject
    WeatherFragment _weatherFragment;
    @Inject
    AlertDialog.Builder _alertDialogBuilder;
    @Inject
    OtherFragment _otherFragment;
    @Inject
    ActivateGPSDialog activateGPSDialog;
    @Inject
    Utils appUtils;

    EarthquakePresenter earthPresenter;
    WeatherPresenter weatherPresenter;
    OtherPresenter otherPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDI();
        _presenter.onAttach(this);
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, new EarthquakeFragment())
                    .commit();
        }
    }///EndOnCreate

    /**
     * initialize dependency injection for MainActivity
     */
    private void initDI(){
        _component = DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .build();
        _component.injectMainActivity(this);

        earthPresenter = _component.provideEarthquakePresenter();
        weatherPresenter = _component.provideWeatherPresenter();
        otherPresenter = _component.provideOtherPresenter();

    }

    public EarthquakePresenter getEarthquakePresenter(){
        return earthPresenter;
    }

    public WeatherPresenter getWeatherPresenter(){
        return weatherPresenter;
    }

    public OtherPresenter getOtherPresenter(){
        return otherPresenter;
    }




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        FragmentManager fm = getSupportFragmentManager();

        navView.getSelectedItemId();
        if (item.getItemId() == navView.getSelectedItemId()){
            //do nothing
        }else {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fm.beginTransaction().replace(R.id.frame_container, _earthquakeFragment)
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
                    fm.beginTransaction().replace(R.id.frame_container, _weatherFragment)
                            .commit();
                    return true;
                case R.id.navigation_other:
                    fm.beginTransaction().replace(R.id.frame_container, _otherFragment)
                            .commit();
                    return true;
            }
        }
                return false;
            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.REQUEST_ON_GPS_IN_SETTINGS && resultCode == RESULT_OK){
            Log.d(App.TAG, "onActivityResult: GPS OK");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        _presenter.requestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPushEventPermissionsAccess(PermissionsAccess event) {
        EventBus.getDefault().post(event);
    }

    @Override
    public void onCheckPermissionsRationale(String namePermission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, namePermission))
            showWarningDialog();
        else
            showGoToSettingsDialog();
    }

    @Override
    public void onShowWarningDialog() {
        showWarningDialog();
    }

    @Override
    public void onGoToSettingsDialog() {
        showGoToSettingsDialog();
    }


    @Override
    public String callString(int id) {
        return getString(id);
    }


    /**
     *
     * @return
     */
    public AlertDialog showWarningDialog(){
        _alertDialogBuilder.setTitle("");
        _alertDialogBuilder.setMessage(getString(R.string.warning_about_needs_permissions));
        _alertDialogBuilder.setCancelable(false);
        _alertDialogBuilder.setPositiveButton(getString(R.string.grant_permissions), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                checkAndRequestPermissions();
            }
        });
        _alertDialogBuilder.setNegativeButton(getString(R.string.no_exit_app), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        AlertDialog _dialog = _alertDialogBuilder.create();
        _dialog.show();
        return _dialog;
    }

    /**
     *
     * @return
     */
    public AlertDialog showGoToSettingsDialog(){
        _alertDialogBuilder.setTitle("");
        _alertDialogBuilder.setMessage(getString(R.string.warning_some_permissions));
        _alertDialogBuilder.setCancelable(false);
        _alertDialogBuilder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        _alertDialogBuilder.setNegativeButton(getString(R.string.no_exit_app), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog _dialog = _alertDialogBuilder.create();
        _dialog.show();
        return _dialog;
    }

    public ActivateGPSDialog getActivateGPSDialog(){
        return activateGPSDialog;
    }

    public Utils getAppUtils(){
        return appUtils;
    }

    public Picasso getPicasso(){
        return ((App)getApplication()).getPicasso();
    }

    public ProviderGoogleAPI getProviderGoogleApi(){
        return ((App)getApplication()).getProviderGoogleApi();
    }

}

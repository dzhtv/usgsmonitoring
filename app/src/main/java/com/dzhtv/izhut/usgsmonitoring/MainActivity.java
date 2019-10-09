package com.dzhtv.izhut.usgsmonitoring;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.androidadvance.topsnackbar.TSnackbar;
import com.dzhtv.izhut.usgsmonitoring.components.DaggerMainActivityComponent;
import com.dzhtv.izhut.usgsmonitoring.components.MainActivityComponent;
import com.dzhtv.izhut.usgsmonitoring.dialog.ActivateGPSDialog;
import com.dzhtv.izhut.usgsmonitoring.events.NetworkConnectionEvent;
import com.dzhtv.izhut.usgsmonitoring.events.PermissionsAccess;
import com.dzhtv.izhut.usgsmonitoring.modules.MainActivityModule;
import com.dzhtv.izhut.usgsmonitoring.services.ProviderGoogleAPI;
import com.dzhtv.izhut.usgsmonitoring.views.MainMvpView;
import com.dzhtv.izhut.usgsmonitoring.presenters.MainPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainMvpView {
    private BottomNavigationView navView;
    private FrameLayout frameContainer;
    //private LinearLayout warningLabel;

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
    MarsRoverFragment _marsRoverFragment;
    @Inject
    NasaFragment _nasaFragment;
    @Inject
    ActivateGPSDialog activateGPSDialog;
    @Inject
    Utils appUtils;

    private BroadcastReceiver networkReceiver;
    private boolean doubleBackToExitClick = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showSunsetAnimation();
        initDI();
        EventBus.getDefault().register(this);
        networkReceiver = new NetworkConnectionReceiver();
        registerNetworkBroadcastForNougat();

        _presenter.onAttach(this);
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        frameContainer = findViewById(R.id.frame_container);
       // warningLabel = findViewById(R.id.warning_label);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, new EarthquakeFragment())
                    .commit();
        }
    }

    private void animateNetworkDisconnectMessage(){
        /*
        float networkYTop = warningLabel.getTop();
        float networkYBottom = frameContainer.getTop();
        ObjectAnimator networkAnim = ObjectAnimator.ofFloat(warningLabel, "y", networkYTop, networkYBottom).setDuration(1000);
        networkAnim.start();
        */
    }

    private void animateNetworkConnectMessage(){
        /*
        float networkYTop = warningLabel.getTop();
        float networkYBottom = frameContainer.getTop();

        ObjectAnimator hideLabel_1 = ObjectAnimator.ofFloat(warningLabel, "y", networkYBottom, networkYTop).setDuration(1000);
        hideLabel_1.start();
        */
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(networkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает анимацию заката, если приложение открывается в первый раз
     */
    private void showSunsetAnimation(){
        if (Utils.showSunsetActivity(this)){
            Intent sunsetIntent = new Intent(this, SunsetActivity.class);
            startActivity(sunsetIntent);
        }
    }

    /**
     * Init dependency injection for MainActivity
     */
    private void initDI(){
        _component = DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .build();
        _component.injectMainActivity(this);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        FragmentManager fm = getSupportFragmentManager();

        navView.getSelectedItemId();
        if (item.getItemId() == navView.getSelectedItemId()){
            //do nothing
        }else {
            clearBackStack(fm);
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
                    fm.beginTransaction().replace(R.id.frame_container, _nasaFragment)
                            .commit();
                    return true;
            }
        }
                return false;
            };

    private void clearBackStack(FragmentManager fragmentManager){
        int count = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < count; ++i){
            fragmentManager.popBackStack();
        }
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitClick){
            super.onBackPressed();
            return;
        } else {
            int stackSize = getSupportFragmentManager().getBackStackEntryCount();
            if (stackSize == 0){
                doubleBackToExitClick = true;
                Toast.makeText(this, getString(R.string.please_click_again_to_exit), Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(() -> doubleBackToExitClick = false, 2000);
            } else {
                super.onBackPressed();
                return;
            }
        }
    }

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
        _alertDialogBuilder.setPositiveButton(getString(R.string.grant_permissions), (dialog, which) -> {
            dialog.dismiss();
            checkAndRequestPermissions();
        });
        _alertDialogBuilder.setNegativeButton(getString(R.string.no_exit_app), (dialog, which) -> {
            dialog.dismiss();
            finish();
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
        _alertDialogBuilder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.dismiss();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        _alertDialogBuilder.setNegativeButton(getString(R.string.no_exit_app), (dialog, which) -> {
            dialog.dismiss();
            finish();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NetworkConnectionEvent event){
        if(event.getConnected())
            showConnectionNetworkLabel();
        else
            showDisconnectNetworkLabel();
    }

    /**
     * Отображает информацию о восстановлению интернет связи
     */
    private void showConnectionNetworkLabel(){
       // animateNetworkConnectMessage();
        TSnackbar.make(findViewById(R.id.frame_container),getString(R.string.network_connect), TSnackbar.LENGTH_LONG).show();
    }

    /**
     * Отображает информацию об отсутствии интернета
     */
    private void showDisconnectNetworkLabel(){
        //animateNetworkDisconnectMessage();

        TSnackbar tBar = TSnackbar.make(findViewById(R.id.frame_container),getString(R.string.network_disconnect), TSnackbar.LENGTH_INDEFINITE);
        View _v = tBar.getView();
        _v.setBackgroundColor(getResources().getColor(R.color.magnitude9));
        tBar.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
        EventBus.getDefault().unregister(this);
    }
}

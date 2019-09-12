package com.dzhtv.izhut.usgsmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;

import com.squareup.picasso.Picasso;

public class SunsetActivity extends AppCompatActivity {

    private View sceneView, sunView, skyView;
    private int blueSkyColor, sunsetSkyColor, nightSkyColor;
    //private ImageView sea_image;
    private Picasso picasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sunset);

        sceneView = findViewById(R.id.container);
        sunView = findViewById(R.id.sun);
        skyView = findViewById(R.id.sky);
        //sea_image = findViewById(R.id.sea_image);

        picasso = ((App)getApplication()).getPicasso();
        //picasso.load(R.drawable.sea_500).into(sea_image);

        Resources _res = getResources();
        blueSkyColor = _res.getColor(R.color.blue_sky);
        sunsetSkyColor = _res.getColor(R.color.sunset_sky);
        nightSkyColor = _res.getColor(R.color.night_sky);

        /*
        sceneView.setOnClickListener(v -> {
            startAnimation();
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        Handler hd = new Handler();
        hd.postDelayed(() -> startAnimation(), 500);
    }

    private void startAnimation(){
        float sunYStart = sunView.getTop();
        float sunYEnd = skyView.getHeight();

        ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(sunView, "y", sunYStart, sunYEnd).setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator()); //солнце набирает скорость анимации ближе к концу
        //анимация цвета неба
        ObjectAnimator sunsetSkyAnimator = ObjectAnimator.ofInt(skyView, "backgroundColor", blueSkyColor, sunsetSkyColor).setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

       // heightAnimator.start();
        //sunsetSkyAnimator.start();

        ObjectAnimator nightSkyAnimator = ObjectAnimator.ofInt(skyView, "backgroundColor", sunsetSkyColor, nightSkyColor).setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(heightAnimator)
                .with(sunsetSkyAnimator)
                .before(nightSkyAnimator);
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(App.TAG, "Animation end");
                closeAnimationDisplay();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animSet.start();
    }

    private void closeAnimationDisplay(){
        this.finish();
    }
}

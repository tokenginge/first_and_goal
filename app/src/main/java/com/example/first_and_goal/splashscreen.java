package com.example.first_and_goal;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import gr.net.maroulis.library.EasySplashScreen;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/twenty.ttf");

        EasySplashScreen config = new EasySplashScreen(splashscreen.this).withFullScreen()
                .withTargetActivity(post_splash.class)
                .withSplashTimeOut(2500)
                .withBackgroundColor(R.color.colorBlack)
                .withLogo(R.drawable.splash)
                .withFooterText("2019");



        config.getFooterTextView().setTextColor(Color.WHITE);
       config.getLogo().setMaxHeight(400);
        config.getLogo().setMaxWidth(400);



        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }


}



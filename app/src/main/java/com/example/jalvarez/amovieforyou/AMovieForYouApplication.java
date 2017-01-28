package com.example.jalvarez.amovieforyou;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by jalvarez on 1/18/17.
 * This is a file created for the project A-Movie-For-You
 *
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class AMovieForYouApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Fresco.initialize(this);
    }
}

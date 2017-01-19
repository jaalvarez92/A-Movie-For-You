package com.example.jalvarez.amovieforyou;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by jalvarez on 1/18/17.
 */

public class AMovieForYouApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Fresco.initialize(this);
    }
}

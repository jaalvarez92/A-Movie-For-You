package com.example.jalvarez.amovieforyou.data.videos;

import android.support.annotation.NonNull;

import com.uwetrottmann.tmdb2.entities.Videos;

/**
 * Created by jalvarez on 1/27/17.
 * This is a file created for the project A-Movie-For-You
 *
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class Video {

    @NonNull
    private final String mName;

    @NonNull
    private final String mURL;


    public Video(Videos.Video video){
        mName = video.name;
        mURL = "https://www.youtube.com/watch?v=" + video.key;
    }


    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public String getURL() {
        return mURL;
    }
}

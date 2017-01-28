package com.example.jalvarez.amovieforyou.data;

import android.support.annotation.NonNull;

import com.uwetrottmann.tmdb2.entities.Videos;

/**
 * Created by jalvarez on 1/27/17.
 */

public class Video {

    @NonNull
    private final String mName;

    @NonNull
    private final String mURL;


    public Video(String name, String URL){
        mName = name;
        mURL = URL;
    }

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

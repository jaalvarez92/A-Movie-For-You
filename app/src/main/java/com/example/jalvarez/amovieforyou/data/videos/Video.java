package com.example.jalvarez.amovieforyou.data.videos;

import android.support.annotation.NonNull;

import com.uwetrottmann.tmdb2.entities.Videos;

import io.realm.RealmObject;

/**
 * Created by jalvarez on 1/27/17.
 * This is a file created for the project A-Movie-For-You
 *
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class Video extends RealmObject {

    @NonNull
    private String name;

    @NonNull
    private String URL;

    public Video() {

    }

    public Video(Videos.Video video){
        this.name = video.name;
        this.URL = "https://www.youtube.com/watch?v=" + video.key;
    }


    @NonNull
    public String getName() {
        return this.name;
    }

    @NonNull
    public String getURL() {
        return this.URL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setURL(String URL) {
        URL =  URL;
    }

}

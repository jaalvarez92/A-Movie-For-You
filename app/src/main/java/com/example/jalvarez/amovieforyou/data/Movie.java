package com.example.jalvarez.amovieforyou.data;

import android.support.annotation.NonNull;

import com.google.common.base.Objects;
import com.google.common.collect.ArrayListMultimap;
import com.uwetrottmann.tmdb2.entities.Videos;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by jalvarez on 1/13/17.
 */

public class Movie {

    @NonNull
    private final String mId;

    @NonNull
    private final String mTitle;

    @NonNull
    private final String mSynopsis;

    @NonNull
    private final String mPosterURL;

    @NonNull
    private final String mBackdropURL;

    @NonNull
    private final ArrayList<Video> mVideos;


    @NonNull
    private final double mRating;


    public Movie(@NonNull String id, @NonNull String title, @NonNull String synopsis, @NonNull String posterURL, @NonNull double rating){
        mId = id;
        mTitle = title;
        mSynopsis = synopsis;
        mPosterURL = posterURL;
        mBackdropURL = posterURL;
        mRating = rating;
        mVideos = new ArrayList<>();
    }

    public Movie(@NonNull com.uwetrottmann.tmdb2.entities.Movie movie){
        String tmdbPosterResourcePrefix = "http://image.tmdb.org/t/p/w185/";
        String tmdbBackdropResourcePrefix = "http://image.tmdb.org/t/p/w780/";
        mId = movie.id.toString();
        mTitle = movie.title;
        mSynopsis = movie.overview;
        mPosterURL = tmdbPosterResourcePrefix + movie.poster_path;
        mBackdropURL = tmdbBackdropResourcePrefix + movie.backdrop_path;
        mRating = movie.vote_average / 2;
        mVideos = new ArrayList<>();
        if (movie.videos != null && movie.videos.results != null) {
            for (Videos.Video video : movie.videos.results) {
                if (video.site.equals("YouTube")) {
                    mVideos.add(new Video(video));
                }
            }
        }
    }

    public String getId() {
        return mId;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getSynopsis(){
        return mSynopsis;
    }

    public String getPosterURL(){
        return mPosterURL;
    }

    public String getBackdropURL(){
        return mBackdropURL;
    }

    public double getRating() {
        return mRating;
    }

    public ArrayList<Video> getVideos() {
        return mVideos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equal(mId, movie.mId) &&
                Objects.equal(mTitle, movie.mTitle) &&
                Objects.equal(mSynopsis, movie.mSynopsis);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mTitle, mSynopsis);
    }

    @Override
    public String toString() {
        return "Movie with title " + mTitle;
    }



}

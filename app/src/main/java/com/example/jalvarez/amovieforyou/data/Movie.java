package com.example.jalvarez.amovieforyou.data;

import android.support.annotation.NonNull;

import com.google.common.base.Objects;

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
    private final String mImageURL;


    public Movie(@NonNull String id, @NonNull String title, @NonNull String synopsis, @NonNull String imageURL){
        mId = id;
        mTitle = title;
        mSynopsis = synopsis;
        mImageURL = imageURL;
    }

    public Movie(@NonNull com.uwetrottmann.tmdb2.entities.Movie movie){
        mId = movie.id.toString();
        mTitle = movie.title;
        mSynopsis = movie.overview;
        mImageURL = "http://image.tmdb.org/t/p/w185/" + movie.poster_path;
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

    public String getImageURL(){
        return mImageURL;
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

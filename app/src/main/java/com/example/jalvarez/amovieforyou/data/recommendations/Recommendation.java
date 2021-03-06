package com.example.jalvarez.amovieforyou.data.recommendations;

import android.support.annotation.NonNull;

import com.example.jalvarez.amovieforyou.data.movies.source.Movie;
import com.google.common.base.Objects;

import java.util.Date;

/**
 * Created by jalvarez on 1/13/17.
 * This is a file created for the project A-Movie-For-You
 *
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class Recommendation {

    @NonNull
    private final String mId;

    @NonNull
    private final Date mDate;

    @NonNull
    private final Movie mMovie;


    public Recommendation(@NonNull  String id, @NonNull Date date, @NonNull Movie movie){
        mId = id;
        mDate = date;
        mMovie = movie;
    }


    public String getId(){
        return mId;
    }

    public Date getDate(){
        return mDate;
    }

    public Movie getMovie(){
        return mMovie;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation recommendation = (Recommendation) o;
        return Objects.equal(mId, recommendation.mId) &&
                Objects.equal(mDate, recommendation.mDate) &&
                Objects.equal(mMovie, recommendation.mMovie);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mDate, mMovie);
    }

    @Override
    public String toString() {
        return "Recommendation with movie: " + mMovie.getTitle();
    }





}

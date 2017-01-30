package com.example.jalvarez.amovieforyou.data.movies.source.local;

import android.support.annotation.NonNull;

import com.example.jalvarez.amovieforyou.data.movies.source.MoviesDataSource;

/**
 * Created by jalvarez on 1/29/17.
 * This is a file created for the project A-Movie-For-You
 * <p>
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class MoviesLocalDataSource implements MoviesDataSource {

    private static MoviesLocalDataSource INSTANCE;

    public static MoviesLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MoviesLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getMovies(@NonNull LoadMoviesCallback callback) {
        callback.onDataNotAvailable();
    }

    @Override
    public void getMovie(@NonNull String movieId, @NonNull GetMovieCallback callback) {
        callback.onDataNotAvailable();
    }

    @Override
    public void refreshMovies() { }

}

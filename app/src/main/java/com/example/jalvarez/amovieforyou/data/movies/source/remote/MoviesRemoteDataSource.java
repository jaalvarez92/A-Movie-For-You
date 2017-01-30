package com.example.jalvarez.amovieforyou.data.movies.source.remote;

import android.support.annotation.NonNull;

import com.example.jalvarez.amovieforyou.data.movies.source.Movie;
import com.example.jalvarez.amovieforyou.data.movies.source.MoviesDataSource;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by jalvarez on 1/29/17.
 * This is a file created for the project A-Movie-For-You
 * <p>
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class MoviesRemoteDataSource implements MoviesDataSource {

    private static MoviesRemoteDataSource INSTANCE;
    private static TMDBApiHelper mTmdbApiHelper;

    public static MoviesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MoviesRemoteDataSource();
        }
        return INSTANCE;
    }

    private MoviesRemoteDataSource() {
        mTmdbApiHelper = new TMDBApiHelper();
    }

    @Override
    public void getMovies(@NonNull final LoadMoviesCallback callback) {
        mTmdbApiHelper.getMoviesOnCinemas(new Callback<MovieResultsPage>() {
            @Override
            public void onResponse(Call<MovieResultsPage> call, Response<MovieResultsPage> response) {
                List<com.uwetrottmann.tmdb2.entities.Movie> moviesResponse = response.body().results;
                List<Movie> movies = new ArrayList<>();
                for (com.uwetrottmann.tmdb2.entities.Movie movie : moviesResponse) {
                    movies.add(new Movie(movie));
                }
                callback.onMoviesLoaded(movies);
            }

            @Override
            public void onFailure(Call<MovieResultsPage> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getMovie(@NonNull String movieId, @NonNull final GetMovieCallback callback) {
        mTmdbApiHelper.getMovie(movieId, new Callback<com.uwetrottmann.tmdb2.entities.Movie>() {
            @Override
            public void onResponse(Call<com.uwetrottmann.tmdb2.entities.Movie> call, Response<com.uwetrottmann.tmdb2.entities.Movie> response) {
                callback.onMovieLoaded(new Movie(response.body()));
            }

            @Override
            public void onFailure(Call<com.uwetrottmann.tmdb2.entities.Movie> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void refreshMovies() { }


}

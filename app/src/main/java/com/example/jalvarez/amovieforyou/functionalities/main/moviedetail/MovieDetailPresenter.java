package com.example.jalvarez.amovieforyou.functionalities.main.moviedetail;

import com.example.jalvarez.amovieforyou.data.Movie;
import com.example.jalvarez.amovieforyou.data.source.TMDBApiHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by jalvarez on 1/13/17.
 * This is a file created for the project A-Movie-For-You
 *
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private final MovieDetailContract.View mMovieDetail;
    private boolean mFirstLoad = true;
    private String mId;



    MovieDetailPresenter(MovieDetailContract.View movieDetailView, String id) {
        mMovieDetail = checkNotNull(movieDetailView, "tasksView cannot be null!");
        mMovieDetail.setPresenter(this);
        mId = id;
    }

    @Override
    public void start() {
        loadMovie(mFirstLoad);
        if (mFirstLoad){
            mFirstLoad = false;
        }
    }


    /**
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadMovie(final boolean showLoadingUI) {
        if (showLoadingUI) {
            mMovieDetail.setLoadingIndicator(true);
        }

        new TMDBApiHelper().getMovie(mId, new Callback<com.uwetrottmann.tmdb2.entities.Movie>() {

            @Override
            public void onResponse(Call<com.uwetrottmann.tmdb2.entities.Movie> call, Response<com.uwetrottmann.tmdb2.entities.Movie> response) {
                if (mMovieDetail.isActive()) {
                    Movie movie = new Movie(response.body());
                    processMovie(movie);
                    if (showLoadingUI) {
                        mMovieDetail.setLoadingIndicator(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<com.uwetrottmann.tmdb2.entities.Movie> call, Throwable t) {
                if (mMovieDetail.isActive()) {
                    if (showLoadingUI) {
                        mMovieDetail.setLoadingIndicator(false);
                        processError();
                    }
                }
            }

        });

    }


    private void processMovie(Movie movie) {
        mMovieDetail.showMovie(movie);
    }

    private void processError() {
        mMovieDetail.showError();
    }



}

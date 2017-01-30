package com.example.jalvarez.amovieforyou.functionalities.main.moviedetail;

import android.support.annotation.NonNull;

import com.example.jalvarez.amovieforyou.data.movies.source.Movie;
import com.example.jalvarez.amovieforyou.data.movies.source.MoviesDataSource;
import com.example.jalvarez.amovieforyou.data.movies.source.MoviesRepository;
import com.example.jalvarez.amovieforyou.data.movies.source.remote.TMDBApiHelper;

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
    private final MoviesRepository mMoviesRepository;
    private boolean mFirstLoad = true;
    private String mId;



    MovieDetailPresenter(@NonNull MoviesRepository moviesRepository, @NonNull MovieDetailContract.View movieDetailView, String id) {
        mMoviesRepository = checkNotNull(moviesRepository, "moviesRepository cannot be null!");
        mMovieDetail = checkNotNull(movieDetailView, "moviesView cannot be null!");
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


        mMoviesRepository.getMovie(mId, new MoviesDataSource.GetMovieCallback() {
            @Override
            public void onMovieLoaded(Movie movie) {
                if (mMovieDetail.isActive()) {
                    processMovie(movie);
                    if (showLoadingUI) {
                        mMovieDetail.setLoadingIndicator(false);
                    }
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mMovieDetail.isActive()){
                    processError();
                    if (showLoadingUI) {
                        mMovieDetail.setLoadingIndicator(false);
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

package com.example.jalvarez.amovieforyou.functionalities.main.nowoncinemas;

import android.support.annotation.NonNull;

import com.example.jalvarez.amovieforyou.data.movies.source.Movie;
import com.example.jalvarez.amovieforyou.data.movies.source.MoviesDataSource;
import com.example.jalvarez.amovieforyou.data.movies.source.MoviesRepository;
import com.example.jalvarez.amovieforyou.data.movies.source.remote.TMDBApiHelper;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;

import java.util.ArrayList;
import java.util.List;

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

public class NowOnCinemasPresenter implements NowOnCinemasContract.Presenter {

    private final MoviesRepository mMoviesRepository;
    private final NowOnCinemasContract.View mNowOnCinemasView;
    private boolean mFirstLoad = true;



    public NowOnCinemasPresenter(MoviesRepository moviesRepository,  NowOnCinemasContract.View latestRecommendationsView) {
        mMoviesRepository = checkNotNull(moviesRepository, "moviesRepository cannot be null!");
        mNowOnCinemasView = checkNotNull(latestRecommendationsView, "moviesView cannot be null!");
        mNowOnCinemasView.setPresenter(this);
    }

    @Override
    public void start() {
        loadMovies(false);
    }


    @Override
    public void loadMovies(boolean forceUpdate) {
        loadMovies(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }


    private void loadMovies(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mNowOnCinemasView.setLoadingIndicator(true);
        }

        if (forceUpdate) {
            mMoviesRepository.refreshMovies();
        }

        mMoviesRepository.getMovies(new MoviesDataSource.LoadMoviesCallback() {
            @Override
            public void onMoviesLoaded(List<Movie> movies) {
                if (mNowOnCinemasView.isActive()){
                    processMovies(movies);
                    if (showLoadingUI) {
                        mNowOnCinemasView.setLoadingIndicator(false);
                    }
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mNowOnCinemasView.isActive()) {
                    processEmptyMovies();
                    if (showLoadingUI) {
                        mNowOnCinemasView.setLoadingIndicator(false);
                    }
                }
            }
        });

    }


    private void processMovies(List<Movie> movies) {
        if (movies.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            processEmptyMovies();
        } else {
            // Show the list of tasks
            mNowOnCinemasView.showMovies(movies);
        }
    }

    private void processEmptyMovies() {
        mNowOnCinemasView.showNoMovies();
    }


    @Override
    public void openMovieDetails(@NonNull Movie requestedMovie) {
        checkNotNull(requestedMovie, "requestedRecommendation cannot be null!");
        mNowOnCinemasView.showMovieDetailsUi(requestedMovie.getId());
    }


}

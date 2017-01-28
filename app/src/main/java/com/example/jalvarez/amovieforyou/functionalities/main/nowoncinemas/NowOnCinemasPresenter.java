package com.example.jalvarez.amovieforyou.functionalities.main.nowoncinemas;

import android.support.annotation.NonNull;

import com.example.jalvarez.amovieforyou.data.Movie;
import com.example.jalvarez.amovieforyou.data.source.TMDBApiHelper;
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

    private final NowOnCinemasContract.View mNowOnCinemasView;
    private boolean mFirstLoad = true;



    public NowOnCinemasPresenter(NowOnCinemasContract.View latestRecommendationsView) {
        mNowOnCinemasView = checkNotNull(latestRecommendationsView, "tasksView cannot be null!");
        mNowOnCinemasView.setPresenter(this);
    }

    @Override
    public void start() {
        loadMovies(mFirstLoad);
        if (mFirstLoad){
            mFirstLoad = false;
        }

    }


    /**
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadMovies(final boolean showLoadingUI) {
        if (showLoadingUI) {
            mNowOnCinemasView.setLoadingIndicator(true);
        }

        TMDBApiHelper tmdbApiHelper = new TMDBApiHelper();
        tmdbApiHelper.getMoviesOnCinemas(new Callback<MovieResultsPage>() {
            @Override
            public void onResponse(Call<MovieResultsPage> call, Response<MovieResultsPage> response) {
                if (mNowOnCinemasView.isActive()) {
                    List<com.uwetrottmann.tmdb2.entities.Movie> moviesResponse = response.body().results;
                    List<Movie> movies = new ArrayList<>();
                    for (com.uwetrottmann.tmdb2.entities.Movie movie : moviesResponse) {
                        movies.add(new Movie(movie));
                    }
                    processMovies(movies);
                    if (showLoadingUI) {
                        mNowOnCinemasView.setLoadingIndicator(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResultsPage> call, Throwable t) {
                if (mNowOnCinemasView.isActive()) {
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

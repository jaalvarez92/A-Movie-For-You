package com.example.jalvarez.amovieforyou.functionalities.main.moviedetail;

import com.example.jalvarez.amovieforyou.data.Movie;
import com.example.jalvarez.amovieforyou.data.Recommendation;
import com.example.jalvarez.amovieforyou.data.dummy.RecommendationsDummyData;
import com.example.jalvarez.amovieforyou.data.source.TMDBApiHelper;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by jalvarez on 1/13/17.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private final MovieDetailContract.View mMovieDetail;
    private boolean mFirstLoad = true;
    private String mId;



    public MovieDetailPresenter(MovieDetailContract.View movieDetailView, String id) {
        mMovieDetail = checkNotNull(movieDetailView, "tasksView cannot be null!");
        mMovieDetail.setPresenter(this);
        mId = id;
    }

    @Override
    public void start() {
        loadMovie(true);
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

                Movie movie = new Movie(response.body());
                processMovie(movie);
                if (showLoadingUI) {
                    mMovieDetail.setLoadingIndicator(false);
                }

            }

            @Override
            public void onFailure(Call<com.uwetrottmann.tmdb2.entities.Movie> call, Throwable t) {

                if (showLoadingUI) {
                    mMovieDetail.setLoadingIndicator(false);
                }

            }
        });




    }


    private void processMovie(Movie movie) {
        mMovieDetail.showMovie(movie);
    }

    private void processError() {
//        mMovieDetail.showNoRecommendations();
    }



    @Override
    public void loadMovie() {

    }
}

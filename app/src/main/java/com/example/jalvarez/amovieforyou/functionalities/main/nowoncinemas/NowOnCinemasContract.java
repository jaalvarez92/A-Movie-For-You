package com.example.jalvarez.amovieforyou.functionalities.main.nowoncinemas;

import android.support.annotation.NonNull;

import com.example.jalvarez.amovieforyou.base.BasePresenter;
import com.example.jalvarez.amovieforyou.base.BaseView;
import com.example.jalvarez.amovieforyou.data.Movie;

import java.util.List;

/**
 * Created by jalvarez on 1/13/17.
 */

public class NowOnCinemasContract {
    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showMovies(List<Movie> movies);

        void showMovieDetailsUi(String movieId);

        void showNoMovies();

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void loadMovies();

        void openMovieDetails(@NonNull Movie requestedMovie);

    }
}

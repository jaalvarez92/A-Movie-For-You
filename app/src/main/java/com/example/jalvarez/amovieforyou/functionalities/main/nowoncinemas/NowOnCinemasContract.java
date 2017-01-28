package com.example.jalvarez.amovieforyou.functionalities.main.nowoncinemas;

import android.support.annotation.NonNull;

import com.example.jalvarez.amovieforyou.base.BasePresenter;
import com.example.jalvarez.amovieforyou.base.BaseView;
import com.example.jalvarez.amovieforyou.data.Movie;

import java.util.List;

/**
 * Created by jalvarez on 1/13/17.
 * This is a file created for the project A-Movie-For-You
 *
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
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


        void openMovieDetails(@NonNull Movie requestedMovie);

    }
}

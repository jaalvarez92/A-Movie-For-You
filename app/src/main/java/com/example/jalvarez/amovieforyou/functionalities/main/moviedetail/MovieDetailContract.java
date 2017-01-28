package com.example.jalvarez.amovieforyou.functionalities.main.moviedetail;

import com.example.jalvarez.amovieforyou.base.BasePresenter;
import com.example.jalvarez.amovieforyou.base.BaseView;
import com.example.jalvarez.amovieforyou.data.Movie;
import com.example.jalvarez.amovieforyou.data.Video;

/**
 * Created by jalvarez on 1/13/17.
 * This is a file created for the project A-Movie-For-You
 *
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class MovieDetailContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showMovie(Movie movie);

        void showError();

        boolean isActive();

        void showVideo(Video video);

    }

    interface Presenter extends BasePresenter {


    }
}
